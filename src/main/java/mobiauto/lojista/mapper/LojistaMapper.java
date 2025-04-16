package mobiauto.lojista.mapper;

import mobiauto.lojista.dto.LojistaResponseDTO;
import mobiauto.lojista.model.Lojista;

public class LojistaMapper {


  public static LojistaResponseDTO toDTO(Lojista lojista) {
        return LojistaResponseDTO.builder()
            .id(lojista.getId())
            .nome(lojista.getNome())
            .endereco(formatarEndereco(lojista))
            .build();
    }


    public static LojistaDistanciaDTO toDistanciaDTO(Lojista lojista, double distancia) {
      return LojistaDistanciaDTO.builder()
          .id(lojista.getId())
          .nome(lojista.getNome())
          .endereco(formatarEndereco(lojista))
          .distanciaKm(distancia)
          .build();
  }

  private static String formatarEndereco(Lojista lojista) {
    return String.format("%s, %s - %s, %s",
        lojista.getNumero(),
        lojista.getBairro(),
        lojista.getCidade(),
        lojista.getUf());
}

}
