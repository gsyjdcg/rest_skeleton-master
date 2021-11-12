package com.codechallenge.adapter.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import lombok.NoArgsConstructor;

@Entity(name = "Product")
@Table(name = "PRODUCTS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @Column(name = "ID", updatable = false, nullable = false)
    private String id;

    @Column(name = "NAME", nullable = false)
    private String name;
}
