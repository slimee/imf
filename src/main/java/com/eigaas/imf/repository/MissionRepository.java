package com.eigaas.imf.repository;

import com.eigaas.imf.domain.Mission;
import com.eigaas.imf.domain.Spy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "missions", collectionResourceRel = "missions", itemResourceRel = "mission")
public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findAllBySpy(final Spy spy);
}
