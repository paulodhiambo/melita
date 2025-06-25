package io.transaction.config.repository;

import io.transaction.config.domain.PropertySourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertySourceRepository extends JpaRepository<PropertySourceEntity, Long> {
    List<PropertySourceEntity> findByApplicationAndProfileAndLabel(String application, String profile, String label);
}