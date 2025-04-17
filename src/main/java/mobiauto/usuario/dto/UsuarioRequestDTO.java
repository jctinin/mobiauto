package mobiauto.usuario.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import mobiauto.usuario.model.Endereco;

@Data
@Builder
public class UsuarioRequestDTO {
  @NotBlank
  private String nome;

  @Email
  private String email;

  @Valid
  private Endereco endereco;

}
