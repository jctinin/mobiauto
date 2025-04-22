package mobiauto.usuario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import mobiauto.usuario.model.Endereco;

@Data
@Builder
@Schema(description = "Dados de requisição para criação de um usuário")
public class UsuarioRequestDTO {
  @Schema(description = "Nome do usuário", example = "João da Silva")
  private String nome;

  @Email
  @Schema(description = "email do usuário", example = "joão@email.com", required = true)
  private String email;

  @Valid
  @Schema(description = "Endereço do usuário")
  private Endereco endereco;

}
