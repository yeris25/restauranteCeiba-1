package com.example.retorestaurante.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "menu_entity")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rol" , nullable = false)
    private Character rol;

    @Column(name = "name" , nullable = false)
    private String name;

    @Column(name = "price" , nullable = false)
    private Integer price;

    @Column(name = "description" , nullable = false)
    private String description;

    @Column(name = "url" , nullable = false)
    private String url;

    @Column(name = "category" , nullable = false)
    private String category;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @Column(name = "site", nullable = false)
    private String site;
    @Column(name = "preparationTime", nullable = false)
    private double preparationTime;


    //Constructor vacío

    public Menu() {
    }


    //Constructor con parámetros

    public Menu(Long id, char rol, String name, Integer price, String description, String url, String category, String site, double preparationTime, boolean status) {
        this.id = id;
        this.rol = rol;
        this.name = name;
        this.price = price;
        this.description = description;
        this.url = url;
        this.category = category;
        this.status = status;
        this.site = site;
        this.preparationTime = preparationTime;
//        this.order = order;
    }


    //Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getRol() {
        return rol;
    }

    public void setRol(Character rol) {
        this.rol = rol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public double getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(double preparationTime) {
        this.preparationTime = preparationTime;
    }
}
