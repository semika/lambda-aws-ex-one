package com.semika.aws.util;

import java.util.List;

@FunctionalInterface
public interface FromConverter <F, T> {
    F from(T t);
}
