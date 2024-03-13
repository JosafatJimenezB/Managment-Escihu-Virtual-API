package com.escihu.apiescihuvirtual.Dto;

import java.time.Instant;

public record ErrorDto(Integer status, String message, String path, String error, String exception, Instant timestamp) {

}
