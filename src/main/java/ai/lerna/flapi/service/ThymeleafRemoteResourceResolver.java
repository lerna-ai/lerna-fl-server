package ai.lerna.flapi.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.util.Collections;

@Service
public class ThymeleafRemoteResourceResolver extends StringTemplateResolver {
	public ThymeleafRemoteResourceResolver() {
		setResolvablePatterns(Collections.singleton("*"));
	}
}
