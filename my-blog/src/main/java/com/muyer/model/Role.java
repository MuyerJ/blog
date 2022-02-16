package com.muyer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Describe: 权限
 */
@Data
@NoArgsConstructor
public class Role {

    private int id;

    private String name;

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
