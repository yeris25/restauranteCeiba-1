package com.example.retorestaurante.dto;

public class ClaimErrorDTO extends ClaimDTO{

    //un atributo privado llamado "message" de tipo String.
    private String message;

    //método getter llamado "getMessage()" que devuelve el valor del atributo "message".
    public String getMessage() {
        return message;
    }

   // método setter llamado "setMessage(String message)" que establece el valor del atributo "message"
   // según el parámetro que se le pase.
    public void setMessage(String message) {
        this.message = message;
    }
}

