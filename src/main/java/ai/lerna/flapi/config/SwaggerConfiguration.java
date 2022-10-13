package ai.lerna.flapi.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
	@Bean
	public GroupedOpenApi testApi() {
		return GroupedOpenApi.builder()
			.group("Test API")
			.pathsToMatch("/test/**")
			.build();
	}

	@Bean
	public GroupedOpenApi lernaApi() {
		return GroupedOpenApi.builder()
			.group("Lerna API")
			.pathsToMatch("/api/v1/**")
			.build();
	}

	@Bean
	public GroupedOpenApi lernaApiV2() {
		return GroupedOpenApi.builder()
			.group("Lerna API Ver 2.0")
			.pathsToMatch("/api/v2/**")
			.build();
	}

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
			.info(new Info().title("Lerna FL API")
				.description("Lerna Federated Learning application")
				.version("v0.0.1")
				.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
			.externalDocs(new ExternalDocumentation()
				.description("Lerna AI")
				.url("https://lerna.ai"));
	}
}
