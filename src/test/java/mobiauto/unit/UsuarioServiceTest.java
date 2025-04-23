package mobiauto.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mobiauto.lojista.dto.Coordinates;
import mobiauto.lojista.dto.ViaCepResponse;
import mobiauto.lojista.service.GeolocationService;
import mobiauto.lojista.service.ViaCepClient;
import mobiauto.usuario.dto.UsuarioRequestDTO;
import mobiauto.usuario.dto.UsuarioResponseDTO;
import mobiauto.usuario.model.Endereco;
import mobiauto.usuario.model.Usuario;
import mobiauto.usuario.repository.UsuarioRepository;
import mobiauto.usuario.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

  @Mock
  private UsuarioRepository usuarioRepository;

  @Mock
  private GeolocationService geolocationService;

  @Mock
  private ViaCepClient viaCepClient;

  @InjectMocks
  private UsuarioService usuarioService;

  @Test
  @DisplayName("Testando o método de criar usuário")
  public void criarUsuarioDeveRetornarUsuarioCriadoTest() {
    UsuarioRequestDTO usuarioRequestDTO = criarRequestValido();

    ViaCepResponse viaCepResponse = ViaCepResponse.builder()
        .cep("01311000")
        .logradouro("Avenida Paulista")
        .complemento("Apto 100")
        .bairro("Bela Vista")
        .localidade("São Paulo")
        .estado("SP")
        .uf("SP")
        .erro(false)
        .build();

    when(viaCepClient.buscaEndereco("01311000"))
        .thenReturn(viaCepResponse);

    Coordinates coords = new Coordinates(-23.5631, -46.6562);
    when(geolocationService.getCoordinatesByCep(any(), any()))
        .thenReturn(coords);

    when(usuarioRepository.save(any(Usuario.class)))
        .thenAnswer(invocation -> {
          Usuario usuario = invocation.getArgument(0);
          usuario.setId(1L);
          return usuario;
        });

    UsuarioResponseDTO response = usuarioService.criarUsuario(usuarioRequestDTO);

    assertEquals("Julio Silva", response.getNome());
    assertEquals("julio@email.com", response.getEmail());
    assertEquals("Avenida Paulista", response.getEndereco().getLogradouro());
    assertEquals(-23.5631, response.getEndereco().getLatitude());
  }

  private UsuarioRequestDTO criarRequestValido() {
    return UsuarioRequestDTO.builder()
        .nome("Julio Silva")
        .email("julio@email.com")
        .endereco(Endereco.builder()
            .cep("01311000")
            .numero("100")
            .build())
        .build();
  }



}
