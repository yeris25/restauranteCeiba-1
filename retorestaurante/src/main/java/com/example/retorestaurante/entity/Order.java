package com.example.retorestaurante.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "order_entity")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrder;

    @Column(name = "rol", nullable = false)
    private Character rol;

    @Column(name = "approvalRol", nullable = false)
    private Character approvalRol;

    @Column(name="site",nullable = false)
    private String site;

    @Column(name="status",nullable = false)
    private String status = "Pendiente";

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    private List<OrderDetail> details;

    @OneToOne(mappedBy = "order")
    private Claim claim;

    //constructor vacío

    public Order() {
    }


    //constructor lleno

    public Order(Long idOrder, Character rol, String site, String status, List<OrderDetail> details) {
        this.idOrder = idOrder;
        this.rol = rol;
        this.site = site;
        this.status = status;
        this.details = details;
    }


    //getters y setters


    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Character getRol() {
        return rol;
    }

    public void setRol(Character rol) {
        this.rol = rol;
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

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    public Character getApprovalRol() {
        return approvalRol;
    }

    public void setApprovalRol(Character approvalRol) {
        this.approvalRol = approvalRol;
    }

}
