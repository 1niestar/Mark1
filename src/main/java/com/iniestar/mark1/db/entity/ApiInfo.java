package com.iniestar.mark1.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "api_info")
@Getter
@Setter
public class ApiInfo extends BaseTimeEntity implements Serializable {
    @Id
    @Column(name = "uri", nullable = false)
    String uri;

    @Column(name = "params")
    String params;

    @Column(name = "client_ip")
    String clientIp;

    @Column(name = "access_token")
    String accessToken;

    @Column(name = "http_method")
    String method = "POST";

    @Column(name = "headers")
    String headers;

    @Column(name = "secret_key")
    String secretKey;

    @OneToOne
    @JoinColumn(name = "refresh_token_num", referencedColumnName = "refresh_token_num")
    RefreshToken refreshToken;
}
