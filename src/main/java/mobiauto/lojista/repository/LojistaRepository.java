package mobiauto.lojista.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mobiauto.lojista.model.Lojista;

@Repository
public interface LojistaRepository extends JpaRepository<Lojista, Long> {

   List<Lojista> findByUf(String uf);

   boolean existsByCepAndNumero(String cep, String numero);
}

