package com.example.apsoft.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Schema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] context;

    public Schema() { }

    public Schema(byte[] context) {
        this.context = context;
    }
}
