package info.heitor.petfriends_almoxarifado.infra.repository;

import info.heitor.petfriends_almoxarifado.domain.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Estoque findByProductId(Long produtoId);
}
