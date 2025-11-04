package com.udea.couriersync.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udea.couriersync.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  java.util.Optional<User> findByEmail(String email);
}