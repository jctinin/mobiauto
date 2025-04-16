package mobiauto.lojista.dto;

import lombok.Data;

@Data
public class NominatimResponse {
    private String lat;
    private String lon;
    private String display_name;
}
