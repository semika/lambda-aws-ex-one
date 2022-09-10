package com.semika.aws.util;

public interface ToConverter <F, T> {
    T to(F f);
}
