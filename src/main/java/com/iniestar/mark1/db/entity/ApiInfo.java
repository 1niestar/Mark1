package com.iniestar.mark1.db.entity;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "API_INFO")
@Data
public class ApiInfo extends BaseTimeEntity implements Serializable {
    @Id
    @Column(name = "URI", nullable = false)
    String uri;

    @Column(name = "PARAMS")
    String params;

    @Column(name = "CLIENT_IP")
    String clientIp;

    @Column(name = "ACCESS_TOKEN")
    String accessToken;

    @Column(name = "HTTP_METHOD")
    String method = "POST";

    @Column(name = "HEADERS")
    String headers;

    @Column(name = "SECRET_KEY")
    String secretKey;

    @OneToOne
    @JoinColumn(name = "REFRESH_TOKEN", referencedColumnName = "TOKEN_NUM")
    RefreshToken refreshToken;
}
