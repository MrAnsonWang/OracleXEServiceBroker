package com.pivotal.cf.broker.services;

import java.util.List;

import com.pivotal.cf.broker.model.ServiceDefinition;

public interface CatalogService {

	public ServiceDefinition createServiceDefinition(ServiceDefinition serviceDefinition);
	public boolean deleteServiceDefinition(String serviceDefinitionId);
	
	public List<ServiceDefinition> listServices();
	
}
