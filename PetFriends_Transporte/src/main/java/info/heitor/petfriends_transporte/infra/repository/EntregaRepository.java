package info.heitor.petfriends_transporte.infra.repository;

import info.heitor.petfriends_transporte.domain.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
}
