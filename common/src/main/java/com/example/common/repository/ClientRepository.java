package com.example.common.repository;

import com.example.common.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
//    Client findClientByPhoneNumber(String phoneNumber);
    Optional<Client> findClientByPhoneNumber(String phoneNumber);
}
