package ai.lerna.flapi.config;

import ai.lerna.flapi.service.ThymeleafRemoteResourceResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class ThymeleafConfiguration {

	public static final String TEMPLATES_BASE = "classpath:/templates/";
	public static final String JSON_TEMPLATES_RESOLVE_PATTERN = "json/*";
	private final ThymeleafRemoteResourceResolver thymeleafRemoteResourceResolver;

	@Autowired
	public ThymeleafConfiguration(ThymeleafRemoteResourceResolver thymeleafRemoteResourceResolver) {
		this.thymeleafRemoteResourceResolver = thymeleafRemoteResourceResolver;
	}

	@Bean
	public SpringResourceTemplateResolver jsonMessageTemplateResolver() {
		SpringResourceTemplateResolver theResourceTemplateResolver = new SpringResourceTemplateResolver();
		theResourceTemplateResolver.setPrefix(TEMPLATES_BASE);
		theResourceTemplateResolver.setResolvablePatterns(
				Collections.singleton(JSON_TEMPLATES_RESOLVE_PATTERN));
		theResourceTemplateResolver.setSuffix(".json");
		theResourceTemplateResolver.setCharacterEncoding("UTF-8");
		theResourceTemplateResolver.setCacheable(false);
		theResourceTemplateResolver.setOrder(2);
		return theResourceTemplateResolver;
	}

	@Bean
	public SpringTemplateEngine messageTemplateEngine(
			final Collection<SpringResourceTemplateResolver> inTemplateResolvers) {
		final SpringTemplateEngine theTemplateEngine = new SpringTemplateEngine();
		for (SpringResourceTemplateResolver theTemplateResolver : inTemplateResolvers) {
			theTemplateEngine.addTemplateResolver(theTemplateResolver);
			theTemplateEngine.addTemplateResolver(thymeleafRemoteResourceResolver);
		}
		return theTemplateEngine;
	}
}
