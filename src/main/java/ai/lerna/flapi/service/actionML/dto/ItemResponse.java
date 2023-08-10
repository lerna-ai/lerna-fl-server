package ai.lerna.flapi.service.actionML.dto;

import java.io.Serializable;
import java.util.List;

public class ItemResponse implements Serializable {

	private List<ItemScore> result;

	public List<ItemScore> getResult() {
		return result;
	}

	public void setResult(List<ItemScore> result) {
		this.result = result;
	}
}
