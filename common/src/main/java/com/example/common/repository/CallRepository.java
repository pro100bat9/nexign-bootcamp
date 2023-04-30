package com.example.common.repository;

import com.example.common.entity.Call;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CallRepository extends JpaRepository<Call, Long> {
    List<Call> findAllByPhoneNumber(String phoneNumber);
}
