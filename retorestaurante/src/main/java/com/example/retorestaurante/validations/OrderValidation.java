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
}
