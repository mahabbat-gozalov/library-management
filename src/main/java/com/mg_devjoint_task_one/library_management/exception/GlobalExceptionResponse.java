package com.mg_devjoint_task_one.library_management.exception;

import java.util.List;
import java.util.Map;

public record GlobalExceptionResponse(
        String timestamp,
        int statusCode,
        String reasonPhrase,
        String exceptionMessage,
        String uri,
        Map<String, List<String>> validationErrors
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        String timestamp;
        int statusCode;
        String reasonPhrase;
        String exceptionMessage;
        String uri;
        Map<String, List<String>> validationErrors;

        public Builder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder reasonPhrase(String reasonPhrase) {
            this.reasonPhrase = reasonPhrase;
            return this;
        }

        public Builder exceptionMessage(String exceptionMessage) {
            this.exceptionMessage = exceptionMessage;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder validationErrors(Map<String, List<String>> validationErrors) {
            this.validationErrors = validationErrors;
            return this;
        }

        public GlobalExceptionResponse build() {
            return new GlobalExceptionResponse(
                    timestamp,
                    statusCode,
                    reasonPhrase,
                    exceptionMessage,
                    uri,
                    validationErrors
            );
        }
    }

}
