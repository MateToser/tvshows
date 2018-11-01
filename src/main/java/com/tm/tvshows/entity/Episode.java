package com.tm.tvshows.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;
import com.tm.tvshows.common.View;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "season")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Episode implements Serializable {

	/**
	 *
	 */
	@Transient
	private static final long serialVersionUID = 8449661515540907401L;

	@JsonView(View.Public.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Id
	private Integer id;

	@JsonView(View.Public.class)
	@Column(name = "title", nullable = false)
	private String title;

	@JsonView(View.Public.class)
	@Column(name = "year", nullable = false)
	private String year;

	@JsonView(View.Public.class)
	@Column(name = "released", nullable = false)
	private String released;

	@JsonView(View.Public.class)
	@Column(name = "episode", nullable = false)
	private String episode;

	@JsonView(View.Public.class)
	@Column(name = "runtime", nullable = false)
	private String runtime;

	@JsonView(View.Public.class)
	@Column(name = "plot", nullable = false)
	private String plot;

	@JsonView(View.Public.class)
	@Column(name = "poster", nullable = false)
	private String poster;

	@JsonView(View.Public.class)
	@Column(name = "imdbRating", nullable = false)
	private String imdbRating;

	@JsonView(View.Public.class)
	@Column(name = "imdbVotes", nullable = false)
	private String imdbVotes;

	@JsonView(View.Public.class)
	@Column(name = "imdbId", nullable = false)
	private String imdbId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "season_id")
	private Season season;

}
