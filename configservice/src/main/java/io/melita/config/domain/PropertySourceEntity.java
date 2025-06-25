package io.melita.config.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "property_source")
@Getter
@Setter
public class PropertySourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String application;
    private String profile;
    private String label;
    private String propertyKey;
    private String propertyValue;
}