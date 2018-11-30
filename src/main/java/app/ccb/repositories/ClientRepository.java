package app.ccb.repositories;

import app.ccb.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findByFullName(String fullName);
}

