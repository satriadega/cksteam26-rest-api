package com.juaracoding.cksteam26.repo;

import com.juaracoding.cksteam26.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndIsVerified(String username, Boolean isVerified);

    Optional<User> findByUsernameOrEmailOrToken(String username, String email, String token);

    Optional<User> findByUsernameOrEmailOrTokenAndIsVerified(String username, String email, String token, Boolean isVerified);

    Page<User> findByNameContainsIgnoreCase(String name, Pageable pageable);

    Page<User> findByEmailContainsIgnoreCase(String email, Pageable pageable);

    Page<User> findByUsernameContainsIgnoreCase(String username, Pageable pageable);

    Page<User> findByTokenContainsIgnoreCase(String token, Pageable pageable);

    List<User> findByNameContainsIgnoreCase(String name);

    List<User> findByEmailContainsIgnoreCase(String email);

    List<User> findByUsernameContainsIgnoreCase(String username);

    List<User> findByTokenContainsIgnoreCase(String token);

    Optional<User> findTop1ByOrderByUserIdDesc();

//    Optional<User> findByUsernameAndIsVerified(String username, Boolean valid);
}
