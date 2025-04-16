package mobiauto.lojista.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mobiauto.lojista.dto.LojistaRequestDTO;
import mobiauto.lojista.dto.LojistaResponseDTO;
import mobiauto.lojista.dto.ViaCepResponse;
import mobiauto.lojista.service.LojistaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/lojista")
public class LojistaController {

    @Autowired
    private LojistaService lojistaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<LojistaResponseDTO> cadastrarLojista(@RequestBody LojistaRequestDTO dto) {
        LojistaResponseDTO response = lojistaService.cadastrarLojista(dto);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/cep/{cep}")
    public ResponseEntity<ViaCepResponse> buscaEndereco(@PathVariable String cep) {
        ViaCepResponse response = lojistaService.buscaEndereco(cep);
        return ResponseEntity.ok(response);
    }

}
