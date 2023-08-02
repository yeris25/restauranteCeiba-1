package com.example.retorestaurante.maps;

import com.example.retorestaurante.dto.OrderDetailDTO;
import com.example.retorestaurante.dto.OrderResponseDTO;
import com.example.retorestaurante.entity.Order;
import com.example.retorestaurante.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMap {

    @Mappings({
            @Mapping(source = "idOrder", target = "idOrder"),
            @Mapping(source = "site", target = "site"),
            @Mapping(source="status",target = "status"),
            @Mapping(target = "details", qualifiedByName = "toListOrderDetail")
    })
    public OrderResponseDTO toOrderResponseDto(Order order);
    public List<OrderResponseDTO> toOrderResponseDtos(List<Order> orders);

    @Named("toListOrderDetail")
    default List<OrderDetailDTO> toListOrderDetail(List<OrderDetail> details){
        return details.stream()
                .map(this::toDetailOrder)
                .collect(Collectors.toList());
    }

    @Mapping(target = "name", source = "menu.name")
    @Mapping(target = "quantity", source = "quantity")
    OrderDetailDTO toDetailOrder(OrderDetail orderDetail);
}
