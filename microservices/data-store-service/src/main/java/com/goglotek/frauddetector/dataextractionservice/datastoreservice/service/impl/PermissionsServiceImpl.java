package com.goglotek.frauddetector.dataextractionservice.datastoreservice.service.impl;

import java.util.List;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.Permissions;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.repository.PermissionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.service.PermissionsService;


@Service
public class PermissionsServiceImpl implements PermissionsService {
	@Autowired
	PermissionsRepository permissionsRepository;

	@Override
	public List<Permissions> createAll(List<Permissions> perms) {
		return permissionsRepository.saveAll(perms);
	}
}
