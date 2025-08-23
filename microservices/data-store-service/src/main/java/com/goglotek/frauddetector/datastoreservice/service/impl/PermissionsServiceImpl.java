package com.goglotek.frauddetector.datastoreservice.service.impl;

import java.util.List;

import com.goglotek.frauddetector.datastoreservice.model.Permissions;
import com.goglotek.frauddetector.datastoreservice.repository.PermissionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goglotek.frauddetector.datastoreservice.service.PermissionsService;


@Service
public class PermissionsServiceImpl implements PermissionsService {
	@Autowired
	PermissionsRepository permissionsRepository;

	@Override
	public List<Permissions> createAll(List<Permissions> perms) {
		return permissionsRepository.saveAll(perms);
	}
}
