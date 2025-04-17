package mobiauto.usuario.dto;

import lombok.Builder;
import lombok.Data;
import mobiauto.usuario.model.Endereco;

@Data
@Builder
public class UsuarioResponseDTO{
private Long id;
private String nome;
private String email;
private Endereco endereco;

}
