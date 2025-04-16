package mobiauto.lojista.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LojistaDistanciaDTO {

  private Long id;
  private String nome;
  private String endereco;
  private double distanciaKm;

}
