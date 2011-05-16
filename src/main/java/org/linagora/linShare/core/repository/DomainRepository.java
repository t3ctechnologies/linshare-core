package org.linagora.linShare.core.repository;

import java.util.List;

import org.linagora.linShare.core.domain.entities.Domain;

public interface DomainRepository extends AbstractRepository<Domain> {
	
	public Domain findById(String identifier);

	public List<String> findAllIdentifiers();

}
