package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Class for creating a Json Object for validation
 * @author emersonr
 *
 */

@Entity
public class JsonBase {
	

	@Id
	private long id;
	
	@Lob
	@Column( length = 100000 )
	private String left;
	
	@Lob
	@Column( length = 100000 )
	private String right;

	public JsonBase() {
	}
	
	/**
	 * This method create a new object to map data for validation
	 * @param id is used for object identification
	 * @param valueLeft is the json base64 enconded used for comparison
	 * @param valueRight is the second json base64 enconded used for comparison
	 */
	public JsonBase(Long id, String valueLeft, String valueRight) {
		this.id = id;
		this.left = valueLeft;
		this.right = valueRight;
	}

	
	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "JsonBase [id=" + getId() + ", left=" + left + ", json64Right=" + right + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

		

}
