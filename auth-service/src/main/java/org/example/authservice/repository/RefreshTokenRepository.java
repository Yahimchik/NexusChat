package org.example.authservice.repository;

import org.example.authservice.model.User;
import org.example.authservice.model.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);

    @Query("select r from RefreshToken r where r.user = :user and  r.ipAddress = :ip")
    Optional<RefreshToken> findByUserAndIp(@Param("user") User user, @Param("ip") String ip);

    @Modifying
    @Query("delete from RefreshToken r where r.user = :user and  r.ipAddress = :ip")
    void deleteByUserAndIp(@Param("user") User user, @Param("ip") String ip);
}
