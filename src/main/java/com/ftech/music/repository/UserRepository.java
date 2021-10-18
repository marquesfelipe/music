package com.ftech.music.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftech.music.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

}
