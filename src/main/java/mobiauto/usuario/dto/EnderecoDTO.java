package mobiauto.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class EnderecoDTO {

  @Pattern(regexp = "\\d{8}")
  String cep;

  @NotBlank
  String numero;

  String complemento;
}
