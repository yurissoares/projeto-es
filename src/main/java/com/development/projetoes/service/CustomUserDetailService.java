package com.development.projetoes.service;

import java.util.List;

import com.development.projetoes.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.development.projetoes.entity.UserEntity;

@Component
public class CustomUserDetailService implements UserDetailsService {

	private IUserRepository userRepository;

	@Autowired
	public CustomUserDetailService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Email inexistente."));

		List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
		List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getSenha(),
				user.getIsAdmin() ? authorityListAdmin : authorityListUser);
	}

}
