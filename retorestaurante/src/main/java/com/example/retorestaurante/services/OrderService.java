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


    public OrderResponseDTO createOrder(Order dataOrder) throws Exception {
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

            if (!dataOrder.getStatus().equals("Preparacion"))

                throw new Exception("El estado no puede ser diferente de preparación");
            orderExist.setStatus(dataOrder.getStatus());
            return orderMaps.toOrderResponseDto(repositoryOrder.save(orderExist));


        } catch (Exception error) {
            throw new Exception(error.getMessage());

        }
    }

    public OrderResponseDTO updateOrderReady(Long idOrder, Order dataOrder) throws Exception {
        try {
            if (dataOrder.getRol() != ('A')) {
                throw new Exception("El rol no esta autorizado para actualizar el pedido");
            }
            Optional<Order> orderOptional = repositoryOrder.findById(idOrder);
            if (orderOptional.isEmpty()) {
                throw new Exception("No existe el pedido");
            }
            Order orderExist = orderOptional.get();
            if (!dataOrder.getStatus().equals("Listo") && orderExist.getStatus().equals("Preparacion"))
                throw new Exception("El estado no puede ser diferente de Listo");
            orderExist.setStatus(dataOrder.getStatus());
            return orderMaps.toOrderResponseDto(repositoryOrder.save(orderExist));
        } catch (Exception error) {
            throw new Exception(error.getMessage());
        }

    }

    public Page<OrderResponseDTO> getOrderForStatusAndSite(String side, String status, int numberOfRecords) throws Exception {
        // Se crea un objeto Pageable llamado pagerList utilizando el método estático of()
        // de la clase PageRequest. Este objeto define la paginación de los resultados,
        // indicando que se desea obtener la primera página con numberOfRecords registros
        try {
            Pageable pagerList = PageRequest.of(0, numberOfRecords);
            //Se utiliza el método findByStatusAndSite() de la instancia repositoryOrder para buscar
            // los pedidos en la base de datos que coincidan con el sitio y el estado proporcionados,
            // utilizando el objeto pagerList para la paginación.
            //El resultado se guarda en un objeto Page<Order> llamado orderPagerList.
            Page<Order> orderPagerList = repositoryOrder.findByStatusAndSite(side, status, pagerList);
            //Se utiliza el método map() del objeto orderPagerList para convertir cada objeto Order en
            //un objeto OrderResponseDTO utilizando el método toOrderResponseDto()
            //de la instancia orderMaps. El resultado es una página de objetos OrderResponseDTO.
            return orderPagerList.map(order -> orderMaps.toOrderResponseDto(order));
//
            // Si no se produce ninguna excepción, se devuelve la página de objetos OrderResponseDTO generada
            // en el paso anterior.
            //Si se produce una excepción, se lanza una nueva excepción con el mensaje de error original.
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public OrderResponseDTO updateOrderCanceled(Long idOrder, Order dataOrder) throws Exception {
        try {
            //Se verifica si el rol del usuario que realiza la actualización es diferente de 'A'.
            // Si es así, se lanza una excepción con el mensaje
            // "El rol no está autorizado para actualizar el estado del pedido".
            if (dataOrder.getRol() != ('A')) {
                throw new Exception("El rol no esta autorizado para actualizar el estado del pedido");
            }
            //Se busca el pedido en la base de datos utilizando el método findById() de la instancia repositoryOrder.
            // Si no se encuentra el pedido, se lanza una excepción con el mensaje
            //"No existe un pedido, por lo tanto no se puede actualizar el estado".
            Optional<Order> orderOptional = repositoryOrder.findById(idOrder);
            if (orderOptional.isEmpty()) {
                throw new Exception("No existe un pedido, por lo tanto no se puede actualizar el estado");
            }
            //Se obtiene el objeto Order existente a partir del Optional<Order> obtenido en el paso anterior.
            Order orderExist = orderOptional.get();
            //Se verifica si el estado del pedido existente es "Preparacion" o si el estado actualizado en dataOrder es "Listo" o "Entregado".
            // Si se cumple alguna de estas condiciones,
            // se lanza una excepción con el mensaje "El pedido no se puede cancelar en esta instancia".
            if (orderExist.getStatus() == ("Preparacion") || dataOrder.getStatus() == ("Listo") || dataOrder.getStatus() == ("Entregado")) {
                throw new Exception("El pedido no se puede cancelar en esta instancia");
            }
            //Si todas las validaciones son exitosas, se actualiza el estado del pedido existente
            // con el estado proporcionado en dataOrder utilizando el método setStatus().
            // Luego, se guarda el pedido actualizado en la
            // base de datos utilizando el método save() de la instancia repositoryOrder.
            orderExist.setStatus(dataOrder.getStatus());
            return orderMaps.toOrderResponseDto(repositoryOrder.save(orderExist));

        } catch (Exception error) {
            throw new Exception(error.getMessage());

        }
    }


    public OrderResponseDTO updateOrderDelivered(Long idOrder, Order dataOrder) throws Exception {
        try {
            if (dataOrder.getRol() != ('A')) {
                throw new Exception("El rol no esta autorizado para actualizar el estado del pedido");
            }
            Optional<Order> orderOptional = repositoryOrder.findById(idOrder);
            if (orderOptional.isEmpty()) {
                throw new Exception("No existe un pedido, por lo tanto no se puede actualizar el estado");
            }


            Order orderExist = orderOptional.get();

            if (orderExist.getStatus() != ("Listo") && !dataOrder.getStatus().equals("Entregado")) {
                throw new Exception("El pedido no se puede entregar en esta instancia");
            }


            orderExist.setStatus(dataOrder.getStatus());
            return orderMaps.toOrderResponseDto(repositoryOrder.save(orderExist));

        } catch (Exception error) {
            throw new Exception(error.getMessage());
        }

    }


    //Preparación, Listo, Entregado, Cancelado, Pendiente
    public List<OrderResponseDTO> getOrderReady() {
        return orderMaps.toOrderResponseDtos(repositoryOrder.findByStatus("Listo"));
    }

    public List<OrderResponseDTO> getOrderDelivered() {
        return orderMaps.toOrderResponseDtos(repositoryOrder.findByStatus("Entregado"));
    }

    public List<OrderResponseDTO> getOrderCanceled() {
        return orderMaps.toOrderResponseDtos(repositoryOrder.findByStatus("Cancelado"));
    }

    public List<OrderResponseDTO> getOrderPending() {
        return orderMaps.toOrderResponseDtos(repositoryOrder.findByStatus("Pendiente"));
    }

    public List<OrderResponseDTO> getOrderPreparation() {
        return orderMaps.toOrderResponseDtos(repositoryOrder.findByStatus("Preparacion"));
    }


}
