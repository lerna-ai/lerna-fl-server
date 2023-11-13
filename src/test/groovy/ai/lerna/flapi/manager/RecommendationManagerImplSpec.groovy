package ai.lerna.flapi.manager

import ai.lerna.flapi.api.dto.RecommendationItemScore
import ai.lerna.flapi.repository.LernaAppRepository
import ai.lerna.flapi.service.RecommendationService
import ai.lerna.flapi.service.actionML.dto.ItemScore
import spock.lang.Specification

class RecommendationManagerImplSpec extends Specification {

	RecommendationManagerImpl recommendationManagerImpl
	RecommendationService recommendationService
	LernaAppRepository lernaAppRepository

	def setup() {
		recommendationService = Mock(RecommendationService)
		lernaAppRepository = Mock(LernaAppRepository)
		recommendationManagerImpl = new RecommendationManagerImpl(recommendationService, lernaAppRepository)
	}

	def "ConvertItems"() {
		given:
			ItemScore item = new ItemScore()
			item.setProps("{\"item1\":[1,2,3],\"item2\":\"hello\",\"item3\":2.0}")
			item.item = "item"
			item.score = 1.0
			List<String> exclude
			List<String> include
		when:
			RecommendationItemScore result = recommendationManagerImpl.convertItems(item, exclude,include)
		then:
			with(result) {
				getItem() == "item"
				getScore() == 1.0
				getProps().size() == 3
				getProps().containsKey("item1")
				getProps().containsKey("item2")
				getProps().containsKey("item3")
			}
	}

	def "ConvertItems with empty lists"() {
		given:
			ItemScore item = new ItemScore()
			item.setProps("{\"item1\":[1,2,3],\"item2\":\"hello\",\"item3\":2.0}")
			item.item = "item"
			item.score = 1.0
			List<String> exclude = new ArrayList<>()
			List<String> include = new ArrayList<>()
		when:
			RecommendationItemScore result = recommendationManagerImpl.convertItems(item, exclude,include)
		then:
			with(result) {
				getItem() == "item"
				getScore() == 1.0
				getProps().size() == 3
				getProps().containsKey("item1")
				getProps().containsKey("item2")
				getProps().containsKey("item3")
			}
	}

	def "ConvertItems with exclude items"() {
		given:
			ItemScore item = new ItemScore()
			item.setProps("{\"item1\":[1,2,3],\"item2\":\"hello\",\"item3\":2.0}")
			item.item = "item"
			item.score = 1.0
			List<String> exclude = List.of("item2", "item4")
			List<String> include = new ArrayList<>()
		when:
			RecommendationItemScore result = recommendationManagerImpl.convertItems(item, exclude,include)
		then:
			with(result) {
				getItem() == "item"
				getScore() == 1.0
				getProps().size() == 2
				getProps().containsKey("item1")
				!getProps().containsKey("item2")
				getProps().containsKey("item3")
			}
	}

	def "ConvertItems with include items"() {
		given:
			ItemScore item = new ItemScore()
			item.setProps("{\"item1\":[1,2,3],\"item2\":\"hello\",\"item3\":2.0}")
			item.item = "item"
			item.score = 1.0
			List<String> exclude = new ArrayList<>()
			List<String> include = List.of("item2", "item4")
		when:
			RecommendationItemScore result = recommendationManagerImpl.convertItems(item, exclude,include)
		then:
			with(result) {
				getItem() == "item"
				getScore() == 1.0
				getProps().size() == 1
				!getProps().containsKey("item1")
				getProps().containsKey("item2")
				!getProps().containsKey("item3")
			}
	}

	def "ConvertItems with include and exclude items"() {
		given:
			ItemScore item = new ItemScore()
			item.setProps("{\"item1\":[1,2,3],\"item2\":\"hello\",\"item3\":2.0}")
			item.item = "item"
			item.score = 1.0
			List<String> exclude = List.of("item2", "item4")
			List<String> include = List.of("item2", "item3")
		when:
			RecommendationItemScore result = recommendationManagerImpl.convertItems(item, exclude,include)
		then:
			with(result) {
				getItem() == "item"
				getScore() == 1.0
				getProps().size() == 1
				!getProps().containsKey("item1")
				!getProps().containsKey("item2")
				getProps().containsKey("item3")
			}
	}
}
