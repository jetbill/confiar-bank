package co.com.confiar.bank_demo.repository;

import co.com.confiar.bank_demo.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository  extends JpaRepository<Client, String> {

    Boolean existsByNit(String nit);
    Optional<Client> findByNit(String nit);
}
