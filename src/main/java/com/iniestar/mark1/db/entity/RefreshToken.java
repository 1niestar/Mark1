package com.iniestar.mark1.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "REFRESH_TOKEN")
@Getter
@Setter
public class RefreshToken implements Serializable {
    @Id
    @Column(name = "TOKEN_NUM")
    Long tokenNum;

    @Column(name = "URI")
    String uri;

    @Column(name = "TOKEN")
    String token;

    @Column(name = "EXPIRY_DATE")
    LocalDateTime expiryDate;

    @OneToOne(mappedBy = "refreshToken", cascade = CascadeType.REMOVE, orphanRemoval = true)
    ApiInfo apiInfo;
}
