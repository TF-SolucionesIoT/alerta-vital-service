package com.iot.alertavital.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.persistence.EntityManager;

/**
 * Habilita JPA auditing únicamente cuando JPA está presente en el classpath
 * y la propiedad spring.jpa.enabled está habilitada. Esto evita que los
 * tests @WebMvcTest intenten instanciar beans JPA innecesarios.
 */
@Configuration
@ConditionalOnClass(EntityManager.class)
@ConditionalOnProperty(name = "spring.jpa.enabled", havingValue = "true", matchIfMissing = true)
@EnableJpaAuditing
public class JpaAuditingConfig {

}
