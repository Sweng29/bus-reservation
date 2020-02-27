package com.mantistech.busreservation.dto.response;

import com.mantistech.busreservation.enums.ResponseStatus;

import java.sql.Date;

public class Response<T> {

    private ResponseStatus status;
    private T payload;
    private Object errors;
    private Object metaData;

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    public Object getMetaData() {
        return metaData;
    }

    public void setMetaData(Object metaData) {
        this.metaData = metaData;
    }

    public static <T> Response<T> ok() {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.OK);
        return response;
    }

    public static <T> Response<T> badRequest() {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.BAD_REQUEST);
        return response;
    }

    public static <T> Response<T> unauthorized() {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.UNAUTHORIZED);
        return response;
    }

    public static <T> Response<T> validationException() {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.VALIDATION_EXCEPTION);
        return response;
    }

    public static <T> Response<T> exception() {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.EXCEPTION);
        return response;
    }

    public static <T> Response<T> wrongCredentials() {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.BAD_REQUEST);
        return response;
    }

    public static <T> Response<T> accessDenied() {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.ACCESS_DENIED);
        return response;
    }

    public static <T> Response<T> notFound() {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.NOT_FOUND);
        return response;
    }

    public static <T> Response<T> duplicateEntry() {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.DUPLICATE_ENTITY);
        return response;
    }

    public void addErrorMessageToResponse(String errorMessage, Exception ex)
    {
        ResponseError responseError = new ResponseError();
        responseError.setDetails(errorMessage);
        responseError.setMessage(ex.getMessage());
        responseError.setTimestamp(new Date(System.currentTimeMillis()));
        setErrors(responseError);
    }

    public static class PageMetaData{
        private int size;
        private long totalElements;
        private int totalPages;
        private int number;

        public PageMetaData() {
        }

        public PageMetaData(int size, long totalElements, int totalPages, int number) {
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.number = number;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(long totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}
