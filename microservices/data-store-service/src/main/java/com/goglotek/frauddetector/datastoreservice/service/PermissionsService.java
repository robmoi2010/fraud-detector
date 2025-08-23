package com.goglotek.frauddetector.datastoreservice.service;

import java.util.List;

import com.goglotek.frauddetector.datastoreservice.model.Permissions;

public interface PermissionsService {

	List<Permissions> createAll(List<Permissions> perms);

}