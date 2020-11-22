package com.tuyrk.stream.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO implements Serializable {
    private static final long serialVersionUID = -1641031806754148511L;
    private String mobile;
    private String name;
    private String email;
}
