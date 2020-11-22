package com.tuyrk.stream.rest;


import com.tuyrk.stream.domain.PageableResult;
import com.tuyrk.stream.domain.User;
import com.tuyrk.stream.domain.dto.AddUserDTO;
import com.tuyrk.stream.domain.dto.UpdateUserDTO;
import com.tuyrk.stream.domain.dto.UserDTO;
import com.tuyrk.stream.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/api")
@RestController
public class UserResource {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/users")
    public PageableResult<User> getAllUsers(Pageable pageable) {
        Page<User> userPageable = userRepo.findAll(pageable);
        return new PageableResult<>(userPageable.getTotalElements(), userPageable.getContent());
    }

    @Transactional(readOnly = true)
    @GetMapping("/users/by-age/{age}")
    public List<UserDTO> getUsersByRoleName(@PathVariable Integer age) {
        return userRepo.findByAgeGreaterThan(age)
                .map(user -> UserDTO.builder()
                        .name(user.getName())
                        .enabled(String.valueOf(user.isEnabled()))
                        .mobile(user.getMobile())
                        .username(user.getUsername())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return userRepo.findOptionalByUsername(username)
                .map(mapUserToDto())
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> addUser(@RequestBody AddUserDTO addUserDTO) {
        if (userRepo.findOptionalByUsername(addUserDTO.getUsername()).isPresent()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        User toAdd = User.builder()
                .password(addUserDTO.getPassword())
                .enabled(addUserDTO.getEnabled())
                .username(addUserDTO.getUsername())
                .name(addUserDTO.getName())
                .mobile(addUserDTO.getMobile())
                .email(addUserDTO.getEmail())
                .build();
        User saved = userRepo.save(toAdd);
        return mapUserToDto().apply(saved);
    }

    @PutMapping("/users/{username}")
    public ResponseEntity<UserDTO> updateUserByUsername(@PathVariable String username,
                                                        @RequestBody UpdateUserDTO updateUserDTO) {
        return userRepo.findOptionalByUsername(username)
                .map(saveUser(updateUserDTO))
                .map(mapUserToDto())
                .orElse(ResponseEntity.notFound().build());
    }

    private Function<User, ResponseEntity<UserDTO>> mapUserToDto() {
        return user -> {
            UserDTO dto = UserDTO.builder()
                    .username(user.getUsername())
                    .name(user.getName())
                    .enabled(user.isEnabled() ? "激活" : "禁用")
                    .mobile(user.getMobile())
                    .build();
            return ResponseEntity.ok().body(dto);
        };
    }

    @DeleteMapping("/users/{username}")
    public void deleteUserByUsername(@PathVariable String username) {
        userRepo.findOptionalByUsername(username)
                .map(User::getId)
                .ifPresent(userRepo::deleteById);
    }

    private Function<User, User> saveUser(UpdateUserDTO updateUserDTO) {
        return user -> {
            User toSave = user
                    .withMobile(updateUserDTO.getMobile())
                    .withName(updateUserDTO.getName())
                    .withEmail(updateUserDTO.getEmail());
            return userRepo.save(toSave);
        };
    }
}
