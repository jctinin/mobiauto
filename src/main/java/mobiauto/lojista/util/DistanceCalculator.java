package mobiauto.lojista.util;

import mobiauto.lojista.dto.Coordinates;

public class DistanceCalculator {

  private static final double EARTH_RADIUS_KM = 6371;

  public static double calculate(Coordinates origem, Coordinates destino) {
    double latDistance = Math.toRadians(destino.getLatitude() - origem.getLatitude());
    double lonDistance = Math.toRadians(destino.getLongitude() - origem.getLongitude());

    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
        + Math.cos(Math.toRadians(origem.getLatitude()))
            * Math.cos(Math.toRadians(destino.getLatitude()))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return EARTH_RADIUS_KM * c;
  }

}
