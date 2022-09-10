package com.semika.aws.util;

@FunctionalInterface
public interface FromConverter <F, T> {
    F from(T t);
}
