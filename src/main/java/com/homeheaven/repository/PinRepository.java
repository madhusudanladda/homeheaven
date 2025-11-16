package com.homeheaven.repository;

import com.homeheaven.model.PinToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PinRepository extends JpaRepository<PinToken, Long> {
    Optional<PinToken> findByEmailAndPin(String email, String pin);

    void deleteByEmail(String email);
}
