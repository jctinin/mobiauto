package mobiauto.lojista.service;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mobiauto.lojista.dto.*;
import mobiauto.lojista.mapper.LojistaMapper;
import mobiauto.lojista.model.Lojista;
import mobiauto.lojista.repository.LojistaRepository;
import mobiauto.lojista.util.DistanceCalculator;
import mobiauto.usuario.model.Usuario;
import mobiauto.usuario.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class LojistaService {
  private final LojistaRepository lojistaRepository;
  private final UsuarioRepository usuarioRepository;
  private final ViaCepClient viaCepClient;
  private final GeolocationService geolocationService;

  @Transactional
  public LojistaResponseDTO cadastrarLojista(LojistaRequestDTO dto) {
    ViaCepResponse endereco = viaCepClient.buscaEndereco(dto.getCep());
    if (endereco.isErro()) {
      throw new RuntimeException("CEP não encontrado");
    }

    if (lojistaRepository.existsByCepAndNumero(dto.getCep(), dto.getNumero())) {
      throw new RuntimeException("Lojista já cadastrado com esse CEP e número");
    }

    Coordinates coords = geolocationService.getCoordinatesByCep(dto.getCep(), dto.getNumero());

    Lojista lojista = Lojista.builder()
        .nome(dto.getNome())
        .cep(dto.getCep())
        .numero(dto.getNumero())
        .bairro(endereco.getBairro())
        .cidade(endereco.getLocalidade())
        .uf(endereco.getUf())
        .latitude(coords.getLatitude())
        .longitude(coords.getLongitude())
        .build();
    ;

    Lojista salvo = lojistaRepository.save(lojista);
    return LojistaMapper.toDTO(salvo);
  }

  public List<LojistaDistanciaDTO> buscarLojistasProximos(Long usuarioId) {
    if (usuarioId == null) {
      throw new RuntimeException("ID do usuário não pode ser nulo");
    }

    Usuario usuario = usuarioRepository.findById(usuarioId)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    return calcularLojistasProximos(usuario.getEndereco().toCoordinates());

  }

  public List<LojistaDistanciaDTO> buscarLojistasProximos(String cepCliente, String numeroCliente) {

    Coordinates coords = geolocationService.getCoordinatesByCep(cepCliente, numeroCliente);
    return calcularLojistasProximos(coords);

  }

  private List<LojistaDistanciaDTO> calcularLojistasProximos(Coordinates origem) {

    return lojistaRepository.findAll().stream()
        .map(lojista -> {
          Double distancia = DistanceCalculator.calculate(
              origem,
              new Coordinates(lojista.getLatitude(), lojista.getLongitude()));
          return LojistaMapper.toDistanciaDTO(lojista, distancia);
        })
        .sorted(Comparator.comparingDouble(lojista -> {
          DecimalFormat df = new DecimalFormat("#.##");
          String resultado = df.format(lojista.getDistanciaKm());
          Double distancia = Double.parseDouble(resultado);
          lojista.setDistanciaKm(distancia);
          return (Double.parseDouble(resultado));
        }))
        .collect(Collectors.toList());
  }


  public ViaCepResponse buscaEndereco(String cep) {

    ViaCepResponse endereco = viaCepClient.buscaEndereco(cep);
    if (endereco.isErro()) {
      throw new RuntimeException("CEP não encontrado");
    }
    return endereco;

  }
}