package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.UrlEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UrlRepository extends CrudRepository<UrlEntity, Long> {

    UrlEntity findById(@Size(max = 64) String id);

    UrlEntity findByShortUrl(@NotNull @Size(max = 25) String shortUrl);

    List<UrlEntity> findAllByUserId(String userId);
}