package mobiauto.lojista.service;

import mobiauto.lojista.dto.Coordinates;

public interface GeolocationService {
    Coordinates getCoordinatesByCep(String cep, String numero);
    String getFullAddressByCep(String cep, String numero);
}