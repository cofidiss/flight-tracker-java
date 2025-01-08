package com.flight.infrastructure.persistence;

import com.flight.core.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository  extends JpaRepository<UserEntity, Long> {
    // 1. Kullanıcı adını ve email'i baz alarak kullanıcıyı bul
    /*@Query("SELECT u FROM User u WHERE u.user_name = :username")
    List<UserEntity> findByUsername(@Param("username") String username);
*/

    Optional<UserEntity> findByUsername(String username);
}
