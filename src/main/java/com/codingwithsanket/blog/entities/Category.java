package com.codingwithsanket.blog.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer categoryId;
	@Column(name = "title", length = 100, nullable = false)
	private String CategoryTitle;
	@Column(name = "description")
	private String CategoryDescription;
	// cascade = CascadeType.ALL → Any operation (Persist, Merge, Remove, Refresh,
	// Detach) performed on Category will also be applied to its related Post
	// entities.
	// One Category can have multiple Post records.
	/*
	 * The mappedBy = "category" in @OneToMany means that the Post entity contains
	 * the foreign key (category_id). The @ManyToOne annotation in Post creates the
	 * foreign key relationship. The cascade effect (CascadeType.ALL) ensures that
	 * if a Category is deleted, its related Post entities are also deleted.
	 */
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	//parent nikle child na nikle means category was deleted but post not deleted
	private List<Post> posts = new ArrayList<>();
}
