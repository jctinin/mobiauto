package mobiauto.integration.external;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mobiauto.lojista.dto.ViaCepResponse;
import mobiauto.lojista.service.ViaCepClient;

@SpringBootTest
public class ViaCepClientTest {

  @Autowired
  private ViaCepClient viaCepClient;

  @Test
  void buscaEnderecoDeveRetornarEnderecoValido() {
    ViaCepResponse response = viaCepClient.buscaEndereco("01311000");

    assertFalse(response.isErro());
    assertEquals("Avenida Paulista", response.getLogradouro());
    assertEquals("São Paulo", response.getLocalidade());
  }

  @Test
  void buscaEnderecoDeveRetornarErroParaCepInvalido() {
    ViaCepResponse response = viaCepClient.buscaEndereco("00000000");

    assertTrue(response.isErro());
  }

}
