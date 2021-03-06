package com.keinye.spring.common.struct;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Result<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5345139081725010614L;
	
    private boolean success;
    private int errorCode;
    private String message;
    private String description;
    private T data;
    private Object errorData;
    
    private static final Result<?> EMPTY = new Result<>(false, -1, "", null, null, null);
    
    @SuppressWarnings("unchecked")
	public static <T> Result<T> empty() {
    	return (Result<T>)EMPTY;
    }
    
    public static <T> Result<T> ok(T data) {
    	return new Result<>(true, 0, null, null, data, null);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Result ok() {
    	return new Result(true, 0, null, null, null, null);
    }
    
    public static <T> Result<T> error(int errorCode, @NonNull String errorMessage) {
        if (errorCode <= 0) {
            throw new IllegalArgumentException(
                    String.format("errorCode should be > 0, got: %d", errorCode));
        }

        return new Result<>(
                false,
                errorCode,
                errorMessage,
                null,
                null,
                null);
    }
    
    public static <T> Result<T> of(@NonNull ResultException exception) {
        return new Result<>(
                false,
                exception.getErrorCode(),
                exception.getMessage(),
                exception.getDescription(),
                null,
                exception.getErrorData());
    }
    
    @SuppressWarnings("unchecked")
    public <T> Result<T> errorData(Object errorData) {
        if (isSuccess()) {
            throw new IllegalStateException(
                    "errorData() could only be called if the result is not success");
        }
        this.errorData = errorData;
        return (Result<T>) this;
    }
    
    @SuppressWarnings("unchecked")
	public static <T> Result<T> error(@NonNull ErrorCode errorCode) {
    	return (Result<T>) error(errorCode.getErrorCode(), errorCode.getDescription())
    			.description(errorCode.getDescription());
    }
    
    @SuppressWarnings("unchecked")
    public <T> Result<T> description(String description) {
        if (isSuccess()) {
            throw new IllegalStateException(
                    "description() could only be called if the result is not success");
        }
        this.description = description;
        return (Result<T>) this;
    }
    
    public boolean isSuccess() {
        return success;
    }
	
    public String getMessage() {
        return message;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
    
    public T getData() {
        if (this.isSuccess()) {
            return this.data;
        }

        throw ResultException.of(this.getErrorCode(), this.getMessage())
                .description(this.getDescription())
                .errorData(this.getErrorData());
    }
    
    public Object getErrorData() {
        return errorData;
    }
    
    public void ifSuccess(Consumer<? super T> consumer) {
        if (isSuccess()) {
            consumer.accept(this.data);
        }
    }
    
    public Result<T> filter(@NonNull Predicate<? super T> predicate) {
        if (!isSuccess()) {
            return this;
        } else {
            return predicate.test(this.data) ? this : empty();
        }
    }
    
    @SuppressWarnings("unchecked")
    public <U> Result<U> map(@NonNull Function<? super T, ? extends U> mapper) {
        if (!isSuccess()) {
            return (Result<U>) this;
        } else {
            return Result.ok(mapper.apply(this.data));
        }
    }
    
    @SuppressWarnings("unchecked")
    public <U> Result<U> flatMap(@NonNull Function<? super T, Result<U>> mapper) {
        if (!isSuccess()) {
            return (Result<U>) this;
        } else {
            return Objects.requireNonNull(mapper.apply(this.data));
        }
    }
    
    public T orElse(T other) {
        return isSuccess() ? data : other;
    }
    
    public T orElseGet(Supplier<? extends T> other) {
        return isSuccess() ? data : other.get();
    }
    
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier)
            throws X {
        if (isSuccess()) {
            return data;
        } else {
            throw exceptionSupplier.get();
        }
    }
}
