package com.iniestar.mark1.controller.web;

import com.iniestar.mark1.db.entity.Admin;
import com.iniestar.mark1.db.entity.RoleInfo;
import com.iniestar.mark1.db.repo.AdminRepository;
import com.iniestar.mark1.db.repo.RoleInfoRepository;
import com.iniestar.mark1.utils.Tool;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    RoleInfoRepository roleInfoRepository;

    @PostMapping("/save/permission/{adminId}")
    public void savePermission(HttpServletRequest request, @PathVariable("adminId") String adminId, @RequestBody Map<String, String> permissionList) {

        Admin admin = adminRepository.findOneByAdminId(adminId);
        String permissionJson = Tool.toJson(permissionList);

        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setPermissionData(permissionJson);
        roleInfo.setRole("Manager");
        roleInfoRepository.save(roleInfo);
    }
}
