package mobiauto.lojista.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mobiauto.lojista.dto.LojistaDistanciaDTO;
import mobiauto.lojista.dto.LojistaRequestDTO;
import mobiauto.lojista.dto.LojistaResponseDTO;
import mobiauto.lojista.dto.ViaCepResponse;
import mobiauto.lojista.service.LojistaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/lojista")
@Tag(name = "Lojista", description = "Endpoints para gerenciamento de lojistas")
public class LojistaController {

    @Autowired
    private LojistaService lojistaService;

    @Operation(summary = "Cadastrar Lojista")
    @ApiResponse(responseCode = "200", description = "Lojista cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao cadastrar lojista")
    @ApiResponse(responseCode = "404", description = "Lojista não encontrado")
    @PostMapping("/cadastrar")
    public ResponseEntity<LojistaResponseDTO> cadastrarLojista(@RequestBody LojistaRequestDTO dto) {
        LojistaResponseDTO response = lojistaService.cadastrarLojista(dto);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Buscar Lojista por CEP")
    @ApiResponse(responseCode = "200", description = "Lojista encontrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao buscar lojista")
    @ApiResponse(responseCode = "404", description = "Lojista não encontrado")
    @GetMapping("/cep/{cep}")
    public ResponseEntity<ViaCepResponse> buscaEndereco(@PathVariable String cep) {
        ViaCepResponse response = lojistaService.buscaEndereco(cep);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar Lojistas próximos")
    @ApiResponse(responseCode = "200", description = "Lojistas encontrados com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao buscar lojistas")
    @ApiResponse(responseCode = "404", description = "Lojistas não encontrados")
    @GetMapping("/proximos")
    public ResponseEntity<List<LojistaDistanciaDTO>> listarLojistasProximos(@RequestParam String cep, @RequestParam String numero) {

        List<LojistaDistanciaDTO> lojistasProximos = lojistaService.buscarLojistasProximos(cep, numero);
        return ResponseEntity.ok(lojistasProximos);
    }

    @Operation(summary = "Listar Lojistas próximos por ID de usuário")
    @ApiResponse(responseCode = "200", description = "Lojistas encontrados com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao buscar lojistas")
    @ApiResponse(responseCode = "404", description = "Lojistas não encontrados")
    @GetMapping("/proximos/{usuarioId}")
    public ResponseEntity<List<LojistaDistanciaDTO>> listarLojistasProximos(@PathVariable Long usuarioId) {

        List<LojistaDistanciaDTO> lojistasProximos = lojistaService.buscarLojistasProximos(usuarioId);
        return ResponseEntity.ok(lojistasProximos);
    }

}
