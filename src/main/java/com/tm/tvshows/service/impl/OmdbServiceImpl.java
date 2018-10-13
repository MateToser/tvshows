package com.tm.tvshows.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tm.tvshows.entity.OmdbResponse;
import com.tm.tvshows.service.api.OmdbService;

@Service
public class OmdbServiceImpl implements OmdbService {

	private static final String API_URL = "http://www.omdbapi.com/?t=";
	private static final String API_KEY = "&apikey=d771ff61";

	@Override
	public OmdbResponse getOmdbResponse(String title) {
		title = title.replaceAll(" ", "+");
		final String uri = API_URL + title + API_KEY;

		OmdbResponse omdbResponse = new OmdbResponse();
		RestTemplate restTemplate = new RestTemplate();
		omdbResponse = restTemplate.getForObject(uri, OmdbResponse.class);

		return omdbResponse;
	}

}
