package com.example.extblocker.repo;

import com.example.extblocker.domain.FixedExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FixedExtensionRepo extends JpaRepository<FixedExtension, Long> {
    Optional<FixedExtension> findByExt(String ext);
}
