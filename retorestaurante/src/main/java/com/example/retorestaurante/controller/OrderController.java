package com.example.retorestaurante.controller;

import com.example.retorestaurante.dto.OrderDTO;
import com.example.retorestaurante.dto.OrderErrorDTO;
import com.example.retorestaurante.dto.OrderResponseDTO;
import com.example.retorestaurante.entity.Order;
import com.example.retorestaurante.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

  //Quinta Historia
    @Operation(summary = "Create an order")
    //respuesta exitosa con un código de estado HTTP 201
    @ApiResponse(responseCode = "201", description = "Order created successfully", content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    //respuesta de error con un código de estado HTTP 400
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = OrderErrorDTO.class)))
    @PostMapping
    //Crear una orden y si la orden es exitosa se devuelve una respuesta con un codigo HTTP 201
    public ResponseEntity<OrderDTO> createOrder(@RequestBody Order dataOrder) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(dataOrder));
            // Si ocurre alguna excepción durante la creación de la orden,
            // se captura esa excepción en el bloque catch
        } catch (Exception e) {
            OrderErrorDTO orderErrorDTO = new OrderErrorDTO();
            //se devuelve una respuesta con la orden creada. Si ocurre algún error,
            // se devuelve una respuesta con un mensaje de error.
            orderErrorDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderErrorDTO);
        }
    }

    //Sexta historia
    @Operation(summary = "Update an order by ID")
    @ApiResponse(responseCode = "200", description = "Order updated successfully", content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = OrderErrorDTO.class)))
    @PutMapping("/updateOrder/{id}")
    public ResponseEntity<OrderDTO> updatedOrder(@PathVariable Long id, @RequestBody Order order) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrderPreparation(id, order));
        } catch (Exception e) {
            OrderErrorDTO orderErrorDTO = new OrderErrorDTO();
            orderErrorDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderErrorDTO);
        }
    }

    //Septima historia
    @Operation(summary = "Update an order to set it as ready")
    @ApiResponse(responseCode = "200", description = "Order updated successfully", content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = OrderErrorDTO.class)))
    @PutMapping("/updateOrderReady/{id}")
    public ResponseEntity<OrderDTO> updateOrderReady(@PathVariable long id, @RequestBody Order order) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrderReady(id, order));
        } catch (Exception e) {
            OrderErrorDTO orderErrorDTO = new OrderErrorDTO();
            orderErrorDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderErrorDTO);
        }
    }

    //11 historia
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getPaginatedAndFilterOrder(
            @RequestParam() String site,
            @RequestParam() String status,
            @RequestParam() int numberOfRecords
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


    // decima historia
    @Operation(summary = "Update an order to set it as canceled")
    @ApiResponse(responseCode = "200", description = "Order updated successfully", content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = OrderErrorDTO.class)))

    @PutMapping("/updateOrderCanceled/{id}")
    public ResponseEntity<OrderDTO> updateOrderCanceled(@PathVariable Long id, @RequestBody Order order) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrderCanceled(id, order));
        } catch (Exception e) {
            OrderErrorDTO orderErrorDTO = new OrderErrorDTO();
            orderErrorDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderErrorDTO);
        }
    }


    //Novena historia
    @Operation(summary = "Update an order to set it as delivered")
    @ApiResponse(responseCode = "200", description = "Order updated successfully", content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = OrderErrorDTO.class)))

    @PutMapping("/updateOrderDelivered/{id}")
    public ResponseEntity<OrderDTO> updateOrderDelivered(@PathVariable Long id, @RequestBody Order order) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrderDelivered(id, order));
        } catch (Exception e) {
            OrderErrorDTO orderErrorDTO = new OrderErrorDTO();
            orderErrorDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderErrorDTO);
        }
    }


    //7 historia
    @GetMapping("/getForStatus")
    public ResponseEntity<List<OrderResponseDTO>> getOrderForStatus(@RequestParam() String status, @RequestParam() int numberOfRecords) {
        try {
            Page<OrderResponseDTO> orderPages = orderService.getOrderForStatus(status, numberOfRecords);
            List<OrderResponseDTO> orderList = orderPages.getContent();
            return ResponseEntity.status(HttpStatus.OK).body(orderList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

