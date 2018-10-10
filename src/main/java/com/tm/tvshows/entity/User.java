package com.tm.tvshows.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;
import com.tm.tvshows.common.Role;
import com.tm.tvshows.common.View;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements Serializable {

	/**
	 *
	 */
	@Transient
	private static final long serialVersionUID = -5070583730435929792L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Id
	private Integer id;

	@JsonView(View.Public.class)
	@Column(name = "email", length = 50, nullable = false)
	private String email;

	@JsonView(View.Public.class)
	@Column(name = "first_name", length = 30, nullable = false)
	private String firstName;

	@JsonView(View.Public.class)
	@Column(name = "last_name", length = 30, nullable = false)
	private String lastName;

	@Column(name = "password_hash", length = 255, nullable = false)
	private String passwordHash;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;

	@JsonView(View.User.class)
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
	private Set<Show> shows;

}
