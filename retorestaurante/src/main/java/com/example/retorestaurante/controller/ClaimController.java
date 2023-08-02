package com.example.retorestaurante.controller;

import com.example.retorestaurante.dto.ClaimDTO;
import com.example.retorestaurante.dto.ClaimErrorDTO;
import com.example.retorestaurante.dto.ClaimResponseDTO;
import com.example.retorestaurante.entity.Claim;
import com.example.retorestaurante.services.ClaimService;
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
    @GetMapping
    public ResponseEntity<List<ClaimResponseDTO>> getClaims(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(claimService.getClaimsInStateGenerated());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

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