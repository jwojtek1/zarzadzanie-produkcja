package com.faktoria.zarzadzanieprodukcja.repository;

import com.faktoria.zarzadzanieprodukcja.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
