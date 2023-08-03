package com.example.retorestaurante.controller;

import com.example.retorestaurante.dto.MenuDTO;
import com.example.retorestaurante.dto.MenuErrorDTO;
import com.example.retorestaurante.dto.MenuResponseDTO;
import com.example.retorestaurante.entity.Menu;
import com.example.retorestaurante.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("restaurante/menu")
public class MenuController {

    //@Autowired se utiliza en Spring Framework para realizar la inyección
    // de dependencias de manera automática
    @Autowired
    MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuDTO> createMenu(@RequestBody Menu dataMenu) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(menuService.createMenu(dataMenu));
        }catch (Exception e){
            MenuErrorDTO menuErrorDTO = new MenuErrorDTO();
            menuErrorDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(menuErrorDTO);
        }
    }
    @PutMapping("/updateMenu/{id}")
    public ResponseEntity<MenuDTO> updatedMenu(@PathVariable Long id, @RequestBody Menu menu){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(menuService.updateMenu(id, menu));
        }catch (Exception e){
            MenuErrorDTO menuErrorDTO = new MenuErrorDTO();
            menuErrorDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(menuErrorDTO);
        }
    }


    //El método está anotado con @PutMapping y tiene una ruta específica "updateStatus/{id}".
    // Esto indica que se debe llamar a este método cuando se reciba una solicitud PUT en esa ruta, donde {id}
    // es un parámetro de ruta que representa el identificador del menú que se desea actualizar.
    @PutMapping("updateStatus/{id}")
    public  ResponseEntity<MenuDTO> updateMenuStatus(@PathVariable Long id, @RequestBody Menu menu){
        try {
            //Si la actualización es exitosa, se devuelve una respuesta ResponseEntity con el estado HTTP OK
            // y el objeto MenuDTO en el cuerpo de la respuesta utilizando el método body() de ResponseEntity.
            return ResponseEntity.status(HttpStatus.OK).body(menuService.updateStatus(id, menu));
        }catch (Exception e){
            //3. Si ocurre una excepción durante la ejecución del código, se captura en el bloque catch.
            // Se crea un objeto de tipo MenuErrorDTO y se le asigna el mensaje de error de la excepción. Luego,
            // se devuelve una respuesta ResponseEntity con el estado HTTP BAD_REQUEST y el objeto MenuErrorDTO
            // en el cuerpo de la respuesta.
            MenuErrorDTO menuErrorDTO = new MenuErrorDTO();
            menuErrorDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(menuErrorDTO);
        }
    }



    // @GetMapping, lo que indica que se debe llamar cuando se reciba una solicitud GET en la ruta especificada.
    @GetMapping
    //El método tiene tres parámetros anotados con @RequestParam, que indican que se esperan parámetros en la URL de la solicitud.
    // Los parámetros son "category", "site" y "numberOfRecords".
    public ResponseEntity <List<MenuResponseDTO>> getPaginatedAndFilterMenu (
            @RequestParam () String category,
            @RequestParam () String site,
            @RequestParam () int numberOfRecords
    ) {
        try {
            // Llamamos al servicio para obtener la respuesta paginada
            Page<MenuResponseDTO> menuPages = menuService.getMenusForCategoryAndSite(category, site, numberOfRecords);

            // Creamos una instancia de PlatoRespuestaPaginadaDTO y le pasamos la lista de platos obtenida del Page
            List<MenuResponseDTO> listMenus = menuPages.getContent();
           //Se devuelve una respuesta ResponseEntity con el estado HTTP OK y la lista de menús
            // en el cuerpo de la respuesta utilizando el método body() de ResponseEntity.
            return ResponseEntity.status(HttpStatus.OK).body(listMenus);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

