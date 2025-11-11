package com.kk.sso.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "detail_user")
public record DetailUser(
                @Id Integer id, Integer userId, String first_name,
                String last_name,
                String email,
                LocalDateTime crt_at,
                LocalDateTime upd_at, Integer verion) {
}
