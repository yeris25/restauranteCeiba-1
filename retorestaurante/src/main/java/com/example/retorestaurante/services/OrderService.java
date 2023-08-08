package com.example.retorestaurante.services;


import com.example.retorestaurante.dto.OrderDetailDTO;
import com.example.retorestaurante.dto.OrderResponseDTO;
import com.example.retorestaurante.entity.Menu;
import com.example.retorestaurante.entity.Order;
import com.example.retorestaurante.entity.OrderDetail;
import com.example.retorestaurante.maps.OrderMap;
import com.example.retorestaurante.repository.RepositoryMenu;
import com.example.retorestaurante.repository.RepositoryOrder;
import com.example.retorestaurante.validations.OrderValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class OrderService {

    @Autowired
    RepositoryOrder repositoryOrder;
    @Autowired
    OrderMap orderMaps;
    @Autowired
    OrderValidation orderValidation;

    @Autowired
    RepositoryMenu repositoryMenu;


//    public OrderResponseDTO updateStatusOrder(Long idOrder, Order dataOrder) throws Exception {
//        try {
//            if (!dataOrder.getApprovalRol().equals('A')) {
//                throw new Exception("No tiene permisos para cambiar el estado a este pedido");
//            }
//            Optional<Order> orderOptional = repositoryOrder.findById(idOrder);
//            if (orderValidation.validateIsIdIsPresent(orderOptional)) {
//                throw new Exception("El pedido no existe");
//            }
//            Order orderExist = orderOptional.get();
//            if (orderExist.getStatus() != ("Listo") && dataOrder.getStatus() != ("Entregado")) {
//                throw new Exception("Solamente puede actualizar el estado del pedido a entregado cuando se encuentre listo");
//            }
//            orderExist.setStatus(dataOrder.getStatus());
//            return orderMaps.toOrderResponseDto(repositoryOrder.save(orderExist));
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//    }

    public OrderResponseDTO createOrder (Order dataOrder) throws Exception {
        try {
            if (dataOrder.getRol() != ('U')) {
                throw new Exception("No tiene permisos para crear una Orden");
            }
            if (OrderValidation.validateRequired(dataOrder)) {
                throw new Exception("Campos obligatorios vacios, verifique nuevamente");
            }
            double timePreparationOrder = 0;
            for (OrderDetail detail : dataOrder.getDetails()) {
                Long idOrder = detail.getMenu().getId();
                Optional<Menu> menuOptional = repositoryMenu.findById(idOrder);
                detail.getMenu().setName(menuOptional.get().getName());
                timePreparationOrder += menuOptional.get().getPreparationTime()*detail.getQuantity();
            }
            dataOrder.setTimeOrder(timePreparationOrder);
            return orderMaps.toOrderResponseDto(repositoryOrder.save(dataOrder));

        } catch (Exception error) {
            throw new Exception(error.getMessage());
        }
    }
    public OrderResponseDTO updateOrderPreparation(Long idOrder, Order dataOrder) throws Exception {
        try {
            if (dataOrder.getRol() != ('A')) {
                throw new Exception("El rol no esta autorizado para actualizar el pedido");
            }
            Optional<Order> orderOptional = repositoryOrder.findById(idOrder);
            if (orderOptional.isEmpty()) {
                throw new Exception("No existe el pedido");
            }
            Order orderExist = orderOptional.get();

            if (!dataOrder.getStatus().equals ("Preparacion"))

                throw new Exception("El estado no puede ser diferente de preparación");

            else if (!orderExist.getStatus().equals("Pendiente")) {
                throw new Exception("No puedes cambiar a preparacion una orden que tenga estado diferente de pendiente");
            }
            orderExist.setStatus(dataOrder.getStatus());
            return orderMaps.toOrderResponseDto(repositoryOrder.save(orderExist));


        } catch (Exception error) {
            throw new Exception(error.getMessage());

        }
    }

    public OrderResponseDTO updateOrderReady(Long idOrder, Order dataOrder) throws  Exception {
        try {
            if (dataOrder.getRol() != ('A')) {
                throw new Exception("El rol no esta autorizado para actualizar el pedido");
            }
            Optional<Order> orderOptional = repositoryOrder.findById(idOrder);
            if (orderOptional.isEmpty()) {
                throw new Exception("No existe el pedido");
            } Order orderExist = orderOptional.get();
            if (!dataOrder.getStatus().equals("Listo")){
                throw new Exception("El estado no puede ser diferente de Listo");
            }
            else if (!orderExist.getStatus().equals("Preparacion")){
                throw new Exception("No puedes pasar a listo una orden que se encuentra en estado diferente a preparacion");
            }
            orderExist.setStatus(dataOrder.getStatus());
            return orderMaps.toOrderResponseDto(repositoryOrder.save(orderExist));
        }catch (Exception error) {
            throw new Exception(error.getMessage());
        }

    }


    public Page<OrderResponseDTO> getOrderForStatusAndSite (String site, String status, int numberOfRecords) throws Exception {
        try {
            Pageable pagerList = PageRequest.of(0, numberOfRecords);
            Page<Order> orderPagerList = repositoryOrder.findByStatusAndSite(status, site, pagerList);
            return orderPagerList.map(order -> orderMaps.toOrderResponseDto(order));

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }



    public OrderResponseDTO updateOrderCanceled(Long idOrder, Order dataOrder) throws Exception {

        try {
            // Verificar si el rol es autorizado para actualizar el estado del pedido
            orderValidation.validarLetra(dataOrder.getRol());

            // Buscar el pedido por ID en la base de datos
            Optional<Order> orderOptional = repositoryOrder.findById(idOrder);
            if (orderOptional.isEmpty()) {
                throw new Exception("No existe un pedido con el ID proporcionado");
            }

            Order orderExist = orderOptional.get();
            String status = orderExist.getStatus();
            orderExist.setReasonForCancellation(dataOrder.getReasonForCancellation());

            if (orderExist.getReasonForCancellation() == null) {
                throw new Exception("Debe ingresar una razón para cancelar el pedido");
            }

            // Verificar si el pedido está en estado "Pendiente" para permitir la cancelación

            if (!dataOrder.getStatus().equals("Cancelado")) {
                throw new Exception("Solo puedes actualizar este pedido a 'Cancelado'");

            }

            if (!status.equals("Pendiente")) {
                throw new Exception("Lo sentimos, tu pedido ya está "  + status +  " y no puede ser cancelado");
            }


            orderExist.setStatus(dataOrder.getStatus());
            return orderMaps.toOrderResponseDto(repositoryOrder.save(orderExist));

        } catch (Exception error) {
            throw new Exception(error.getMessage());
        }
    }


    public OrderResponseDTO updateOrderDelivered (Long idOrder, Order dataOrder) throws Exception {
        try
        {
            if (dataOrder.getRol() != ('A')) {
                throw new Exception("El rol no esta autorizado para actualizar el estado del pedido");
            }
            Optional<Order> orderOptional = repositoryOrder.findById(idOrder);
            if (orderOptional.isEmpty()) {
                throw new Exception("No existe un pedido, por lo tanto no se puede actualizar el estado");
            }


            Order orderExist = orderOptional.get();

            if (!orderExist.getStatus().equals("Listo")) {
                throw new Exception("No puedes pasar a Entregado una orden que esta en estado diferente a Listo");
            } else if (!dataOrder.getStatus().equals("Entregado")) {
                throw new Exception("En este punto solo puedes actualizar el estado a entregado");
            }


            orderExist.setStatus(dataOrder.getStatus());
            return orderMaps.toOrderResponseDto(repositoryOrder.save(orderExist));

        } catch (Exception error) {
            throw new Exception(error.getMessage());
        }

    }


    public Page<OrderResponseDTO> getOrderForStatus(String status, int numberOfRecords) throws  Exception{
        try{
            Pageable pagerList = PageRequest.of(0,numberOfRecords);
            Page<Order> orderPagerList = repositoryOrder.findByStatus(status, pagerList);
            return orderPagerList.map(order -> orderMaps.toOrderResponseDto(order));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }




    //Preparación, Listo, Entregado, Cancelado, Pendiente
//    public List<OrderResponseDTO> getOrderReady () {
//        return orderMaps.toOrderResponseDtos(repositoryOrder.findByStatus("Listo"));
//    }
//
//    public List<OrderResponseDTO> getOrderDelivered () {
//        return orderMaps.toOrderResponseDtos(repositoryOrder.findByStatus("Entregado"));
//    }
//
//    public List<OrderResponseDTO> getOrderCanceled () {
//    return orderMaps.toOrderResponseDtos(repositoryOrder.findByStatus("Cancelado"));
//    }
//
//    public List<OrderResponseDTO> getOrderPending () {
//        return orderMaps.toOrderResponseDtos(repositoryOrder.findByStatus("Pendiente"));
//    }
//
//    public List<OrderResponseDTO> getOrderPreparation () {
//        return orderMaps.toOrderResponseDtos(repositoryOrder.findByStatus("Preparacion"));
//    }

}
