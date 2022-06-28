package com.iniestar.mark1.db.repo;

import com.iniestar.mark1.db.entity.ApiInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ApiListRepository extends JpaRepository<ApiInfo, String>, JpaSpecificationExecutor<ApiInfo> {

   List<ApiInfo> findAllByUri(String uri);

   Optional<ApiInfo> findOneByUriAndMethod(String uri, String method);
}
