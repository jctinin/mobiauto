package mobiauto.usuario.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mobiauto.lojista.dto.Coordinates;
import mobiauto.lojista.service.GeolocationService;
import mobiauto.usuario.dto.UsuarioRequestDTO;
import mobiauto.usuario.dto.UsuarioResponseDTO;
import mobiauto.usuario.model.Endereco;
import mobiauto.usuario.model.Usuario;
import mobiauto.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final GeolocationService geolocationService;

  public UsuarioService(UsuarioRepository usuarioRepository, GeolocationService geolocationService) {
    this.geolocationService = geolocationService;
    this.usuarioRepository = usuarioRepository;
  }

  @Transactional
  public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO usuarioDTO) {
    Endereco enderecoUsuario = usuarioDTO.getEndereco();

    try{
      Coordinates coords = geolocationService.getCoordinatesByCep(enderecoUsuario.getCep(), enderecoUsuario.getNumero());

      enderecoUsuario.setLatitude(coords.getLatitude());
      enderecoUsuario.setLongitude(coords.getLongitude());

    } catch (Exception e) {
      System.err.println("Erro ao obter coordenadas: " + e.getMessage());
      throw new RuntimeException("Erro ao obter coordenadas: " + e.getMessage());
    }

    Usuario usuario = Usuario.builder()
        .nome(usuarioDTO.getNome())
        .email(usuarioDTO.getEmail())
        .endereco(usuarioDTO.getEndereco())
        .build();

    Usuario usuarioSalvo = usuarioRepository.save(usuario);

    UsuarioResponseDTO usuarioResponseDTO = UsuarioResponseDTO.builder()
        .id(usuarioSalvo.getId())
        .nome(usuarioSalvo.getNome())
        .email(usuarioSalvo.getEmail())
        .endereco(usuarioSalvo.getEndereco())
        .build();

    return usuarioResponseDTO;

  }

}
