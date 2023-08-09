package com.example.retorestaurante.controller;

import com.example.retorestaurante.dto.MenuDTO;
import com.example.retorestaurante.dto.MenuErrorDTO;
import com.example.retorestaurante.dto.MenuResponseDTO;
import com.example.retorestaurante.entity.Menu;
import com.example.retorestaurante.services.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    //Primer historia
    @Operation(summary = "Create a menu")
    @ApiResponse(responseCode = "201", description = "Menu created successfully", content = @Content(schema = @Schema(implementation = MenuDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = MenuErrorDTO.class)))
    @PostMapping("/createMenu")
    public ResponseEntity<MenuDTO> createMenu(@RequestBody Menu dataMenu) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(menuService.createMenu(dataMenu));
        }catch (Exception e){
            MenuErrorDTO menuErrorDTO = new MenuErrorDTO();
            menuErrorDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(menuErrorDTO);
        }
    }

    //Actualizar el plato 2
    @Operation(summary = "Update a menu by ID")
    @ApiResponse(responseCode = "200", description = "Menu updated successfully", content = @Content(schema = @Schema(implementation = MenuDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = MenuErrorDTO.class)))
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


    //Historia 3
    @Operation(summary = "Update menu status by ID")
    @ApiResponse(responseCode = "200", description = "Menu status updated successfully", content = @Content(schema = @Schema(implementation = MenuDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = MenuErrorDTO.class)))
    @PutMapping("updateStatus/{id}")
    public  ResponseEntity<MenuDTO> updateMenuStatus(@PathVariable Long id, @RequestBody Menu menu){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(menuService.updateStatus(id, menu));
        }catch (Exception e){
            MenuErrorDTO menuErrorDTO = new MenuErrorDTO();
            menuErrorDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(menuErrorDTO);
        }
    }



   //Historia 4
    @Operation(summary = "Get paginated and filtered menus")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = MenuResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = MenuErrorDTO.class)))

    @GetMapping
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

            return ResponseEntity.status(HttpStatus.OK).body(listMenus);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //4

    @GetMapping("getForCategory")
    public ResponseEntity <List<MenuResponseDTO>>getMenusForCategory(
            @RequestParam () String category,
            @RequestParam () int numberOfRecords
    ) {
        try {
            // Llamamos al servicio para obtener la respuesta paginada
            Page<MenuResponseDTO> menuPages = menuService.getMenusForCategory(category, numberOfRecords);

            // Creamos una instancia de PlatoRespuestaPaginadaDTO y le pasamos la lista de platos obtenida del Page
            List<MenuResponseDTO> listMenus = menuPages.getContent();

            return ResponseEntity.status(HttpStatus.OK).body(listMenus);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //4
    @GetMapping("getForSite")
    public ResponseEntity <List<MenuResponseDTO>>getMenusForSite(
            @RequestParam () String site,
            @RequestParam () int numberOfRecords
    ) {
        try {
            // Llamamos al servicio para obtener la respuesta paginada
            Page<MenuResponseDTO> menuPages = menuService.getMenusForSite(site, numberOfRecords);

            // Creamos una instancia de PlatoRespuestaPaginadaDTO y le pasamos la lista de platos obtenida del Page
            List<MenuResponseDTO> listMenus = menuPages.getContent();

            return ResponseEntity.status(HttpStatus.OK).body(listMenus);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

