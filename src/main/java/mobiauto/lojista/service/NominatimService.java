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

  @Cacheable(value = "coordinatesCache", key = "{#address}")
  public Mono<Coordinates> getCoordinates(String address) {

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
        .map(result -> new Coordinates(Double.parseDouble(result.getLat()),
            Double.parseDouble(result.getLon())))
        .switchIfEmpty(Mono.error(new RuntimeException("Endereço não encontrado: " + address)));

  }

}
