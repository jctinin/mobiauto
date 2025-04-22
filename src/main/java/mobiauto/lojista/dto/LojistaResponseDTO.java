package mobiauto.lojista.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Representa um lojista")
public class LojistaResponseDTO {

    @Schema(description = "ID do lojista", example = "1")
    private Long id;

    @Schema(description = "Nome do lojista", example = "Lojista Exemplo")
    private String nomeLojista;

    @Schema(description = "Nome do usuário", example = "João da Silva")
    private String enderecoCompleto;

    @Schema(description = "cep do lojista", example = "10203000")
    private Double distanciaKm;

}
