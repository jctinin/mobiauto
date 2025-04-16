package mobiauto.lojista.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import mobiauto.lojista.dto.ViaCepResponse;

@FeignClient(name = "viaCepClient", url = "https://viacep.com.br/ws")
public interface ViaCepClient {

  @GetMapping("/{cep}/json")
  ViaCepResponse buscaEndereco(@PathVariable String cep);

}
