package mobiauto.lojista.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Schema(description = "Modelo de resposta do ViaCep")
@AllArgsConstructor
@NoArgsConstructor
public class ViaCepResponse {

  @Schema(description = "cep do lojista", example = "10203000")
  private String cep;

  @Schema(description = "Logradouro do lojista", example = "Rua das Flores")
  private String logradouro;

  @Schema(description = "Complemento do lojista", example = "Apto 101")
  private String complemento;

  @Schema(description = "Bairro do lojista", example = "Centro")
  private String bairro;

  @Schema(description = "Cidade do lojista", example = "São Paulo")
  private String localidade;

  @Schema(description = "Estado do lojista", example = "SP")
  private String estado;

  @Schema(description = "Unidade federativa do lojista", example = "SP")
  private String uf;

  @Schema(description = "Erro ao buscar o cep", example = "false")
  private boolean erro;

}
