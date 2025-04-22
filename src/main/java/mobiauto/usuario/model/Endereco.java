package mobiauto.usuario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import mobiauto.lojista.dto.Coordinates;

@Embeddable
@Data
@Schema(description = "Modelo de Endereço")
public class Endereco {

    @Schema(description = "cep do endereco", example = "102030000")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
    @NotBlank(message = "CEP não pode ser vazio")
    private String cep;

    @Schema(description = "Número do endereço", example = "123")
    @NotBlank(message = "Número não pode ser vazio")
    private String numero;

    @Schema(description = "Complemento do endereço", example = "Apto 101")
    private String complemento;

    @Schema(description = "Logradouro do endereço", example = "Rua das Flores")
    private String logradouro;

    @Schema(description = "Bairro do endereço", example = "Centro")
    private String bairro;

    @Schema(description = "Cidade do endereço", example = "São Paulo")
    private String cidade;

    @Schema(description = "Estado do endereço", example = "SP")
    private String uf;

    @Schema(description = "Latitude do endereço", example = "-23.5505")
    private Double latitude;

    @Schema(description = "Longitude do endereço", example = "-46.6333")
    private Double longitude;

    public Coordinates toCoordinates() {
        if (latitude == null || longitude == null) {
            throw new IllegalStateException("Endereço não possui coordenadas geográficas");
        }
        return new Coordinates(latitude, longitude);
    }
}