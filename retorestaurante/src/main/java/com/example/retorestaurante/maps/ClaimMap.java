package com.example.retorestaurante.maps;

import com.example.retorestaurante.dto.ClaimResponseDTO;
import com.example.retorestaurante.entity.Claim;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClaimMap {

 // tipo traductor para retornar DTO
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "reason", target = "reason"),
            @Mapping(source = "response", target = "response")
    })

    //lo convirte a un dto
    public ClaimResponseDTO toClaimResponseDTo(Claim claim);
    public List<ClaimResponseDTO> toClaimResponseDTOs(List<Claim> claims);

}
