package com.example.retorestaurante.entity;

import jakarta.persistence.*;

//@Entity se utiliza para marcar una clase como una entidad persistente. En este caso,
// la clase que contiene esta anotación se considera una
// entidad que se puede almacenar y recuperar de una base de datos.
@Entity
@Table(name = "claim_entity")
// @Table se utiliza para especificar el nombre de la tabla en la base de datos a la que se debe
// mapear la entidad.En este caso, la entidad se mapeará a una tabla llamada "claim_entity".
public class Claim {

    //La anotación @Id se utiliza para marcar un atributo como la clave primaria de la entidad
    @Id
    //La anotación @GeneratedValue se utiliza para especificar
    // cómo se generará automáticamente el valor de la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //La anotación @OneToOne se utiliza para establecer una relación uno a uno entre la entidad actual
    // y otra entidad.
    // En este caso, se establece una relación uno a uno con la entidad "Order".
    @OneToOne
    // La anotación @JoinColumn se utiliza para especificar la columna
    // en la tabla de la base de datos que se utilizará como clave externa para la relación.
    @JoinColumn(name = "idOrder", referencedColumnName = "idOrder")
    private Order order;

    //@Column se utilizan para especificar las propiedades de las columnas en la tabla de la base de datos.
    @Column(name = "site", nullable = false)
    private String site;
    @Column(name = "status", nullable = false)
    private String status = "Generated";
    @Column(name = "reason", nullable = false)
    private String reason;
    @Column(name = "reponse")
    private String response;

    @Column(name = "rol", nullable = false)
    private Character rol;
//el código representa los atributos de una entidad en JPA y las anotaciones utilizadas
// para mapear esos atributos a una tabla en una base de datos relacional
    public Claim() {
    }

    public Claim(Long id, Order order, String site, String status, String reason, String response, Character rol) {
        this.id = id;
        this.order = order;
        this.site = site;
        this.status = status;
        this.reason = reason;
        this.response = response;
        this.rol = rol;
    }
    // un constructor de la clase Claim que asigna los valores de los parámetros a los atributos correspondientes de la clase.
    // Esto permite crear objetos de la clase Claim con valores específicos para cada atributo

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Character getRol() {
        return rol;
    }

    public void setRol(Character rol) {
        this.rol = rol;
    }
}
//Los métodos getter se utilizan para obtener el valor de un atributo específico.
// En este caso, los métodos getter devuelven el valor de los atributos correspondientes.

//Los métodos setter se utilizan para establecer el valor de un atributo específico. En este caso,
// los métodos setter asignan el valor proporcionado como parámetro al atributo correspondiente