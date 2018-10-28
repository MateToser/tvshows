package com.tm.tvshows.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "tv_show")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Show implements Serializable {

	/**
	 *
	 */
	@Transient
	private static final long serialVersionUID = -1177306598858303420L;

	@JsonView(View.Public.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Id
	private Integer id;

	@JsonView(View.Public.class)
	@Column(name = "title", length = 50, nullable = false, unique = true)
	private String title;

	@JsonView(View.Public.class)
	@Column(name = "description", length = 500, nullable = false)
	private String description;

	@JsonView(View.Public.class)
	@Column(name = "released", length = 20, nullable = false)
	private String released;

	@JsonView(View.Public.class)
	@Column(name = "writer", length = 255, nullable = false)
	private String writer;

	@JsonView(View.Public.class)
	@Column(name = "awards", length = 255, nullable = false)
	private String awards;

	@JsonView(View.Public.class)
	@Column(name = "seasons", nullable = false)
	private Long seasons;

	@JsonView(View.Public.class)
	@Column(name = "imdb_rating", nullable = false)
	private Double imdbRating;

	@JsonView(View.Public.class)
	@Column(name = "imdb_votes", nullable = false)
	private String imdbVotes;

	@JsonView(View.Public.class)
	@Column(name = "imdb_id", nullable = false)
	private String imdbId;

	@JsonView(View.Public.class)
	@Column(name = "approved", columnDefinition = "TINYINT DEFAULT 0")
	private Boolean approved;

	@JsonView(View.Public.class)
	@Column(name = "poster_url", length = 2000, nullable = false)
	private String posterUrl;

	@JsonView(View.Show.class)
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "show_user", joinColumns = @JoinColumn(name = "show_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private Set<User> users;

	@JsonView(View.Show.class)
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "show_category", joinColumns = @JoinColumn(name = "show_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
	private Set<Category> categories;
}
