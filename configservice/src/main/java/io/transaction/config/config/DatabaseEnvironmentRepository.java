package io.transaction.config.config;

import lombok.RequiredArgsConstructor;
import io.transaction.config.domain.PropertySourceEntity;
import io.transaction.config.repository.PropertySourceRepository;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class DatabaseEnvironmentRepository implements EnvironmentRepository {
    private final PropertySourceRepository repository;

    @Override
    public Environment findOne(String application, String profile, String label) {
        List<PropertySourceEntity> properties =
                repository.findByApplicationAndProfileAndLabel(application, profile, label);

        Map<String, Object> props = new HashMap<>();
        for (PropertySourceEntity entity : properties) {
            props.put(entity.getPropertyKey(), entity.getPropertyValue());
        }

        PropertySource propertySource = new PropertySource("db", props);

        Environment environment = new Environment(application, new String[]{profile}, label, null, null);
        environment.add(propertySource);
        return environment;
    }
}

