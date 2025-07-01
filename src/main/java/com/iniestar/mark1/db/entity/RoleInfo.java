package com.iniestar.mark1.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "ROLE_INFO")
@Getter@Setter
public class RoleInfo implements Serializable {

    @Id
    @Column(name = "role_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long roleNum;

    @Column(name = "permission_data")
    String permissionData;

    @Column(name = "role")
    String role;

    @CreatedDate
    @Column(name = "crt_date", nullable = true, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'kk:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'kk:mm:ss")
    private LocalDateTime crtDate;

}
