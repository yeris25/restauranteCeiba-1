package com.example.retorestaurante.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "claim_entity")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "idOrder", referencedColumnName = "idOrder")
    private Order order;
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
