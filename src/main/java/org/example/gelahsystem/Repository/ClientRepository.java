package org.example.gelahsystem.Repository;

import org.example.gelahsystem.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client , Integer> {
    Client findClientById(Integer id);
}
