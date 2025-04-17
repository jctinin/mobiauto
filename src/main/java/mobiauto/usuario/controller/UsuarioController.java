package mobiauto.usuario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mobiauto.usuario.dto.UsuarioRequestDTO;
import mobiauto.usuario.dto.UsuarioResponseDTO;
import mobiauto.usuario.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

  private UsuarioService usuarioService;

  public UsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @PostMapping("/cadastrar")
  public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioDTO) {

    UsuarioResponseDTO usuarioResponseDTO = usuarioService.criarUsuario(usuarioDTO);

    return ResponseEntity.ok(usuarioResponseDTO);

  }

}
