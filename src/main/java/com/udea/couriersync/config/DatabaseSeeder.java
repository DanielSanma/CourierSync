package com.udea.couriersync.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.udea.couriersync.entity.User;
import com.udea.couriersync.enums.UserRole;
import com.udea.couriersync.repository.UserRepository;

@Component
public class DatabaseSeeder {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationContext context;

    @Autowired
    public DatabaseSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder, ApplicationContext context) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.context = context;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void seed() {
        logger.info("Starting database seeding...");
        try {
            // ✅ Llamar al método transaccional a través del proxy
            DatabaseSeeder proxy = context.getBean(DatabaseSeeder.class);
            proxy.seedUsersInTransaction();
        } catch (Exception e) {
            logger.error("Error while seeding database: {}", e.getMessage(), e);
        }
        logger.info("Database seeding completed.");
    }

    @Transactional(rollbackFor = Exception.class)
    public void seedUsersInTransaction() {
        seedUsers();
    }

    private void seedUsers() {
        String adminEmail = "admin@couriersync.com";
        long adminCount = userRepository.count();

        if (adminCount == 0) {
            try {
                logger.info("Creating admin user...");

                // ✅ Leer contraseña desde variable de entorno
                String adminPassword = System.getenv("ADMIN_DEFAULT_PASSWORD");

                if (adminPassword == null || adminPassword.isBlank()) {
                    logger.error("ADMIN_DEFAULT_PASSWORD no está configurada. Abortando creación del usuario admin.");
                    throw new IllegalStateException("ADMIN_DEFAULT_PASSWORD no configurada en el entorno.");
                }

                User adminUser = new User();
                adminUser.setName("Admin");
                adminUser.setEmail(adminEmail);
                adminUser.setPassword(passwordEncoder.encode(adminPassword));
                adminUser.setRole(UserRole.ADMIN);
                adminUser.setPhone("1234567890");

                userRepository.save(adminUser);
                logger.info("Admin user created successfully");

            } catch (Exception e) {
                logger.error("Error creating admin user: {}", e.getMessage(), e);
            }
        } else {
            logger.info("Admin user already exists, skipping seeding");
        }
    }
}
