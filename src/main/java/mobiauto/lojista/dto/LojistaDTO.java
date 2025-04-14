package mobiauto.lojista.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class LojistaDTO {

    private Long id;
    private String nome;
    private String cep;
    private String cidade;
    private String estado;
    private String numero;
    private String complemento;
    private String bairro;
    private String longitude;
    private String latitude;


}
