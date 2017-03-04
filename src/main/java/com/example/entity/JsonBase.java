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
	 * @param left is the json base64 enconded used for comparison
	 * @param right is the second json base64 enconded used for comparison
	 */
	public JsonBase(Long id, String left, String right) {
		this.id = id;
		this.left = left;
		this.right = right;
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
		return "JsonBase [id=" + getId() + ", left=" + getLeft() + ", right=" + getRight() + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JsonBase other = (JsonBase) obj;
		if (id != other.id)
			return false;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}

	
		

}
