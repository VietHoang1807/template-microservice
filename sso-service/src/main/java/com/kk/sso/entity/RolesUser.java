package com.kk.sso.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "roles")
public record RolesUser(@Id Integer id, Integer users_id, String role_name) {

}
