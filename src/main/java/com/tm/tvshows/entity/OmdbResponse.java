package com.tm.tvshows.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OmdbResponse {

	@JsonProperty("Title")
	private String title;
	@JsonProperty("Year")
	private String year;
	@JsonProperty("Rated")
	private String rated;
	@JsonProperty("Released")
	private String released;
	@JsonProperty("Runtime")
	private String runtime;
	@JsonProperty("Genre")
	private String genre;
	@JsonProperty("Director")
	private String director;
	@JsonProperty("Writer")
	private String writer;
	@JsonProperty("Actors")
	private String actors;
	@JsonProperty("Plot")
	private String plot;
	@JsonProperty("Language")
	private String language;
	@JsonProperty("Country")
	private String country;
	@JsonProperty("Awards")
	private String awards;
	@JsonProperty("Poster")
	private String poster;
	@JsonProperty("Ratings")
	private List<Rating> ratings;
	@JsonProperty("Metascore")
	private String metascore;
	@JsonProperty("imdbRating")
	private String imdbRating;
	@JsonProperty("imdbVotes")
	private String imdbVotes;
	@JsonProperty("imdbID")
	private String imdbID;
	@JsonProperty("Type")
	private String type;
	@JsonProperty("totalSeasons")
	private String totalSeasons;
	@JsonProperty("Response")
	private String response;

	public Double getImdbRatingInDouble() {
		if ("N/A".equals(imdbRating)) {
			return 0.0;
		}
		return Double.parseDouble(imdbRating);
	}

	public Long getSeasonsInLong() {
		return Long.parseLong(totalSeasons);
	}

}
