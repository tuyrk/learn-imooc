package com.tuyrk.stream.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserDTO implements Serializable {
    private static final long serialVersionUID = 3705242122741841918L;
    private String username;
    private String mobile;
    private String name;
    private String email;
    private String password;
    private Boolean enabled;
}
