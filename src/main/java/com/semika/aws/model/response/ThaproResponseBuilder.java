package com.semika.aws.model.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.crypto.Data;
import java.util.List;

public class ThaproResponseBuilder<T> {

    public static <T> MessagePhrase<T> create() {
        ThaproResponse<T> thaproResponse = new ThaproResponse<>();
        return new MessagePhrase(thaproResponse);
    }

    public static class MessagePhrase<T> {
        private final ThaproResponse<T> thaproResponse;

        public MessagePhrase(ThaproResponse<T> thaproResponse) {
            this.thaproResponse = thaproResponse;
        }

        public DataPhrase<T> withMessage(String message) {
            this.thaproResponse.setMessage(message);
            return new DataPhrase<>(this.thaproResponse);
        }
    }

    public static class DataPhrase<T> {
        private final ThaproResponse<T> thaproResponse;

        public DataPhrase(ThaproResponse<T> thaproResponse) {
            this.thaproResponse = thaproResponse;
        }

        public ThaproResponse<T> withData(T t) {
            this.thaproResponse.setData(t);
            return this.thaproResponse;
        }

        public ThaproResponse<T> withoutData() {
            return this.thaproResponse;
        }

    }
}
