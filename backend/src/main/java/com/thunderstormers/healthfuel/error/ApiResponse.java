package com.thunderstormers.healthfuel.error;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public record ApiResponse<T>(
	boolean success,
	int status,
	String message,
	T data,
	List<String> errors,
	String path,
	ZonedDateTime timestamp
) {

	public static <T> ApiResponse<T> success(T data, String message) {
		return success(data, message, HttpStatus.OK);
	}

	public static <T> ApiResponse<T> success(T data, String message, HttpStatus status) {
		return success(data, message, status.value());
	}

	public static <T> ApiResponse<T> success(T data, String message, int status) {
		return new ApiResponse<>(
			true,
			status,
			message,
			data,
			List.of(),
			getRequestPath(),
			ZonedDateTime.now(ZoneOffset.UTC)
		);
	}

	public static <T> ApiResponse<T> error(String error, int status) {
		return error(error, HttpStatus.valueOf(status).getReasonPhrase(), status);
	}

	public static <T> ApiResponse<T> error(String error, HttpStatus status) {
		return error(error, status.getReasonPhrase(), status.value());
	}

	public static <T> ApiResponse<T> error(List<String> errors, int status) {
		return error(errors, HttpStatus.valueOf(status).getReasonPhrase(), status);
	}

	public static <T> ApiResponse<T> error(List<String> errors, HttpStatus status) {
		return error(errors, status.getReasonPhrase(), status.value());
	}

	public static <T> ApiResponse<T> error(String error, String message, int status) {
		return error(List.of(error), message, status);
	}

	public static <T> ApiResponse<T> error(List<String> errors, String message, int status) {
		return new ApiResponse<>(
			false,
			status,
			message,
			null,
			errors,
			getRequestPath(),
			ZonedDateTime.now(ZoneOffset.UTC)
		);
	}

	public ResponseEntity<ApiResponse<T>> toResponseEntity() {
		return ResponseEntity.status(status).body(this);
	}

	private static String getRequestPath() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attrs == null) {
			throw new IllegalStateException("No request attributes found. This method must be called in the context of an HTTP request.");
		}
		return attrs.getRequest().getRequestURI();
	}

}
