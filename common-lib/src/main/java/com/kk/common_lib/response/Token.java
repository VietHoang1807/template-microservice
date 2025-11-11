package com.kk.common_lib.response;

import java.util.Date;

public record Token(String username, String accessToken, Date createdAt, Date expiredAt) {
    static Object user = null;

    public static void setUser(Object user) {
        Token.user = user;
    }
}
