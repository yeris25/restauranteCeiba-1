package com.example.retorestaurante.maps;

import com.example.retorestaurante.dto.MenuResponseDTO;
import com.example.retorestaurante.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuMap {

    @Mappings({
            @Mapping(source="name",target = "name"),
            @Mapping(source="price",target = "price"),
            @Mapping(source="description",target = "description"),
            @Mapping(source="url",target = "url"),
            @Mapping(source="category",target = "category")

    })

    public MenuResponseDTO toMenuResponseDto(Menu menu);

    public List <MenuResponseDTO> toMenuResponseDtos(List<Menu> menus);


}
