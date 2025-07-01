package com.iniestar.mark1.service;

import com.iniestar.mark1.db.entity.ApiInfo;
import com.iniestar.mark1.db.repo.ApiInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService {

    @Autowired
    ApiInfoRepository repo;

    public void save(ApiInfo entity) {
        repo.save(entity);
    }

    public boolean isEmptyWithUri(String uri) {
        List<ApiInfo> list = repo.findAllByUri(uri);
        return list.isEmpty();
    }

    public ApiInfo getOneByUri(String uri) {
        List<ApiInfo> list = repo.findAllByUri(uri);

        if(list.isEmpty()) return null;
        return list.get(0);
    }

    public ApiInfo getOneByUriAndMethod(String uri, String method) {
        return repo.findOneByUriAndMethod(uri, method).orElse(null);
    }

}
