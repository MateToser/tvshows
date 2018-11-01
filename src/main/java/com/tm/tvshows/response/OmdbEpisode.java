package com.tm.tvshows.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OmdbEpisode {

	@JsonProperty("Title")
	private String title;
	@JsonProperty("Released")
	private String released;
	@JsonProperty("Episode")
	private String episode;
	@JsonProperty("imdbRating")
	private String imdbRating;
	@JsonProperty("imdbID")
	private String imdbId;

}
