package com.nonisystems.jit.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role implements Serializable {

    private Long id;
    private String name;
    private String description;
    private Set<Permission> permissions;
}
