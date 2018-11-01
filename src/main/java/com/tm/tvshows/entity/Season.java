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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Season implements Serializable {

	/**
	 *
	 */
	@Transient
	private static final long serialVersionUID = 6075304837874102451L;

	@JsonView(View.Public.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Id
	private Integer id;

	@JsonView(View.Public.class)
	@Column(name = "season", nullable = false)
	private String season;

	@JsonView(View.Show.class)
	@OneToMany(mappedBy = "season", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<Episode> episodes;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "show_id")
	private Show show;

}
