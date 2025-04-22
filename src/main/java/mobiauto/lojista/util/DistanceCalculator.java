package mobiauto.lojista.util;

import mobiauto.lojista.dto.Coordinates;
import mobiauto.usuario.model.Endereco;

public class DistanceCalculator {

  private static final double EARTH_RADIUS_KM = 6371;

  public static double calculate(Coordinates origem, Coordinates destino) {
    double lat1 = Math.toRadians(origem.getLatitude());
    double lon1 = Math.toRadians(origem.getLongitude());
    double lat2 = Math.toRadians(destino.getLatitude());
    double lon2 = Math.toRadians(destino.getLongitude());

    double dLat = lat2 - lat1;
    double dLon = lon2 - lon1;

    double a = Math.pow(Math.sin(dLat / 2), 2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.pow(Math.sin(dLon / 2), 2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return EARTH_RADIUS_KM * c;
  }

  public static Coordinates toCoordinates(Endereco endereco) {
    if (endereco == null) {
      throw new IllegalArgumentException("Endereço não pode ser nulo");
    }
    if (endereco.getLatitude() == null || endereco.getLongitude() == null) {
      throw new IllegalArgumentException("Endereço não possui coordenadas válidas");
    }
    return new Coordinates(endereco.getLatitude(), endereco.getLongitude());
  }

}
