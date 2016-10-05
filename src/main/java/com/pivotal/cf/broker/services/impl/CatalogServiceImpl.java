package com.pivotal.cf.broker.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pivotal.cf.broker.model.ServiceDefinition;
import com.pivotal.cf.broker.repositories.PlanRepository;
import com.pivotal.cf.broker.repositories.ServiceDefinitionRepository;
import com.pivotal.cf.broker.services.BaseService;
import com.pivotal.cf.broker.services.CatalogService;

@Service
public class CatalogServiceImpl extends BaseService implements CatalogService{

	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private ServiceDefinitionRepository serviceRepository;

	@Override
	public ServiceDefinition createServiceDefinition(ServiceDefinition serviceDefinition) {
		return serviceRepository.save(serviceDefinition);
	}

	@Override
	public List<ServiceDefinition> listServices() {
		return makeCollection(serviceRepository.findAll());
	}

	@Override
	public boolean deleteServiceDefinition(String serviceDefinitionId) {
		if(!serviceRepository.exists(serviceDefinitionId)){
			return false;
		}
		ServiceDefinition serviceDefinition = serviceRepository.findOne(serviceDefinitionId);
		if(planRepository.countByServiceDefinition(serviceDefinition) > 0){
			throw new IllegalStateException("Can not remove service instance, the instance has plans associated to it");
		}
		serviceRepository.delete(serviceDefinitionId);
		return true;
	}
	
}
