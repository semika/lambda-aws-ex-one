package com.semika.aws.util;

import java.util.List;

public interface ToConverterList<F, T> {
    List<T> toList(List<F> domainList);
}
