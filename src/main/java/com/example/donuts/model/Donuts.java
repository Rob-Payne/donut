package com.example.donuts.model;

import com.example.donuts.dao.DonutsRepository;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Donuts {

    //Self populating id for the db
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String topping;

    //Formatted date
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date expiration;

    //No args constructor
    public Donuts() {
    }

    //All args constructor
    @JsonCreator
    public Donuts(Long id, String name, String topping, Date expiration) {
        this.id = id;
        this.name = name;
        this.topping = topping;
        this.expiration = expiration;
    }

    @JsonGetter
    public Long getId() {
        return id;
    }

    @JsonSetter
    public void setId(Long id) {
        this.id = id;
    }

    @JsonGetter
    public String getName() {
        return name;
    }

    @JsonSetter
    public void setName(String name) {
        this.name = name;
    }

    @JsonGetter
    public String getTopping() {
        return topping;
    }

    @JsonSetter
    public void setTopping(String topping) {
        this.topping = topping;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonGetter
    public Date getExpiration() {
        return expiration;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSetter
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
