package com.iniestar.mark1.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@EqualsAndHashCode(callSuper=false)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name = "crt_date", nullable = true, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'kk:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'kk:mm:ss")
    private LocalDateTime crtDate;

    @LastModifiedDate
    @Column(name = "chg_date", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'kk:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'kk:mm:ss")
    private LocalDateTime chgDate;

}
