package ai.lerna.flapi.entity;

import ai.lerna.flapi.entity.converter.WebhookConfigFilterConverter;
import ai.lerna.flapi.entity.converter.WebhookConfigRequestConverter;
import ai.lerna.flapi.service.dto.WebhookMessageType;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "webhook_config")
public class WebhookConfig {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(name = "app_id")
	private long appId;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private WebhookMessageType type;

	@Column(name = "request")
	@Convert(converter = WebhookConfigRequestConverter.class)
	private WebhookConfigRequest request = new WebhookConfigRequest();

	@Column(name = "filter")
	@Convert(converter = WebhookConfigFilterConverter.class)
	private WebhookConfigFilter filter = new WebhookConfigFilter();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public WebhookMessageType getType() {
		return type;
	}

	public void setType(WebhookMessageType type) {
		this.type = type;
	}

	public WebhookConfigRequest getRequest() {
		return request;
	}

	public void setRequest(WebhookConfigRequest request) {
		this.request = request;
	}

	public WebhookConfigFilter getFilter() {
		return filter;
	}

	public void setFilter(WebhookConfigFilter filter) {
		this.filter = filter;
	}
}
