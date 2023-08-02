package com.example.retorestaurante.dto;

public class OrderErrorDTO extends OrderDTO{
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
