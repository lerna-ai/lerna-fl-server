package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.AuthRequest;
import ai.lerna.flapi.api.dto.AuthResponse;
import ai.lerna.flapi.api.dto.UserPasswordChange;
import ai.lerna.flapi.config.jwt.JwtTokenUtil;
import ai.lerna.flapi.entity.LernaApp;
import ai.lerna.flapi.entity.LernaUser;
import ai.lerna.flapi.repository.LernaAppRepository;
import ai.lerna.flapi.repository.LernaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserManagerImpl implements UserManager {

	private final LernaUserRepository lernaUserRepository;

	private final LernaAppRepository lernaAppRepository;

	private final AuthenticationManager authenticationManager;

	private final JwtTokenUtil jwtTokenUtil;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserManagerImpl(LernaUserRepository lernaUserRepository, LernaAppRepository lernaAppRepository, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder) {
		this.lernaUserRepository = lernaUserRepository;
		this.lernaAppRepository = lernaAppRepository;
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public AuthResponse createAuthenticationToken(AuthRequest authRequest) throws Exception {

		authenticate(authRequest.getEmail(), authRequest.getPassword());

		final LernaUser lernaUser = lernaUserRepository.findByEmail(authRequest.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + authRequest.getEmail()));

		final String token = jwtTokenUtil.generateToken(lernaUser);

		return new AuthResponse(token);
	}

	@Override
	public LernaUser getProfile(String token) {
		Long userId = jwtTokenUtil.getUserIdFromToken(jwtTokenUtil.getJwtFromBearerToken(token));
		return lernaUserRepository.findById(userId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
	}

	@Override
	public List<LernaApp> getUserApps(String token) {
		Long userId = jwtTokenUtil.getUserIdFromToken(jwtTokenUtil.getJwtFromBearerToken(token));
		return lernaAppRepository.findByUserIdOrderById(userId);
	}

	@Override
	public String changePassword(LernaUser user, UserPasswordChange userPasswordChange) throws Exception {
		authenticate(user.getEmail(), userPasswordChange.getCurrent());
		user.setPassword(passwordEncoder.encode(userPasswordChange.getNewPassword()));
		lernaUserRepository.save(user);
		return "OK";
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
