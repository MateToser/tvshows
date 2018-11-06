package com.tm.tvshows.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tm.tvshows.response.OmdbEpisode;
import com.tm.tvshows.response.OmdbResponse;
import com.tm.tvshows.response.OmdbSeasonResponse;
import com.tm.tvshows.response.OmdbSeriesResponse;
import com.tm.tvshows.service.api.OmdbService;

@Service
public class OmdbServiceImpl implements OmdbService {

	private static final String API_URL = "http://www.omdbapi.com/";
	private static final String API_BYNAME = "?t=";
	private static final String API_BYID = "?i=";
	private static final String API_KEY = "&apikey=d771ff61";
	private static final String API_SEASON = "&Season=";
	private static final String API_EPISODE = "&Episode=";

	@Override
	public OmdbResponse getOmdbResponse(String title) {

		final OmdbResponse response = new OmdbResponse();
		response.setShow(getOmdbSeriesResponse(title));
		response.setSeasons(
				getOmdbSeasonResponse(response.getShow().getImdbID(), response.getShow().getSeasonsInLong()));
		return response;
	}

	private OmdbSeriesResponse getOmdbSeriesResponse(String title) {
		title = title.replaceAll(" ", "+");
		final String uri = API_URL + API_BYNAME + title + API_KEY;

		OmdbSeriesResponse omdbSeriesResponse = new OmdbSeriesResponse();
		RestTemplate restTemplate = new RestTemplate();
		omdbSeriesResponse = restTemplate.getForObject(uri, OmdbSeriesResponse.class);
		if ("movie".equals(omdbSeriesResponse.getType())) {
			throw new BadRequestException("Not a series");
		}

		return omdbSeriesResponse;
	}

	private List<OmdbSeasonResponse> getOmdbSeasonResponse(String showId, Long totalSeasons) {

		List<OmdbSeasonResponse> omdbSeasonResponseList = new ArrayList<>();
		for (int i = 0; i < totalSeasons; i++) {
			final String uri = API_URL + API_BYID + showId + API_SEASON + (i + 1) + API_KEY;
			OmdbSeasonResponse omdbSeasonResponse = new OmdbSeasonResponse();
			RestTemplate restTemplate = new RestTemplate();
			omdbSeasonResponse = restTemplate.getForObject(uri, OmdbSeasonResponse.class);

			List<OmdbEpisode> notReleasedEpisodes = new ArrayList<>();
			for (OmdbEpisode episode : omdbSeasonResponse.getEpisodes()) {
				if ("N/A".equals(episode.getReleased())) {
					notReleasedEpisodes.add(episode);
				}
			}
			if (!notReleasedEpisodes.isEmpty()) {
				for (OmdbEpisode notReleasedEpisode : notReleasedEpisodes) {
					omdbSeasonResponse.getEpisodes().remove(notReleasedEpisode);
				}
			}
			omdbSeasonResponseList.add(omdbSeasonResponse);
		}

		return omdbSeasonResponseList;
	}

	/*
	 * private List<OmdbEpisodeResponse> getOmdbEpisodeResponse(String showId, Integer season, Integer totalEpisodes) {
	 * List<OmdbEpisodeResponse> omdbEpisodeResponseList = new ArrayList<>(); for (int i = 0; i < totalEpisodes; i++) {
	 * final String uri = API_URL + API_BYID + showId + API_SEASON + season + API_EPISODE + (i + 1) + API_KEY;
	 * OmdbEpisodeResponse omdbEpisodeResponse = new OmdbEpisodeResponse(); RestTemplate restTemplate = new
	 * RestTemplate(); omdbEpisodeResponse = restTemplate.getForObject(uri, OmdbEpisodeResponse.class);
	 * omdbEpisodeResponseList.add(omdbEpisodeResponse); }
	 * 
	 * return omdbEpisodeResponseList; }
	 */

}
