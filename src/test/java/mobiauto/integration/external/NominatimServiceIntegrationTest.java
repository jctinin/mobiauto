package mobiauto.integration.external;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mobiauto.lojista.dto.Coordinates;
import mobiauto.lojista.service.NominatimService;
import reactor.core.publisher.Mono;

@SpringBootTest
public class NominatimServiceIntegrationTest {

   @Autowired
    private NominatimService nominatimService;

    @Test
    void getCoordinatesDeveRetornarCoordenadasValidas() {
        Mono<Coordinates> result = nominatimService.getCoordinates("01311000", "1000");
        Coordinates coords = result.block();

        assertNotNull(coords);
        assertTrue(coords.getLatitude() > -24 && coords.getLatitude() < -23);
        assertTrue(coords.getLongitude() > -47 && coords.getLongitude() < -46);
    }

}
