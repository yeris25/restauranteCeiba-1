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

    //13 historia
    @Operation(summary = "Create a claim")
    @ApiResponse(responseCode = "201", description = "Claim created successfully", content = @Content(schema = @Schema(implementation = ClaimDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ClaimErrorDTO.class)))
    @PostMapping
    public ResponseEntity<ClaimDTO> createClaim (@RequestBody Claim claim){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(claimService.createClaim(claim));
        }catch (Exception e){
            ClaimErrorDTO claimErrorDTO = new ClaimErrorDTO();
            claimErrorDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(claimErrorDTO);
        }
    }

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

    //15 historia
    @Operation(summary = "Update claim status by ID")
    @ApiResponse(responseCode = "200", description = "Claim status updated successfully", content = @Content(schema = @Schema(implementation = ClaimDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ClaimErrorDTO.class)))
    @PutMapping("/{id}")
    public ResponseEntity<ClaimDTO> updateStatusClaim(@PathVariable Long id, @RequestBody Claim claim){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(claimService.updateStatusClaim(id, claim));
        }catch (Exception e){
            ClaimErrorDTO claimErrorDTO = new ClaimErrorDTO();
            claimErrorDTO.setMessage(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(claimErrorDTO);
        }
    }

}