package mobiauto.lojista.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mobiauto.lojista.dto.*;
import mobiauto.lojista.model.Lojista;
import mobiauto.lojista.repository.LojistaRepository;
import mobiauto.lojista.util.DistanceCalculator;

@Service
@RequiredArgsConstructor
public class LojistaService {
  private final LojistaRepository repository;
  private final ViaCepClient viaCepClient;
  private final NominatimService nominatimService;

  @Transactional
  public LojistaResponseDTO cadastrarLojista(LojistaRequestDTO dto) {
    ViaCepResponse endereco = viaCepClient.buscaEndereco(dto.getCep());
    if (endereco.isErro()) {
      throw new RuntimeException("CEP não encontrado");
    }

    String enderecoCompleto = formatarEndereco(endereco, dto.getNumero());
    Coordinates coords = nominatimService.getCoordinates(enderecoCompleto).block();

    Lojista lojista = new Lojista();
    lojista.setNome(dto.getNome());
    lojista.setCep(dto.getCep());
    lojista.setNumero(dto.getNumero());
    lojista.setBairro(endereco.getBairro());
    lojista.setCidade(endereco.getLocalidade());
    lojista.setEstado(endereco.getUf());
    lojista.setLatitude(coords.getLatitude());
    lojista.setLongitude(coords.getLongitude());

    Lojista salvo = repository.save(lojista);
    return toResponseDTO(salvo);
  }

  // Busca lojistas próximos a um CEP
  public List<LojistaResponseDTO> buscarProximos(String cepCliente, String numeroCliente) {
    ViaCepResponse enderecoCliente = viaCepClient.buscaEndereco(cepCliente);
    if (enderecoCliente.isErro()) {
      throw new RuntimeException("CEP do cliente não encontrado");
    }

    String enderecoCompletoCliente = formatarEndereco(enderecoCliente, numeroCliente);
    Coordinates coordsCliente = nominatimService.getCoordinates(enderecoCompletoCliente).block();

    // 2. Calcula distância para cada lojista
    return repository.findAll().stream()
        .map(lojista -> {
          double distancia = DistanceCalculator.calculate(
              coordsCliente,
              new Coordinates(lojista.getLatitude(), lojista.getLongitude()));
          LojistaResponseDTO dto = toResponseDTO(lojista);
          dto.setDistanciaKm(distancia);
          return dto;
        })
        .sorted(Comparator.comparingDouble(LojistaResponseDTO::getDistanciaKm))
        .collect(Collectors.toList());
  }

  // --- Métodos auxiliares ---
  private String formatarEndereco(ViaCepResponse endereco, String numero) {
    return String.format("%s, %s, %s, %s, Brasil",
        endereco.getLogradouro(),
        numero,
        endereco.getBairro(),
        endereco.getLocalidade());
  }

  private LojistaResponseDTO toResponseDTO(Lojista lojista) {
    LojistaResponseDTO dto = new LojistaResponseDTO();
    dto.setId(lojista.getId());
    dto.setNomeLojista(lojista.getNome());
    dto.setEnderecoCompleto(
        String.format("%s, %s, %s, %s",
            lojista.getNumero(),
            lojista.getCidade(),
            lojista.getEstado()));
    return dto;
  }
}