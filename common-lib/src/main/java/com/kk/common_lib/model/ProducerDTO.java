package com.kk.common_lib.model;

import java.io.Serializable;

public record ProducerDTO(String id, String event, String name, Object contact) implements Serializable {
}
