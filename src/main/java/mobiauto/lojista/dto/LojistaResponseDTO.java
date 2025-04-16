package mobiauto.lojista.dto;

import lombok.Data;

@Data
public class LojistaResponseDTO {

    private Long id;
    private String nomeLojista;
    private String enderecoCompleto;
    private Double distanciaKm;

}
