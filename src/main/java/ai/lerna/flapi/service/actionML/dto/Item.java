package ai.lerna.flapi.service.actionML.dto;

import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
	private String engineId;
	private Integer num;
	private String user;
	private String item;
	private List<String> itemSet;
	private List<String> blacklistItems;
	private List<Rules> rules;

	Item() {
		// for serialisation/deserialization
	}

	private Item(Builder builder) {
		engineId = builder.engineId;
		num = builder.num;
		user = builder.user;
		rules = builder.rules;
		itemSet = builder.itemSet;
		blacklistItems = builder.blacklistItems;
		item = builder.item;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(Item copy) {
		return newBuilder()
				.setEngineId(copy.getEngineId())
				.setNum(copy.getNum())
				.setUser(copy.getUser())
				.setItem(copy.getItem())
				.setItemSet(copy.getItemSet())
				.setBlacklistItems(copy.getBlacklistItems())
				.setRules(copy.getRules());
	}

	public String getEngineId() {
		return engineId;
	}

	public Integer getNum() {
		return num;
	}

	public String getUser() {
		return user;
	}

	public String getItem() {
		return item;
	}

	public List<String> getItemSet() {
		return itemSet;
	}

	public List<String> getBlacklistItems() {
		return blacklistItems;
	}

	public List<Rules> getRules() {
		return rules;
	}

	@Override
	public String toString() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		return gsonBuilder.create().toJson(this);
	}

	public static final class Builder {
		private String engineId;
		private Integer num;
		private String user;
		private String item;
		private List<String> itemSet;
		private List<String> blacklistItems;
		private List<Rules> rules;

		private Builder() {
			itemSet = new ArrayList<>();
			blacklistItems = new ArrayList<>();
			rules = new ArrayList<>();
		}

		public Builder setEngineId(String engineId) {
			this.engineId = engineId;
			return this;
		}

		public Builder setNum(Integer num) {
			this.num = num;
			return this;
		}

		public Builder setUser(String user) {
			this.user = user;
			return this;
		}

		public Builder setItem(String item) {
			this.item = item;
			return this;
		}

		public Builder setItemSet(List<String> itemSet) {
			this.itemSet = itemSet;
			return this;
		}

		public Builder setBlacklistItems(List<String> blacklistItems) {
			this.blacklistItems = blacklistItems;
			return this;
		}

		public Builder setRules(List<Rules> rules) {
			this.rules = rules;
			return this;
		}

		public Item build() {
			return new Item(this);
		}
	}
}
