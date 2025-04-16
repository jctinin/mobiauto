package mobiauto.lojista.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class LojistaRequestDTO {

    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    @NotBlank(message = "CEP não pode ser vazio")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
    private String cep;

    @NotBlank(message = "Número não pode ser vazio")
    private String numero;

}
