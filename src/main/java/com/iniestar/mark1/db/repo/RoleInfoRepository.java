package com.iniestar.mark1.db.repo;

import com.iniestar.mark1.db.entity.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleInfoRepository extends JpaRepository<RoleInfo, Long>, JpaSpecificationExecutor<RoleInfo> {
}
