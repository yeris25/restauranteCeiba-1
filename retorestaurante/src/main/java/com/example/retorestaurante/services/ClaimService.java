package com.example.retorestaurante.services;

import com.example.retorestaurante.dto.ClaimResponseDTO;
import com.example.retorestaurante.entity.Claim;
import com.example.retorestaurante.entity.Order;
import com.example.retorestaurante.maps.ClaimMap;
import com.example.retorestaurante.repository.RepositoryClaim;
import com.example.retorestaurante.repository.RepositoryOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class ClaimService {
    @Autowired
    RepositoryClaim repositoryClaim;
    @Autowired
    ClaimMap claimMaps;
    @Autowired
    RepositoryOrder repositoryOrder;

    //Se crea una reclamacion verificando una orden y los permisos de el usuario y si
    //cumple las condiciones se guarda la reclamacion y devuelve un objeto de claimResponseDto
    //Si ocurre algún error, se lanza una excepción con el mensaje de error correspondiente.

    public ClaimResponseDTO createClaim(Claim claim) throws Exception{
        try {
            Optional< Order> orderOptional = repositoryOrder.findById(claim.getOrder().getIdOrder());
            if(!orderOptional.isPresent()){ //Captura la excepcion
                throw new Exception("La Orden no existe");
            }
            if (!claim.getRol().equals('U')){
                throw new Exception("No tiene permisos para crear una reclamacion");
            }
            return claimMaps.toClaimResponseDTo(repositoryClaim.save(claim));
        }catch (Exception e){ //maneja la excepcion
            throw new Exception(e.getMessage()); //Llama nueva excepcion
        }
    }

    //el código realiza una consulta al repositorio para obtener reclamaciones con un
    // estado específico y luego las convierte en objetos ClaimResponseDTO utilizando un mapeador.
    // El resultado de este proceso se devuelve como resultado de la función.
    public List<ClaimResponseDTO> getClaimsInStateGenerated(){
        return claimMaps.toClaimResponseDTOs(repositoryClaim.findByStatus("Generada"));
    }

    public ClaimResponseDTO updateStatusClaim(Long id, Claim claim) throws Exception{
        try {
            Optional<Claim> claimOptional = repositoryClaim.findById(id);
            //Se ejecuta si la condicion es verdadera y se es falsa se omite
            if (!claimOptional.isPresent()){
                throw new Exception("La reclamacion no existe");
            }
            if (!claim.getRol().equals('A')){
                throw new Exception("No tiene permisos para modificar el estado de una reclamcion");
            }
            Claim claimExist = claimOptional.get();
            claimExist.setStatus(claim.getStatus());
            claimExist.setResponse(claim.getResponse());
            if (claimExist.getResponse()== null){
                throw new Exception("El campo de respuesta no puede quedar vacio");
            }
            return claimMaps.toClaimResponseDTo(repositoryClaim.save(claimExist));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
