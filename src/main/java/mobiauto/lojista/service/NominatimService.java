package mobiauto.lojista.service;

import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import mobiauto.lojista.dto.Coordinates;
import mobiauto.lojista.dto.NominatimResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NominatimService {

  private final WebClient webClient;

  public NominatimService() {
    this.webClient = WebClient.builder()
        .baseUrl("https://nominatim.openstreetmap.org")
        .defaultHeader("User-Agent", "Mobiauto")
        .build();
  }

  @Cacheable(value = "coordinatesCache", key = "{#cep, #numero}")
  public Mono<Coordinates> getCoordinates(String cep, String numero) {
    String address = String.format("%s, %s, Brasil", numero, cep);


    return webClient.get()
        .uri(urlBuilder -> urlBuilder
            .path("/search")
            .queryParam("format", "json")
            .queryParam("q", address)
            .build())
        .retrieve()
        .bodyToMono(NominatimResponse[].class)
        .flatMapMany(Flux::fromArray)
        .next()
        .map(response -> new Coordinates(
                    Double.parseDouble(response.getLat()),
                    Double.parseDouble(response.getLon())
        ))
        .switchIfEmpty(Mono.error(new RuntimeException("Endereço não encontrado: " + address)));

  }
      // private Endereco mapearParaEndereco(NominatimResponse response) {
      //   Endereco endereco = new Endereco();
      //   endereco.setLatitude(Double.parseDouble(response.getLat()));
      //   endereco.setLongitude(Double.parseDouble(response.getLon()));

      //   String[] parts = response.getDisplayName().split(",");
      //   if (parts.length > 0) {
      //       endereco.setLogradouro(parts[0].trim());
      //   }
      //   if (parts.length > 1) {
      //       endereco.setBairro(parts[1].trim());
      //   }

      //   return endereco;

      // }


}
