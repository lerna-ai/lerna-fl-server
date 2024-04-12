package ai.lerna.flapi.api.dto

import org.joda.time.DateTime
import spock.lang.Specification

class RecommendationEventSpec extends Specification {
	def "Should return correct CSV response"() {
		given:
			RecommendationEvent event = RecommendationEvent.newBuilder()
					.setEngineId("engine")
					.setEvent("event")
					.setEntityId("entity")
					.setTargetEntityId("targetEntity")
					.setEventTime(DateTime.parse("2024-04-10T15:30:45.123+00:00"))
					.build()
		when:
			String response = event.toCsv()
		then:
			response == "engine,event,entity,targetEntity,2024-04-10T15:30:45.123+0000"
	}
}
