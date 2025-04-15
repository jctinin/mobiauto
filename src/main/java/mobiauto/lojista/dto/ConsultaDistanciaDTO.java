package mobiauto.lojista.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ConsultaDistanciaDTO {

  @NotBlank(message = "CEP não pode ser vazio")
  @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
  private String cep;

}
