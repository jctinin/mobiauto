package mobiauto.usuario.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mobiauto.lojista.dto.Coordinates;
import mobiauto.lojista.dto.ViaCepResponse;
import mobiauto.lojista.service.GeolocationService;
import mobiauto.lojista.service.ViaCepClient;
import mobiauto.usuario.dto.UsuarioRequestDTO;
import mobiauto.usuario.dto.UsuarioResponseDTO;
import mobiauto.usuario.model.Endereco;
import mobiauto.usuario.model.Usuario;
import mobiauto.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final GeolocationService geolocationService;
  private final ViaCepClient viaCepClient;


  public UsuarioService(UsuarioRepository usuarioRepository, GeolocationService geolocationService, ViaCepClient viaCepClient) {
    this.viaCepClient = viaCepClient;
    this.geolocationService = geolocationService;
    this.usuarioRepository = usuarioRepository;
  }

  @Transactional
  public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO usuarioDTO) {
    Endereco enderecoUsuario = usuarioDTO.getEndereco();
    ViaCepResponse viaCepResponse = viaCepClient.buscaEndereco(enderecoUsuario.getCep());
    if (viaCepResponse.isErro()) {
      throw new RuntimeException("CEP não encontrado");
    }

    System.out.println("LOCALIDADE: "+ viaCepResponse.getLocalidade());

    enderecoUsuario.setLogradouro(viaCepResponse.getLogradouro());
    enderecoUsuario.setBairro(viaCepResponse.getBairro());
    enderecoUsuario.setCidade(viaCepResponse.getLocalidade());
    enderecoUsuario.setUf(viaCepResponse.getUf());

    try{
      Coordinates coords = geolocationService.getCoordinatesByCep(enderecoUsuario.getCep(), enderecoUsuario.getNumero());

      enderecoUsuario.setLatitude(coords.getLatitude());
      enderecoUsuario.setLongitude(coords.getLongitude());

      // String enderecoCompleto = geolocationService.getFullAddressByCep(enderecoUsuario.getCep(), enderecoUsuario.getNumero());

    } catch (Exception e) {
      System.err.println("Erro ao obter coordenadas: " + e.getMessage());
      throw new RuntimeException("Erro ao obter coordenadas: " + e.getMessage());
    }

    Usuario usuario = Usuario.builder()
        .nome(usuarioDTO.getNome())
        .email(usuarioDTO.getEmail())
        .endereco(enderecoUsuario)
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

  public List<Usuario> listarUsuarios() {

    try{
      List<Usuario> usersList = usuarioRepository.findAll();
      System.out.println("Lista de usuários: " + usersList);
    } catch (Exception e) {
      System.err.println("Erro ao listar usuários: " + e.getMessage());
      throw new RuntimeException("Erro ao listar usuários: " + e.getMessage());
    }

    return usuarioRepository.findAll();
  }

}
