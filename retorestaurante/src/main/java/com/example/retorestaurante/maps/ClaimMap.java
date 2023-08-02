package com.example.retorestaurante.maps;

import com.example.retorestaurante.dto.ClaimResponseDTO;
import com.example.retorestaurante.entity.Claim;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClaimMap {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "reason", target = "reason"),
            @Mapping(source = "response", target = "response")
    })
    public ClaimResponseDTO toClaimResponseDTO(Claim claim);
    public List<ClaimResponseDTO> toClaimResponseDTOs(List<Claim> claims);
}
