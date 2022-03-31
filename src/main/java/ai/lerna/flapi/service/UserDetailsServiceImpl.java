package ai.lerna.flapi.service;

import ai.lerna.flapi.entity.LernaUser;
import ai.lerna.flapi.repository.LernaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	private final LernaUserRepository lernaUserRepository;

	@Autowired
	public UserDetailsServiceImpl(LernaUserRepository lernaUserRepository) {
		this.lernaUserRepository = lernaUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		final LernaUser lernaUser = lernaUserRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		return new User(lernaUser.getEmail(), lernaUser.getPassword(), new ArrayList<>());
	}
}
