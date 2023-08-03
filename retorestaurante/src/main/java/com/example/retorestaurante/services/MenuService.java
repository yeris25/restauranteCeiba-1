package com.example.retorestaurante.services;

import com.example.retorestaurante.dto.MenuResponseDTO;
import com.example.retorestaurante.entity.Menu;
import com.example.retorestaurante.maps.MenuMap;
import com.example.retorestaurante.repository.RepositoryMenu;
import com.example.retorestaurante.validations.MenuValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuService {
    @Autowired
    MenuValidation validate;
    @Autowired
    RepositoryMenu repositoryMenu;
    @Autowired
    MenuMap menuMap;

    public MenuResponseDTO createMenu(Menu dataMenu) throws Exception{
        try{
            if(!dataMenu.getRol().equals('A')){
                throw new Exception("No tiene permisos para crear un plato");
            }else if (validate.validatePrice(dataMenu.getPrice())){
                throw new Exception("El plato no puede tener un precio negativo");
            }
            return menuMap.toMenuResponseDto(repositoryMenu.save(dataMenu));

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }
    public MenuResponseDTO updateStatus(Long id, Menu dataMenu) throws Exception{
        try{
            if (!dataMenu.getRol().equals('A')){
                throw new Exception("No tienes permisos para actualizar el estado");
            }
            Optional<Menu> menuOptional = repositoryMenu.findById(id);
            if (validate.validateIsIdIsPresent(menuOptional)){
                throw new Exception("El id no esta presente o es nulo");
            }
            Menu menuExist =  menuOptional.get();
            menuExist.setStatus(dataMenu.getStatus());
            return menuMap.toMenuResponseDto(repositoryMenu.save(menuExist));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public Page<MenuResponseDTO> getMenusForCategoryAndSite(String category, String site, int numberOfRecords) throws Exception{
        try{
            Pageable pagerList = PageRequest.of(0, numberOfRecords);
            Page<Menu> menuPagerList = repositoryMenu.findByCategoryAndSite(category, site, pagerList);
            return menuPagerList.map(menu -> menuMap.toMenuResponseDto(menu));

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public MenuResponseDTO updateMenu (Long id, Menu dataNewMenu) throws Exception {
        //Hacemos nuestras validaciones
        try {
            if (!dataNewMenu.getRol().equals('A')) {
                throw new Exception("No tienes permisos para actualizar un plato");
            }
            //Validamos si el plato existe en nuestra base de datos
            Optional<Menu> dishOptional = repositoryMenu.findById(id);

            if (dishOptional.isPresent()) {
                // El plato existe, traemos nuestro plato
                Menu menuFound = dishOptional.get();

                // Actualizar solo los campos que recibimos como parámetros
                if (dataNewMenu.getPrice() != null) {
                    menuFound.setPrice(dataNewMenu.getPrice());
                }
                if (dataNewMenu.getSite() != null) {
                    menuFound.setSite(dataNewMenu.getSite());
                }
                if (dataNewMenu.getDescription() != null) {
                    menuFound.setDescription(dataNewMenu.getDescription());
                }
                // Guardar el plato actualizado en la base de datos
                Menu menuUpdated = repositoryMenu.save(menuFound);

                return menuMap.toMenuResponseDto(menuUpdated);
            } else {
                throw new Exception("El plato no existe");
            }

        } catch (Exception error) {
            throw new Exception(error.getMessage());

        }

    }


}
