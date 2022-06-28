package com.iniestar.mark1.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
public class RefreshToken implements Serializable {
    @Id
    @Column(name = "refresh_token_num")
    Long refreshTokenNum;

    @Column(name = "uri")
    String uri;

    @Column(name = "token")
    String token;

    @Column(name = "expiry_date")
    LocalDateTime expiryDate;

    @OneToOne(mappedBy = "refreshToken", cascade = CascadeType.REMOVE, orphanRemoval = true)
    ApiInfo apiInfo;
}
