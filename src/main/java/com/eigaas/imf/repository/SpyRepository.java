package com.eigaas.imf.repository;

import com.eigaas.imf.domain.Spy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpyRepository extends JpaRepository<Spy, Long> {

    Optional<Spy> findByUsername(String username);

}
