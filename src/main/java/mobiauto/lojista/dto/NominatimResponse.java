package mobiauto.lojista.dto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NominatimResponse {
    private String lat;
    private String lon;
    private String displayName;

    public NominatimResponse(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
