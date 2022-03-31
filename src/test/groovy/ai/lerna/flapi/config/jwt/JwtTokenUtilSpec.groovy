package ai.lerna.flapi.config.jwt

import ai.lerna.flapi.entity.LernaUser
import org.apache.commons.lang3.StringUtils
import spock.lang.Ignore
import spock.lang.Specification

class JwtTokenUtilSpec extends Specification {
	JwtTokenUtil jwtTokenUtil;

	def setup() {
		jwtTokenUtil = new JwtTokenUtil()
	}

	@Ignore // ToDo: fix issue with null jwt secret
	def "Should generate token with correct format"() {
		given:
			LernaUser user = new LernaUser(id: 1, email: "test@lerna.ai")
		when:
			String result = jwtTokenUtil.generateToken(user)
		then:
			result.startsWith("eyJhbGciOiJIUzUxMiJ9.")
			StringUtils.countMatches(result, ".") == 2
			jwtTokenUtil.getUsernameFromToken(result) == "test@lerna.ai"
			!jwtTokenUtil.isTokenExpired(result)
	}
}
