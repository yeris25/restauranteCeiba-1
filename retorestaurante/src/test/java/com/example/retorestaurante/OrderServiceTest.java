package com.example.retorestaurante;

import com.example.retorestaurante.dto.OrderResponseDTO;
import com.example.retorestaurante.entity.Claim;
import com.example.retorestaurante.entity.Menu;
import com.example.retorestaurante.entity.Order;
import com.example.retorestaurante.entity.OrderDetail;
import com.example.retorestaurante.maps.OrderMap;
import com.example.retorestaurante.repository.RepositoryMenu;
import com.example.retorestaurante.repository.RepositoryOrder;
import com.example.retorestaurante.services.OrderService;
import com.example.retorestaurante.validations.OrderValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    private Order order;
    private OrderResponseDTO orderResponseDTO;


    @Mock
    private RepositoryMenu repositoryMenu;

    @Mock
    private RepositoryOrder repositoryOrder;

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderMap orderMaps;

    @Mock
    private OrderValidation orderValidation;

    @BeforeEach
    void setup() throws Exception{
        MockitoAnnotations.openMocks(this);
        Menu mockMenu = new Menu();
        mockMenu.setId(1L);
        mockMenu.setName("Menu de prueba");
        mockMenu.setPreparationTime(10.0);
        when(repositoryMenu.findById(1L)).thenReturn(Optional.of(mockMenu));

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setMenu(mockMenu);
        orderDetail.setQuantity(2);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);

        Long id = 1L;
        Character rol = 'U';
        Character aprovalRol = 'A';
        String site = "Medellin";
        String status = "Pendiente";
        Claim claim = new Claim();
        double timeOrder = 10.0;
        String reasonForCancellation = "Ejemplo de cancelacion";
        order = new Order(id,rol, aprovalRol,site,status,orderDetails,claim,timeOrder, reasonForCancellation);


    }

    @Test
    void createOrder() throws Exception{
        when(repositoryOrder.save(order)).thenReturn(order);
        OrderResponseDTO result = orderService.createOrder(order);
        assertEquals(result, orderResponseDTO);
    }

    @Test
    void createOrderWrongRol() throws Exception{
        order.setRol('A');
        assertThrows(Exception.class, ()->orderService.createOrder(order));
    }

    @Test
    void createOrderWithNull() throws Exception{
        order.setSite("");
        assertThrows(Exception.class, ()->orderService.createOrder(order));
    }

    @Test
    void updateOrderPreparation() throws Exception{
        order.setRol('A');
        order.setStatus("Preparacion");

        Order orderExist = new Order();
        orderExist.setStatus("Pendiente");
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(Optional.of(orderExist));
        when(repositoryOrder.save(order)).thenReturn(order);
        OrderResponseDTO responseDTO = orderService.updateOrderPreparation(order.getIdOrder(), order);
        assertEquals(responseDTO,orderResponseDTO);
    }

    @Test
    void updateOrderPreparationWrongRol() throws Exception{
        assertThrows(Exception.class, ()->orderService.updateOrderPreparation(order.getIdOrder(), order));
    }
    @Test
    void updateOrderPreparationIdNotExist() throws Exception{
        order.setRol('A');
        Optional<Order> optionalEmpty = Optional.empty();
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(optionalEmpty);
        assertThrows(Exception.class, ()->orderService.updateOrderPreparation(order.getIdOrder(), order));
    }

    @Test
    void updateOrderPreparationDataEnteredWrong() throws Exception{
        order.setRol('A');
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(Optional.of(order));
        assertThrows(Exception.class, ()-> orderService.updateOrderPreparation(order.getIdOrder(), order));
    }
    @Test
    void updateOrderPreparationDataExistWrong() throws Exception{
        order.setRol('A');
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(Optional.of(order));
        order.setStatus("Preparacion");
        assertThrows(Exception.class, ()-> orderService.updateOrderPreparation(order.getIdOrder(), order));
    }

    @Test
    void updateOrderReady() throws Exception{
        order.setRol('A');
        order.setStatus("Listo");

        Order orderExist = new Order();
        orderExist.setStatus("Preparacion");
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(Optional.of(orderExist));
        when(repositoryOrder.save(order)).thenReturn(order);
        OrderResponseDTO responseDTO = orderService.updateOrderReady(order.getIdOrder(), order);
        assertEquals(responseDTO,orderResponseDTO);
    }

    @Test
    void updateOrderReadyWrongRol() throws Exception{
        assertThrows(Exception.class,()->orderService.updateOrderReady(order.getIdOrder(), order));
    }
    @Test
    void updateOrderReadyIdNotExist() throws Exception{
        order.setRol('A');
        Optional<Order> optionalEmpty = Optional.empty();
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(optionalEmpty);
        assertThrows(Exception.class, ()->orderService.updateOrderReady(order.getIdOrder(), order));
    }
    @Test
    void updateOrderReadyDataEnteredWrong() throws Exception{
        order.setRol('A');
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(Optional.of(order));
        assertThrows(Exception.class, ()-> orderService.updateOrderReady(order.getIdOrder(), order));
    }
    @Test
    void updateOrderReadyDataExistWrong() throws Exception{
        order.setRol('A');
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(Optional.of(order));
        order.setStatus("Listo");
        assertThrows(Exception.class, ()-> orderService.updateOrderReady(order.getIdOrder(), order));
    }

    @Test
    void getOrderForStatusAndSite() throws Exception{
        int numberOfRecords = 1;
        Pageable pagerList = (Pageable) PageRequest.of(0,numberOfRecords);
        Page<Order> orderPage = new PageImpl<>(List.of(order));
        when(repositoryOrder.findByStatusAndSite(order.getStatus(),order.getSite(), (org.springframework.data.domain.Pageable) pagerList)).thenReturn(orderPage);
        when(orderMaps.toOrderResponseDto(order)).thenReturn(orderResponseDTO);
        Page<OrderResponseDTO> result = orderService.getOrderForStatusAndSite(order.getSite(), order.getStatus(), numberOfRecords);
        assertNotNull(result);
        assertEquals(1,result.getTotalElements());
    }
    @Test
    void getOrderForStatusAndSiteWithError() throws Exception{
        int numberOfRecords = 0;
        assertThrows(Exception.class, ()->orderService.getOrderForStatusAndSite(order.getSite(),order.getStatus(),numberOfRecords));
    }

    @Test
    void getOrderForStatus() throws Exception{
        int numberOfRecords = 1;
        Pageable pagerList = (Pageable) PageRequest.of(0,numberOfRecords);
        Page<Order> orderPage = new PageImpl<>(List.of(order));
        when(repositoryOrder.findByStatus(order.getStatus(), (org.springframework.data.domain.Pageable) pagerList)).thenReturn(orderPage);
        when(orderMaps.toOrderResponseDto(order)).thenReturn(orderResponseDTO);
        Page<OrderResponseDTO> result = orderService.getOrderForStatus(order.getStatus(),numberOfRecords);
        assertNotNull(result);
        assertEquals(1,result.getTotalElements());
    }

    @Test
    void getOrderForStatusWithError() throws Exception{
        int numberOfRecords = 0;
        assertThrows(Exception.class, ()->orderService.getOrderForStatus(order.getStatus(),numberOfRecords));
    }

    @Test
    void updateOrderCancelled() throws Exception{
        order.setRol('A');
        order.setStatus("Cancelado");

        Order orderExist = new Order();
        orderExist.setStatus("Pendiente");
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(Optional.of(orderExist));
        when(repositoryOrder.save(order)).thenReturn(order);
        OrderResponseDTO responseDTO = orderService.updateOrderCanceled(order.getIdOrder(), order);
        assertEquals(responseDTO,orderResponseDTO);
    }
    @Test
    void updateOrderCancelledWrongRol() throws Exception{
        assertThrows(Exception.class,()->orderService.updateOrderCanceled(order.getIdOrder(), order));
    }
    @Test
    void updateOrderCancelledIdNotExist() throws Exception{
        order.setRol('A');
        Optional<Order> optionalEmpty = Optional.empty();
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(optionalEmpty);
        assertThrows(Exception.class, ()->orderService.updateOrderCanceled(order.getIdOrder(), order));
    }

    @Test
    void updateOrderCancelledMessageNull() throws Exception{
        order.setRol('A');
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(Optional.of(order));
        order.setReasonForCancellation(null);
        assertThrows(Exception.class, ()->orderService.updateOrderCanceled(order.getIdOrder(), order));
    }
    @Test
    void updateOrderCancelledDataEnteredWrong() throws Exception{
        order.setRol('A');
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(Optional.of(order));
        assertThrows(Exception.class, ()-> orderService.updateOrderCanceled(order.getIdOrder(), order));
    }
    @Test
    void updateOrderCancelledDataExistWrong() throws Exception{
        order.setRol('A');
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(Optional.of(order));
        order.setStatus("Cancelado");
        assertThrows(Exception.class, ()-> orderService.updateOrderCanceled(order.getIdOrder(), order));
    }


    @Test
    void updateOrderDelivered() throws Exception{
        order.setRol('A');
        order.setStatus("Entregado");

        Order orderExist = new Order();
        orderExist.setStatus("Listo");
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(Optional.of(orderExist));
        when(repositoryOrder.save(order)).thenReturn(order);
        OrderResponseDTO responseDTO = orderService.updateOrderDelivered(order.getIdOrder(), order);
        assertEquals(responseDTO,orderResponseDTO);
    }
    @Test
    void updateOrderDeliveredWrongRol() throws Exception{
        assertThrows(Exception.class,()->orderService.updateOrderDelivered(order.getIdOrder(), order));
    }
    @Test
    void updateOrderDeliveredIdNotExist() throws Exception{
        order.setRol('A');
        Optional<Order> optionalEmpty = Optional.empty();
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(optionalEmpty);
        assertThrows(Exception.class, ()->orderService.updateOrderDelivered(order.getIdOrder(), order));
    }
    @Test
    void updateOrderDeliveredDataExistWrong() throws Exception{
        order.setRol('A');
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(Optional.of(order));
        assertThrows(Exception.class, ()-> orderService.updateOrderDelivered(order.getIdOrder(), order));
    }
    @Test
    void updateOrderDeliveredDataEnteredWrong() throws Exception{
        order.setRol('A');
        when(repositoryOrder.findById(order.getIdOrder())).thenReturn(Optional.of(order));
        order.setStatus("Listo");
        assertThrows(Exception.class, ()-> orderService.updateOrderDelivered(order.getIdOrder(), order));
    }

}
