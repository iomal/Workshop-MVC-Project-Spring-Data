package app.ccb.repositories;

import app.ccb.domain.entities.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.*;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findByFullName(String fullName);

    @Query (value = "SELECT c from app.ccb.domain.entities.Client as " +
            "c join c.bankAccount as b order by size(b.cards) desc")
    List<Client> familyGuy(Pageable pageable);
}

