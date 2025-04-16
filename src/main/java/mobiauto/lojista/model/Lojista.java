package mobiauto.lojista.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Lojista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nome;

    @Column(length = 8)
    @Pattern(regexp = "\\d{8}")
    private String cep;
    private String cidade;
    private String uf;

    @NotBlank
    private String numero;
    private String complemento;
    private String bairro;


    @NotNull
    @Column(name = "longitude")
    private Double longitude;
    @NotNull
    @Column(name = "latitude")
    private Double latitude;

}
