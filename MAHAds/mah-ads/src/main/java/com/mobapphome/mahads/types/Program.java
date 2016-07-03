package com.mobapphome.mahads.types;

import java.io.Serializable;
import java.util.Date;

public class Program  implements Serializable{
	int id;
	String name;
	String desc;
	String uri;
	String img;
	String release_date;
	Date releaseDateAsDate;
	boolean newPrgram = false;
	
	public boolean isNewPrgram() {
		return newPrgram;
	}

	public void setNewPrgram(boolean newPrgram) {
		this.newPrgram = newPrgram;
	}

	public Program(){
		
	}

	public Program(int id, String name, String desc, String uri, String img,
			String release_date) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.uri = uri;
		this.img = img;
		this.release_date = release_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public Date getReleaseDateAsDate() {
		return releaseDateAsDate;
	}

	public void setReleaseDateAsDate(Date releaseDateAsDate) {
		this.releaseDateAsDate = releaseDateAsDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Program other = (Program) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
