package mobiauto.lojista.model;

import javax.persistence.*;

import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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
    private String estado;

    @NotBlank
    private String numero;
    private String complemento;
    private String bairro;


    @NotNull
    @Column(name = "longitude")
    private String longitude;
    @NotNull
    @Column(name = "latitude")
    private String latitude;

}
