package com.nonisystems.jit.service;

import com.nonisystems.jit.common.dto.Url;
import com.nonisystems.jit.common.exception.GeneralException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UrlService {
    Url findById(String id) throws GeneralException;

    List<Url> findAllByUserId(String userId) throws GeneralException;

    Url createUrl(Url url) throws GeneralException;

    void updateUrl(Url url) throws GeneralException;

    void deleteById(String id);

    void deleteByUserId(String userId);
}
