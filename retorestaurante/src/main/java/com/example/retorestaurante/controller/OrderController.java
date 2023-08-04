package com.example.retorestaurante.controller;

import com.example.retorestaurante.dto.OrderDTO;
import com.example.retorestaurante.dto.OrderErrorDTO;
import com.example.retorestaurante.dto.OrderResponseDTO;
import com.example.retorestaurante.entity.Order;
import com.example.retorestaurante.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("restaurantAPI/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody Order dataOrder) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(dataOrder));
        }catch (Exception e){
            OrderErrorDTO orderErrorDTO = new OrderErrorDTO();
            orderErrorDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderErrorDTO);
        }
    }
    @PutMapping("/updateOrder/{id}")
    public ResponseEntity<OrderDTO> updatedOrder(@PathVariable Long id, @RequestBody Order order){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrderPreparation(id, order));
        }catch (Exception e){
            OrderErrorDTO orderErrorDTO = new OrderErrorDTO();
            orderErrorDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderErrorDTO);
        }
    }
    @PutMapping("/updateOrderReady/{id}")
    public ResponseEntity<OrderDTO> updateOrderReady(@PathVariable long id, @RequestBody Order order){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrderReady(id, order));
        }catch (Exception e){
            OrderErrorDTO orderErrorDTO = new OrderErrorDTO();
            orderErrorDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderErrorDTO);
        }
    }
    // GET para obtener todas las órdenes
    @GetMapping
    public ResponseEntity <List<OrderResponseDTO>> getPaginatedAndFilterOrder (
            @RequestParam () String site,
            @RequestParam () String status,
            @RequestParam () int numberOfRecords
    ) {
        try {
            // Llamamos al servicio para obtener la respuesta paginada
            Page<OrderResponseDTO> orderPages = orderService.getOrderForStatusAndSite(site, status, numberOfRecords);

            // Creamos una instancia de PlatoRespuestaPaginadaDTO y le pasamos la lista de platos obtenida del Page
            List<OrderResponseDTO> listOrders = orderPages.getContent();

            return ResponseEntity.status(HttpStatus.OK).body(listOrders);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @PutMapping("/updateOrderCanceled/{id}")
    public ResponseEntity<OrderDTO> updateOrderCanceled(@PathVariable Long id, @RequestBody Order order){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrderCanceled(id, order));
        }catch (Exception e){
            OrderErrorDTO orderErrorDTO = new OrderErrorDTO();
            orderErrorDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderErrorDTO);
        }
    }


    @PutMapping("/updateOrderDelivered/{id}")
    public ResponseEntity<OrderDTO> updateOrderDelivered (@PathVariable Long id, @RequestBody Order order){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrderDelivered(id, order));
        }catch (Exception e){
            OrderErrorDTO orderErrorDTO = new OrderErrorDTO();
            orderErrorDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderErrorDTO);
        }
    }


    @GetMapping("/getOrderReady")
    public ResponseEntity<List<OrderResponseDTO>> getOrderReady () {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderReady());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @GetMapping("/getOrderDelivered")
    public ResponseEntity<List<OrderResponseDTO>> getOrderDelivered () {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderDelivered());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getOrderCanceled")
    public ResponseEntity<List<OrderResponseDTO>> getOrderCanceled () {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderCanceled());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @GetMapping("/getOrderPending")
    public ResponseEntity<List<OrderResponseDTO>> getOrderPending () {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderPending());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @GetMapping("getOrderPreparation")
    public ResponseEntity<List<OrderResponseDTO>> getOrderPreparation () {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderPreparation());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}

