package com.kh.cinepic.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="category")
@Getter @Setter @ToString
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Column(name ="category_name", nullable = false)
    private String categoryName;
}
