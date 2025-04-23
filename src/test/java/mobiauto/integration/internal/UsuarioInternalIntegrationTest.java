package mobiauto.integration.internal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import mobiauto.usuario.dto.UsuarioRequestDTO;
import mobiauto.usuario.model.Endereco;
import mobiauto.usuario.service.UsuarioService;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioInternalIntegrationTest {

  @Autowired
  private UsuarioService usuarioService;

  @Test
  void criarUsuarioDevePersistirECalcularCoordenadas() {

    Endereco endereco = Endereco.builder()
        .cep("01311000")
        .numero("100")
        .build();

    UsuarioRequestDTO request = UsuarioRequestDTO.builder()
        .nome("Teste Integração")
        .email("test@integration.com")
        .endereco(endereco)
        .build();

    var response = usuarioService.criarUsuario(request);

    assertNotNull(response.getId());
    assertNotNull(response.getEndereco().getLatitude());
    assertNotNull(response.getEndereco().getLongitude());
  }

}
