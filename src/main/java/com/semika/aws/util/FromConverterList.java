package com.semika.aws.util;

import java.util.List;

@FunctionalInterface
public interface FromConverterList<F, T> {
    List<F> fromList(List<T> dtoList);
}
