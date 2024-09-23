package com.noxinfinity.pdating.Primary.Base;

public record Response<T>(
        int code,
        String status,
        T body,
        String message
) {}