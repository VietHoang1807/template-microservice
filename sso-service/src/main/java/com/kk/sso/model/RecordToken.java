package com.kk.sso.model;

import lombok.Builder;

@Builder
public record RecordToken(String token, String tokenRefresh) {
}
