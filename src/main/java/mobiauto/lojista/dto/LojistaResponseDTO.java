package mobiauto.lojista.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LojistaResponseDTO {

    private Long id;
    private String nomeLojista;
    private String enderecoCompleto;
    private Double distanciaKm;

}
