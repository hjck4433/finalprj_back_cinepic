package com.kh.cinepic.repository;

import com.kh.cinepic.entity.Admin;
import com.kh.cinepic.entity.RefreshTokenAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenAdminRepository extends JpaRepository<RefreshTokenAdmin, Long> {
    boolean existsByRefreshToken(String refreshToken);
    boolean existsByAdmin(Admin admin);

    @Modifying
    @Query("DELETE FROM RefreshTokenAdmin r WHERE r.admin = :admin")
    void deleteByAdmin(@Param("admin")Admin admin);
}
