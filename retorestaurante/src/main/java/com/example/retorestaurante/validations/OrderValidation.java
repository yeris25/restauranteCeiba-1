package com.example.retorestaurante.validations;

import com.example.retorestaurante.entity.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderValidation {

    public  boolean validateIsIdIsPresent(Optional<?> id){
        if (!id.isPresent() || id.isEmpty()){
            return true;
        }else {
            return false;
        }
    }
    public static Boolean validateRequired(Order order){
        return  order.getSite() == null || order.getSite().isEmpty() ||
                order.getDetails() == null || order.getDetails().isEmpty();
    }

    public  void validarLetra(Character letra) throws Exception {
        if (!letra.equals('A') && !letra.equals('U')) {
            throw new Exception("No tienes permiso para modificar el pedido");
        }
    }

}
//Si el campo "site" del objeto Order es nulo o vacío, o si el campo "details" del objeto Order
// es nulo o vacío,
// el método devuelve true. Esto indica que uno o ambos campos requeridos están faltando o son inválidos.
//- De lo contrario, si ninguno de los campos requeridos es nulo o vacío,
// el método devuelve false. Esto indica que todos los campos requeridos están presentes y son válidos
