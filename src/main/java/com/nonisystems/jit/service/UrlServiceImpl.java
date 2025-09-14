package com.nonisystems.jit.service;

import com.nonisystems.jit.common.converter.UrlEntityConverter;
import com.nonisystems.jit.common.dto.Url;
import com.nonisystems.jit.common.exception.GeneralException;
import com.nonisystems.jit.domain.entity.TagEntity;
import com.nonisystems.jit.domain.entity.UrlEntity;
import com.nonisystems.jit.domain.entity.UserEntity;
import com.nonisystems.jit.domain.repository.TagRepository;
import com.nonisystems.jit.domain.repository.UrlRepository;
import com.nonisystems.jit.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Validated
public class UrlServiceImpl implements UrlService {

    private final UserRepository userRepository;
    private final UrlRepository urlRepository;
    private final TagRepository tagRepository;
    private final UrlEntityConverter urlEntityConverter;

    public UrlServiceImpl(UserRepository userRepository, UrlRepository urlRepository, TagRepository tagRepository, UrlEntityConverter urlEntityConverter) {
        this.userRepository = userRepository;
        this.urlRepository = urlRepository;
        this.tagRepository = tagRepository;
        this.urlEntityConverter = urlEntityConverter;
    }

    /**
     * Get url by id
     *
     * @param id url id
     * @return Url
     * @throws GeneralException 400
     */
    @Override
    public Url findById(String id) throws GeneralException {
        if (StringUtils.isBlank(id)) {
            throw new GeneralException(400, "validation.url_id.required");
        }
        Optional<UrlEntity> urlOptional = this.urlRepository.findById(id);
        UrlEntity urlEntity = urlOptional.orElseThrow(() ->
                new GeneralException(404, "validation.url.not_found"));
        return this.urlEntityConverter.convert(urlEntity);
    }

    /**
     * Find all urls by user id
     *
     * @param userId user id
     * @return List of Url
     * @throws GeneralException 400
     */
    @Override
    public List<Url> findAllByUserId(String userId) throws GeneralException {
        if (StringUtils.isBlank(userId)) {
            throw new GeneralException(400, "validation.user_id.required");
        }
        List<UrlEntity> urlEntities = this.urlRepository.findAllByUserId(userId);
        return urlEntities.stream().map(urlEntityConverter::convert).toList();
    }

    /**
     * Create new url
     *
     * @param url new url
     * @return saved url
     * @throws GeneralException 400, 404
     */
    @Transactional
    @Override
    public Url createUrl(Url url) throws GeneralException {
        if (log.isDebugEnabled()) {
            log.debug("Creating new url {}", url);
        }
        if (StringUtils.isBlank(url.getOriginalUrl())) {
            throw new GeneralException(400, "validation.original_url.required");
        }
        if (StringUtils.isBlank(url.getShortUrl())) {
            throw new GeneralException(400, "validation.short_url.required");
        }
        // Check if user exists or not (by email)
        Optional<UserEntity> userOptional = this.userRepository.findById(url.getUserId());
        UserEntity userEntity = userOptional.orElseThrow(() ->
                new GeneralException(404, "validation.email.not_found"));
        // Save url entity
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setTitle(url.getTitle());
        urlEntity.setOriginalUrl(url.getOriginalUrl());
        urlEntity.setShortUrl(url.getShortUrl());
        urlEntity.setShortPart(url.getShortPart());
        urlEntity.setExpirationDate(url.getExpirationDate());
        urlEntity.setClickLimited(url.getClickLimited());
        urlEntity.setClickLimit(url.getClickLimit());
        urlEntity.setEditable(url.getEditable());
        urlEntity.setEdited(url.getEdited());
        urlEntity.setPasswordProtected(url.getPasswordProtected());
        urlEntity.setShowOriginalUrl(url.getShowOriginalUrl());
        urlEntity.setClickCount(url.getClickCount());
        urlEntity.setHasQrCode(url.getHasQrCode());
        urlEntity.setUser(userEntity);
        UrlEntity savedUrlEntity = this.urlRepository.save(urlEntity);
        // Get the latest url and return
        Url savedUrl = this.urlEntityConverter.convert(savedUrlEntity);
        if (log.isDebugEnabled()) {
            log.debug("Created new url {}", savedUrl);
        }
        return savedUrl;
    }

    /**
     * Update a url
     *
     * @param url url to be updated
     * @throws GeneralException 400, 404
     */
    @Transactional
    @Override
    public void updateUrl(Url url) throws GeneralException {
        if (log.isDebugEnabled()) {
            log.debug("Update url {}", url);
        }
        if (StringUtils.isBlank(url.getOriginalUrl())) {
            throw new GeneralException(400, "validation.original_url.required");
        }
        if (StringUtils.isBlank(url.getShortUrl())) {
            throw new GeneralException(400, "validation.short_url.required");
        }
        // Check if user exists or not (by email)
        Optional<UserEntity> userOptional = this.userRepository.findById(url.getUserId());
        UserEntity userEntity = userOptional.orElseThrow(() ->
                new GeneralException(404, "validation.email.not_found"));
        // Save url entity
        Optional<UrlEntity> urlOptional = this.urlRepository.findById(url.getId());
        UrlEntity urlEntity = urlOptional.orElseThrow(() ->
                new GeneralException(404, "validation.url.not_found"));

        urlEntity.setTitle(url.getTitle());
        urlEntity.setOriginalUrl(url.getOriginalUrl());
        urlEntity.setShortUrl(url.getShortUrl());
        urlEntity.setShortPart(url.getShortPart());
        urlEntity.setExpirationDate(url.getExpirationDate());
        urlEntity.setClickLimited(url.getClickLimited());
        urlEntity.setClickLimit(url.getClickLimit());
        urlEntity.setEditable(url.getEditable());
        urlEntity.setEdited(url.getEdited());
        urlEntity.setPasswordProtected(url.getPasswordProtected());
        urlEntity.setShowOriginalUrl(url.getShowOriginalUrl());
        urlEntity.setClickCount(url.getClickCount());
        urlEntity.setHasQrCode(url.getHasQrCode());
        urlEntity.setUser(userEntity);
        this.urlRepository.save(urlEntity);
    }

    /**
     * Delete url by id
     *
     * @param id url id
     */
    @Transactional
    @Override
    public void deleteById(String id) {
        if (!StringUtils.isBlank(id)) {
            this.urlRepository.deleteById(id);
        }
    }

    /**
     * Delete url by userId
     *
     * @param userId user id
     */
    @Transactional
    @Override
    public void deleteByUserId(String userId) {
        if (!StringUtils.isBlank(userId)) {
            this.urlRepository.deleteByUserId(userId);
        }
    }

    /**
     * Add tag to url
     *
     * @param urlId url id
     * @param tagId tag id
     * @return Url with new tag
     */
    @Transactional
    public Url addTagToUrl(String urlId, Long tagId) {
        UrlEntity url = urlRepository.findById(urlId)
                .orElseThrow(() -> new GeneralException(400, "validation.url_id.required"));
        TagEntity tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new GeneralException(400, "validation.tag_id.required"));
        url.getTags().add(tag);
        UrlEntity urlEntity = urlRepository.save(url);
        return urlEntityConverter.convert(urlEntity);
    }
}
