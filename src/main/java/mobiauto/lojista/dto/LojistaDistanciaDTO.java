package mobiauto.lojista.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Representa um lojista")
public class LojistaDistanciaDTO {

  @Schema(description = "ID do lojista", example = "1")
  private Long id;

  @Schema(description = "Nome do lojista", example = "Lojista Exemplo")
  private String nome;

  @Schema(description = "Nome do usuário", example = "João da Silva")
  private String endereco;

  @Schema(description = "Distância em km", example = "10.5")
  private Double distanciaKm;

}
