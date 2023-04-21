package com.example.commonthings.repository;

import com.example.commonthings.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrtRepository extends JpaRepository<Client, Long> {
    Client findClientByPhoneNumber(String phoneNumber);
}
