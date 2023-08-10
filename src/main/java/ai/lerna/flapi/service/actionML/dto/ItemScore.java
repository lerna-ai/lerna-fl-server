package ai.lerna.flapi.service.actionML.dto;

import java.io.Serializable;
import java.util.Map;

public class ItemScore implements Serializable {
	private String item;
	private Double score;
	private String props;
	private Map<String, Double> ranks;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getProps() {
		return props;
	}

	public void setProps(String props) {
		this.props = props;
	}

	public Map<String, Double> getRanks() {
		return ranks;
	}

	public void setRanks(Map<String, Double> ranks) {
		this.ranks = ranks;
	}
}
