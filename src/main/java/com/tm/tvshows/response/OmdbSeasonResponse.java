package com.tm.tvshows.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class OmdbSeasonResponse {

	@JsonProperty("Title")
	private String title;
	@JsonProperty("Season")
	private String season;
	@JsonProperty("totalSeasons")
	private String totalSeasons;
	@JsonProperty(value = "Episodes", access = Access.WRITE_ONLY)
	private List<OmdbEpisode> episodes;
	@JsonProperty("Response")
	private String response;
}
