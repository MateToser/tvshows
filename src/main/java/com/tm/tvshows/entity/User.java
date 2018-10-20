package com.tm.tvshows.entity;

import java.io.Serializable;
import java.util.Set;

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
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonView;
import com.tm.tvshows.common.View;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
@EqualsAndHashCode(of = "id")
public class User implements Serializable {

	/**
	 *
	 */
	@Transient
	private static final long serialVersionUID = 3955409955613511591L;

	public User() {

	}

	public User(String email, String firstName, String lastName, String password) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public User(UserPrincipal currentUser) {
		id = currentUser.getId();
		email = currentUser.getEmail();
		firstName = currentUser.getFirstName();
		lastName = currentUser.getLastName();
		password = currentUser.getPassword();
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Id
	private Integer id;

	@JsonView(View.Public.class)
	@Column(name = "email", length = 50, nullable = false, unique = true)
	private String email;

	@JsonView(View.Public.class)
	@Column(name = "first_name", length = 30, nullable = false)
	private String firstName;

	@JsonView(View.Public.class)
	@Column(name = "last_name", length = 30, nullable = false)
	private String lastName;

	@Column(name = "password", length = 255, nullable = false)
	private String password;

	@JsonView(View.Role.class)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles;

	@JsonView(View.User.class)
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
	private Set<Show> shows;

	public String getFullName() {
		return firstName + " " + lastName;
	}
}
