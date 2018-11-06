package com.tm.tvshows.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;
import com.tm.tvshows.common.View;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Comment implements Serializable {

	/**
	 *
	 */
	@Transient
	private static final long serialVersionUID = -5742996773494528011L;

	@JsonView(View.Public.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Id
	private Integer id;

	@JsonView(View.Public.class)
	@Column(name = "message", length = 255)
	private String message;

	@JsonView(View.Public.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	private Date date;

	@JsonView({ View.Comment.class, View.Show.class })
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@JsonView(View.Comment.class)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "show_id")
	private Show show;

}
