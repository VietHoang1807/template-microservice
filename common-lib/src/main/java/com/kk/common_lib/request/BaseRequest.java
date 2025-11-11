package com.kk.common_lib.request;

import java.util.Date;

public record BaseRequest(String username, Date fromDate, Date toDate) {
}
