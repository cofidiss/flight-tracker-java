package com.flight.application.DTO;

import lombok.Data;
@Data
public class RoleLovDto {

    private String name;
    private Integer id;

    public RoleLovDto(String name, Integer id) {
        this.name = name;
        this.id = id;
    }
public RoleLovDto(){


}


}


