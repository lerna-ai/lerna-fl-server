package ai.lerna.flapi.api

import spock.lang.Specification

class TestApiSpec extends Specification {
  TestApi testApi

  def setup() {
    testApi = new TestApiImpl()
  }

  def "Should return correct response"() {
    given:

    when:
      String response = testApi.index()
    then:
      response == "FL API"
  }
}
