package com.kk.common_lib.request;

public record PageRequest(int pageNumber, int pageSize, String sortField, String sortOrder, Object dataRequest) {
}
