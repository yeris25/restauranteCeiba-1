package com.example.retorestaurante.controller;

import com.example.retorestaurante.dto.ClaimDTO;
import com.example.retorestaurante.dto.ClaimErrorDTO;
import com.example.retorestaurante.dto.ClaimResponseDTO;
import com.example.retorestaurante.entity.Claim;
import com.example.retorestaurante.services.ClaimService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("restauranteAPI/claim")
public class ClaimController {

    @Autowired
    ClaimService claimService;


    //14 historia

    @Operation(summary = "Get all claims in state 'Generated'")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ClaimResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ClaimErrorDTO.class)))
    @GetMapping
    public ResponseEntity<List<ClaimResponseDTO>> getClaims(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(claimService.getClaimsInStateGenerated());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}