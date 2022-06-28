package com.iniestar.mark1.db.repo;

import com.iniestar.mark1.db.entity.Admin;
import com.iniestar.mark1.db.entity.ApiInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {

    List<Admin> findAllByAdminId(String adminId);
    Admin findOneByAdminId(String adminId);
    Admin findOneByAdminName(String adminName);

}
