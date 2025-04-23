package mobiauto.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mobiauto.lojista.dto.*;
import mobiauto.lojista.model.Lojista;
import mobiauto.lojista.repository.LojistaRepository;
import mobiauto.lojista.service.GeolocationService;
import mobiauto.lojista.service.LojistaService;
import mobiauto.lojista.service.ViaCepClient;
import mobiauto.usuario.model.Endereco;
import mobiauto.usuario.model.Usuario;
import mobiauto.usuario.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class LojistaServiceTest {

    @Mock
    private LojistaRepository lojistaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ViaCepClient viaCepClient;

    @Mock
    private GeolocationService geolocationService;

    @InjectMocks
    private LojistaService lojistaService;

    @Test
    void cadastrarLojista_shouldSuccess() {

      ViaCepResponse viaCepResponse = ViaCepResponse.builder()
            .logradouro("Rua Teste")
            .bairro("Bairro Teste")
            .localidade("Cidade Teste")
            .uf("TS")
            .erro(false)
            .build();

        Coordinates coords = new Coordinates(-23.5, -46.6);

        when(viaCepClient.buscaEndereco(anyString())).thenReturn(viaCepResponse);
        when(geolocationService.getCoordinatesByCep(anyString(), anyString())).thenReturn(coords);
        when(lojistaRepository.existsByCepAndNumero(anyString(), anyString())).thenReturn(false);
        when(lojistaRepository.save(any(Lojista.class))).thenAnswer(inv -> {
            Lojista l = inv.getArgument(0);
            l.setId(1L);
            return l;
        });

        LojistaRequestDTO request = new LojistaRequestDTO();
        request.setNome("Lojista Teste");
        request.setCep("01311000");
        request.setNumero("100");

        LojistaResponseDTO response = lojistaService.cadastrarLojista(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Lojista Teste", response.getNomeLojista());

    }

    @Test
    void cadastrarLojista_shouldThrowWhenCepNotFound() {
        ViaCepResponse viaCepResponse = ViaCepResponse.builder().erro(true).build();
        when(viaCepClient.buscaEndereco(anyString())).thenReturn(viaCepResponse);

        LojistaRequestDTO request = new LojistaRequestDTO();
        request.setCep("00000000");

        assertThrows(RuntimeException.class, () ->
            lojistaService.cadastrarLojista(request));
    }

    @Test
    void cadastrarLojista_shouldThrowWhenDuplicateCepNumero() {
        ViaCepResponse viaCepResponse = ViaCepResponse.builder().erro(false).build();
        when(viaCepClient.buscaEndereco(anyString())).thenReturn(viaCepResponse);
        when(lojistaRepository.existsByCepAndNumero(anyString(), anyString())).thenReturn(true);

        LojistaRequestDTO request = new LojistaRequestDTO();
        request.setCep("01311000");
        request.setNumero("100");

        assertThrows(RuntimeException.class, () ->
            lojistaService.cadastrarLojista(request));
    }

    @Test
    void buscarLojistasProximosByUsuarioId_shouldSuccess() {

      Usuario usuario = new Usuario();
        usuario.setId(1L);
        Endereco endereco = new Endereco();
        endereco.setLatitude(-23.5);
        endereco.setLongitude(-46.6);
        usuario.setEndereco(endereco);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));


        Lojista lojista1 = new Lojista();
        lojista1.setLatitude(-23.51);
        lojista1.setLongitude(-46.61);

        Lojista lojista2 = new Lojista();
        lojista2.setLatitude(-23.52);
        lojista2.setLongitude(-46.62);

        when(lojistaRepository.findAll()).thenReturn(List.of(lojista1, lojista2));


        List<LojistaDistanciaDTO> result = lojistaService.buscarLojistasProximos(1L);


        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0).getDistanciaKm() < result.get(1).getDistanciaKm());
    }


    @Test
    void buscarLojistasProximosByCep_shouldSuccess() {

      Coordinates coords = new Coordinates(-23.5, -46.6);
        when(geolocationService.getCoordinatesByCep(anyString(), anyString())).thenReturn(coords);


        Lojista lojista = new Lojista();
        lojista.setLatitude(-23.51);
        lojista.setLongitude(-46.61);
        when(lojistaRepository.findAll()).thenReturn(List.of(lojista));


        List<LojistaDistanciaDTO> result = lojistaService.buscarLojistasProximos("01311000", "100");


        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getDistanciaKm() > 0);
    }

    @Test
    void buscarLojistasProximos_shouldThrowWhenUsuarioNotFound() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
            lojistaService.buscarLojistasProximos(1L));
    }

    @Test
    void buscaEndereco_shouldReturnViaCepResponse() {
        ViaCepResponse expected = ViaCepResponse.builder()
            .logradouro("Rua Teste")
            .erro(false)
            .build();

        when(viaCepClient.buscaEndereco(anyString())).thenReturn(expected);

        ViaCepResponse result = lojistaService.buscaEndereco("01311000");

        assertEquals(expected, result);
    }
}