package com.example.commonthings.repository;

import com.example.commonthings.entity.Call;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CallRepository extends JpaRepository<Call, Long> {
    List<Call> findAllByPhoneNumber(String phoneNumber);
}
