package com.flight.core.domain;

import lombok.Data;
@Data
public class RoleLov {

    private String name;
    private Integer id;

    public RoleLov(String name, Integer id) {
        this.name = name;
        this.id = id;
    }
public RoleLov(){


}


}


