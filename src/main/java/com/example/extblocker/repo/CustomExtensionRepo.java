package com.example.extblocker.repo;

import com.example.extblocker.domain.CustomExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomExtensionRepo extends JpaRepository<CustomExtension, Long> {
    Optional<CustomExtension> findByExt(String ext);
    long countBy();
    void deleteByExt(String ext);
    boolean existsByExt(String ext);
}
