package mobiauto.usuario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import mobiauto.usuario.model.Endereco;

@Data
@Builder
@Schema(description = "Representa um usuário")
public class UsuarioResponseDTO {

  @Schema(description = "ID do usuário", example = "1")
  private Long id;

  @Schema(description = "Nome do usuário", example = "João da Silva")
  private String nome;

  @Schema(description = "email do usuário", example = "joao@email.com")
  private String email;

  @Schema(description = "Endereço do usuário", example = "Rua das Flores, 123")
  private Endereco endereco;

}
