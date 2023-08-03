package com.example.retorestaurante.validations;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MenuValidation {


    // "validatePrice(Integer price)": Este método toma un parámetro de tipo Integer llamado "price"
    // y verifica si el valor es menor que cero. Si el valor es menor que cero, el método devuelve true,
    // lo que indica que el precio no es válido. De lo contrario, devuelve false.
    public boolean validatePrice(Integer price){
        if (price<0){
            return true;
        }else {
            return false;
        }
    }

    //Este método toma un parámetro de tipo Optional llamado "id" y verifica si el valor
    // está presente o no. Si el valor no está presente o es vacío, el método devuelve true,
    // lo que indica que el id no está presente o no es válido. De lo contrario, devuelve false.
    public  boolean validateIsIdIsPresent(Optional<?> id){
        if (!id.isPresent() || id.isEmpty()){
            return true;
        }else {
            return false;
        }
    }
}
