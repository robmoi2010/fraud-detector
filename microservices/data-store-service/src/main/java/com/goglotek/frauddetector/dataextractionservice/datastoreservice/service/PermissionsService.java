package com.goglotek.frauddetector.dataextractionservice.datastoreservice.service;

import java.util.List;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.Permissions;

public interface PermissionsService {

	List<Permissions> createAll(List<Permissions> perms);

}