package ai.lerna.flapi.service.actionML.dto;

import ai.lerna.flapi.service.actionML.DateTimeAdapter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Map;


/**
 * Todo: Semen, this looks generic enough since the input events are all of this form
 * the key differences will be in the properties, which will be Map<String, Object>
 * where the Object may be any primitive type or an array of primitive types. If all these work
 * we are ok. So we will want to encode "properties": {"some-property": ["string1","string"]},
 * a full event might looks like this
 {
 "eventId":"5bc96652c06e443a92e21a61dc89c7a3",
 "event":"$set",
 "entityType":"some-object-type",
 "entityId":"25853ac4-d8de-4c87-bf64-3d02de4e5011-62",
 "properties":{
 "locations":["here", "there"],
 "gender":"m",
 "dead": false,
 "children": 0
 },
 "eventTime":"2016-08-11T09:59:52.693+05:30",
 "creationTime":"2016-08-11T04:32:04.702Z"
 }
 */

public class Event implements Serializable {

	private String eventId;
	private String engineId;

	// mandatory fields
	private String event;
	private String entityType;
	private String entityId;

	// optional fields
	private String targetEntityType;
	private String targetEntityId;
	private Map<String, Object> properties = Maps.newHashMap();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private DateTime eventTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private DateTime creationTime;

	/**
	 * Instantiate an event object.
	 */
	public Event() {}

	/**
	 * Returns the name of the event.
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * Returns the entity type. entityType-entityId forms the unique identifier of the entity.
	 */
	public String getEntityType() {
		return entityType;
	}

	/**
	 * Returns the entity id. entityType-entityId forms the unique identifier of the entity.
	 */
	public String getEntityId() {
		return entityId;
	}

	/**
	 * Returns the target entity type, or null if the field is not set.
	 */
	public String getTargetEntityType() {
		return targetEntityType;
	}

	/**
	 * Returns the target entity id, or null if the field is not set.
	 */
	public String getTargetEntityId() {
		return targetEntityId;
	}

	/**
	 * Returns the set of properties as a map.
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * Returns the event time, or null if the field is not set.
	 */
	public DateTime getEventTime() {
		return eventTime;
	}

	// builder methods for convenience

	public Event eventId(String eventId) {
		this.eventId = eventId;
		return this;
	}

	public Event engineId(String engineId) {
		this.engineId = engineId;
		return this;
	}

	/**
	 * Sets the name of the event.
	 */
	public Event event(String event) {
		this.event = event;
		return this;
	}

	/**
	 * Sets the entity type. entityType-entityId forms the unique identifier of the entity.
	 */
	public Event entityType(String entityType) {
		this.entityType = entityType;
		return this;
	}

	/**
	 * Sets the entity id. entityType-entityId forms the unique identifier of the entity.
	 */
	public Event entityId(String entityId) {
		this.entityId = entityId;
		return this;
	}

	public Event targetEntityType(String targetEntityType) {
		this.targetEntityType = targetEntityType;
		return this;
	}

	public Event targetEntityId(String targetEntityId) {
		this.targetEntityId = targetEntityId;
		return this;
	}

	public Event property(String key, Object value) {
		this.properties.put(key, value);
		return this;
	}

	public Event properties(Map<String, Object> properties) {
		this.properties.putAll(properties);
		return this;
	}

	public Event eventTime(DateTime eventTime) {
		this.eventTime = eventTime;
		return this;
	}

	public Event creationTime(DateTime creationTime) {
		this.creationTime = creationTime;
		return this;
	}

	// toJsonString and toString methods

	public String toJsonString() {
		return toString();
	}

	@Override
	public String toString() {
		// handle DateTime separately
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeAdapter());
		Gson gson = gsonBuilder.create();
		return gson.toJson(this); // works when there are no generic types
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEngineId() {
		return engineId;
	}

	public void setEngineId(String engineId) {
		this.engineId = engineId;
	}

	public DateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(DateTime creationTime) {
		this.creationTime = creationTime;
	}
}
