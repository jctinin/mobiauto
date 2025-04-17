package mobiauto.lojista.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mobiauto.lojista.dto.Coordinates;
import mobiauto.lojista.dto.ViaCepResponse;

@Service
@RequiredArgsConstructor
public class GeolocationServiceImpl implements GeolocationService {

  private final ViaCepClient viaCepClient;
  private final NominatimService nominatimService;


  @Override
  @Cacheable(value = "coordinatesCache", key = "{#cep, #numero}")
  public Coordinates getCoordinatesByCep(String cep, String numero) {
        return nominatimService.getCoordinates(cep, numero)
                .blockOptional()
                .orElseThrow(() -> new RuntimeException("Falha ao obter coordenadas"));
}
    @Override
    @Cacheable(value = "addressCache", key = "{#cep, #numero}")
    public String getFullAddressByCep(String cep, String numero) {
        ViaCepResponse endereco = viaCepClient.buscaEndereco(cep);
        if (endereco.isErro()) {
            throw new RuntimeException("CEP não encontrado: " + cep);
        }

        return String.format("%s, %s, %s, %s, Brasil",
                endereco.getLogradouro(),
                numero,
                endereco.getBairro(),
                endereco.getLocalidade());
    }

}

