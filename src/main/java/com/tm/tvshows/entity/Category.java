package com.tm.tvshows.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category implements Serializable {

	/**
	 *
	 */
	@Transient
	private static final long serialVersionUID = -5844463911803033472L;

	@JsonView(View.Public.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Id
	private Integer id;

	@JsonView(View.Public.class)
	@Column(name = "type", length = 30, nullable = false, unique = true)
	private String type;

	@JsonView(View.Category.class)
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories")
	private Set<Show> shows;
}
