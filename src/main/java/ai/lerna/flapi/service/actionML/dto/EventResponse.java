package ai.lerna.flapi.service.actionML.dto;

import java.io.Serializable;

public class EventResponse implements Serializable {

	private String comment;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
