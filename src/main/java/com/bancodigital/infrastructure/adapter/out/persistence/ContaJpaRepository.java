package com.bancodigital.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ContaJpaRepository extends JpaRepository<ContaEntity, Long> {
    Optional<ContaEntity> findByNumero(String numero);
}