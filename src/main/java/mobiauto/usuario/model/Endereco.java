package mobiauto.usuario.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import mobiauto.lojista.dto.Coordinates;

@Embeddable
@Data
public class Endereco {
    private String cep;
    private String numero;
    private String complemento;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private Double latitude;
    private Double longitude;

    public Coordinates toCoordinates() {
        if (latitude == null || longitude == null) {
            throw new IllegalStateException("Endereço não possui coordenadas geográficas");
        }
        return new Coordinates(latitude, longitude);
    }
}