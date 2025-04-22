package mobiauto.usuario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mobiauto.usuario.dto.UsuarioRequestDTO;
import mobiauto.usuario.dto.UsuarioResponseDTO;
import mobiauto.usuario.model.Usuario;
import mobiauto.usuario.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuario", description = "Endpoints para gerenciamento de usuários")
public class UsuarioController {

  private UsuarioService usuarioService;

  public UsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @Operation(summary = "Cadastrar Usuário")
  @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso")
  @ApiResponse(responseCode = "400", description = "Erro ao cadastrar usuário")
  @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
  @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  @PostMapping("/cadastrar")
  public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioDTO) {

    UsuarioResponseDTO usuarioResponseDTO = usuarioService.criarUsuario(usuarioDTO);

    return ResponseEntity.ok(usuarioResponseDTO);

  }

  @Operation(summary = "Listar Usuários")
  @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso")
  @ApiResponse(responseCode = "400", description = "Erro ao buscar usuários")
  @ApiResponse(responseCode = "404", description = "Usuários não encontrados")
  @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  @GetMapping("/")
  public List<Usuario> listaUsuarios() {
      List<Usuario> usersList = usuarioService.listarUsuarios();
      return usersList;
  }


}
