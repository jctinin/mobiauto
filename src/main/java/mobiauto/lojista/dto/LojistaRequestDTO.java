package mobiauto.lojista.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Schema(description = "Modelo de Lojista")
public class LojistaRequestDTO {

    @Schema(description="Nome do lojista", example = "Lojista Exemplo")
    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    @Schema(description= "cep do lojista", example = "10203000")
    @NotBlank(message = "CEP não pode ser vazio")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
    private String cep;

    @Schema(description = "Número do endereço", example = "123")
    @NotBlank(message = "Número não pode ser vazio")
    private String numero;

}
