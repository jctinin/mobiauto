package mobiauto.lojista.service;

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

@Service
@RequiredArgsConstructor
public class LojistaService {
  private final LojistaRepository lojistaRepository;
  private final ViaCepClient viaCepClient;
  private final NominatimService nominatimService;

  @Transactional
  public LojistaResponseDTO cadastrarLojista(LojistaRequestDTO dto) {
    ViaCepResponse endereco = viaCepClient.buscaEndereco(dto.getCep());
    if (endereco.isErro()) {
      throw new RuntimeException("CEP não encontrado");
    }

    if(lojistaRepository.existsByCepAndNumero(dto.getCep(), dto.getNumero())) {
      throw new RuntimeException("Lojista já cadastrado com esse CEP e número");
    }

    String enderecoCompleto = formatarEndereco(endereco, dto.getNumero());
    Coordinates coords = nominatimService.getCoordinates(enderecoCompleto).block();

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

  public List<LojistaResponseDTO> buscarLojistasProximos(String cepCliente, String numeroCliente) {
    ViaCepResponse enderecoCliente = viaCepClient.buscaEndereco(cepCliente);
    if (enderecoCliente.isErro()) {
      throw new RuntimeException("CEP do cliente não encontrado");
    }

    String enderecoCompletoCliente = formatarEndereco(enderecoCliente, numeroCliente);
    Coordinates coordsCliente = nominatimService.getCoordinates(enderecoCompletoCliente).block();

    return lojistaRepository.findAll().stream()
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
        String.format("%s, %s, %s",
            lojista.getNumero(),
            lojista.getCidade(),
            lojista.getUf()));
    return dto;
  }

  public ViaCepResponse buscaEndereco(String cep) {

    ViaCepResponse endereco = viaCepClient.buscaEndereco(cep);
    if (endereco.isErro()) {
      throw new RuntimeException("CEP não encontrado");
    }
    return endereco;

  }
}