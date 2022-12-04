package com.semika.aws.util;

public interface BiConverter <F, T>
        extends FromConverter<F, T>, ToConverter<F, T>, FromConverterList<F, T>, ToConverterList<F, T> {

}
