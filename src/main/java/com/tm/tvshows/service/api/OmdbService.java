package com.tm.tvshows.service.api;

import com.tm.tvshows.response.OmdbResponse;

public interface OmdbService {

	OmdbResponse getOmdbResponse(String title);

}
