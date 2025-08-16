package com.goglotek.frauddetector.dataextractionservice.datastoreservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.Permissions;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.Role;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.Users;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.repository.UsersRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.service.UsersService;

@Service
public class UsersServiceImpl implements UserDetailsService, UsersService {
	@Autowired
	UsersRepository userRepository;

	Logger logger = LogManager.getLogger(UsersServiceImpl.class);

	@Override
	public Users findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Users create(Users user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteById(long userId) {
		userRepository.deleteById(userId);
	}

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("MpesaUser " + username + " not found");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		try {
			authorities = getGrantedAuthorities(user.getRoles());
		} catch (Exception e) {
			logger.error(e);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<Role> roles) throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		roles.forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.getName()));
			List<Permissions> perm = r.getPermissions();
			perm.forEach(p -> {
				authorities.add(new SimpleGrantedAuthority(p.getName()));
			});
		});
		return authorities;
	}

	private Users createFilterObject(List<FilterModel> filterModel) {
		Users user = new Users();
		for (FilterModel m : filterModel) {
			String filterColumn = m.getColumnField();
			String value = m.getValue();
			switch (filterColumn.toLowerCase()) {
			case "username":
				user.setUsername(value);
				break;
			case "email":
				user.setEmail(value);
				break;
			case "firstname":
				user.setFirstName(value);
				break;
			case "lastname":
				user.setLastName(value);
				break;
			case "accountverified":
				user.setAccountVerified(Boolean.parseBoolean(value));
				break;
			case "active":
				user.setActive(Boolean.parseBoolean(value));
				break;
			case "failedloginattempts":
				user.setFailedLoginAttempts(Integer.parseInt(value));
				break;
			case "logindisabled":
				user.setLoginDisabled(Boolean.parseBoolean(value));
				break;
			}
		}
		return user;
	}

	private ExampleMatcher createFilterMatcher(List<FilterModel> filterModel) {
		ExampleMatcher matcher = ExampleMatcher.matchingAll();
		for (FilterModel m : filterModel) {
			switch (m.getOperatorValue().toLowerCase()) {
			case "contains": {
				matcher = matcher
						.withMatcher(m.getColumnField(), ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
						.withIgnoreNullValues();
				break;
			}
			case "equals": {
				matcher = matcher
						.withMatcher(m.getColumnField(), ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
						.withIgnoreNullValues();
				break;
			}
			case "startswith": {
				matcher = matcher
						.withMatcher(m.getColumnField(),
								ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
						.withIgnoreNullValues();
				break;
			}
			case "endswith": {
				matcher = matcher
						.withMatcher(m.getColumnField(), ExampleMatcher.GenericPropertyMatchers.endsWith().ignoreCase())
						.withIgnoreNullValues();
				break;
			}
			default:
				matcher = matcher
						.withMatcher(m.getColumnField(), ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase())
						.withIgnoreNullValues();
				break;
			}
		}
		matcher = matcher.withIgnoreNullValues();
		return matcher;
	}

	@Override
	public long countAllFilteredPaged(List<FilterModel> filterModel) {
		Example<Users> example = Example.of(createFilterObject(filterModel), createFilterMatcher(filterModel));
		return userRepository.count(example);
	}

	@Override
	public List<Users> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
                                            List<FilterModel> filterModel) {
		Example<Users> example = Example.of(createFilterObject(filterModel), createFilterMatcher(filterModel));
		return userRepository
				.findAll(example, PageRequest.of(page, limit,
						direction.equalsIgnoreCase("desc") ? Sort.by(order).descending() : Sort.by(order).ascending()))
				.getContent();
	}

	@Override
	public Optional<Users> findById(Long userId) {
		return userRepository.findById(userId);
	}

}
