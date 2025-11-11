package com.kk.sso.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "pre_password")
public record PrePass(@Id Integer id, Integer user_id, String pre_password) {

}
