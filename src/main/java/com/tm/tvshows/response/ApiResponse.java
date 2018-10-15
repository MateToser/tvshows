package com.tm.tvshows.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {

	private final Boolean success;
	private final String message;

}
