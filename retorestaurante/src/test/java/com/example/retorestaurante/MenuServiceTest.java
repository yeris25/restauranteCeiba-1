package com.example.retorestaurante;

import com.example.retorestaurante.dto.MenuResponseDTO;
import com.example.retorestaurante.entity.Menu;
import com.example.retorestaurante.maps.MenuMap;
import com.example.retorestaurante.repository.RepositoryMenu;
import com.example.retorestaurante.services.MenuService;
import com.example.retorestaurante.validations.MenuValidation;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MenuServiceTest {

    private Menu Menu;

    private MenuResponseDTO MenuResponseDTO;
    @Mock
    RepositoryMenu MenuRepository;

    @InjectMocks
    MenuService MenuService;
    @Mock
    MenuValidation MenuValidation;

    @Mock
    MenuMap MenuMap;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Long id = 1L;
        char rol = 'A';
        String nombre = "Helado";
        Integer precio = 200;
        String descripcion = "Esta es un descripcion de prueba";
        String url = "Esra es una url de prueba";
        String categoria = "postre";
        boolean estado = true;
        String sede = "medellin";
        double tiempoPreparacion = 2.7;
    }
}
