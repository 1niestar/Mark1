package com.iniestar.mark1.db.repo;

import com.iniestar.mark1.db.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {

    List<Admin> findAllByAdminId(String adminId);
    Admin findOneByAdminId(String adminId);
    Admin findOneByAdminName(String adminName);

}
