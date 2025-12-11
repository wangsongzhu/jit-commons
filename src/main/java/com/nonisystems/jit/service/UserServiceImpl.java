package com.nonisystems.jit.service;

import com.nonisystems.jit.common.config.util.UUIDGenerator;
import com.nonisystems.jit.common.converter.UserEntityConverter;
import com.nonisystems.jit.common.dto.JwtUserDetails;
import com.nonisystems.jit.common.dto.Tag;
import com.nonisystems.jit.common.dto.User;
import com.nonisystems.jit.common.exception.GeneralException;
import com.nonisystems.jit.domain.entity.RoleEntity;
import com.nonisystems.jit.domain.entity.TagEntity;
import com.nonisystems.jit.domain.entity.UserEntity;
import com.nonisystems.jit.domain.repository.RoleRepository;
import com.nonisystems.jit.domain.repository.TagRepository;
import com.nonisystems.jit.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TagRepository tagRepository;

    private final UUIDGenerator uuidGenerator;
    private final PasswordEncoder passwordEncoder;

    private final UserEntityConverter userEntityConverter;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, TagRepository tagRepository, UUIDGenerator uuidGenerator, PasswordEncoder passwordEncoder, UserEntityConverter userEntityConverter) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tagRepository = tagRepository;
        this.uuidGenerator = uuidGenerator;
        this.passwordEncoder = passwordEncoder;
        this.userEntityConverter = userEntityConverter;
    }

    /**
     * Get current login user id
     *
     * @return user id
     */
    @Override
    public String getLoggedInUserId() {
        String userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                userId = userDetails.getUserId();
            }
        }
        if (StringUtils.isBlank(userId)) {
            throw new GeneralException(400, "validation.access.not_logged_in");
        }
        return userId;
    }

    /**
     * Check if current user logged in
     *
     * @return true if logged in otherwise false
     */
    @Override
    public boolean checkIfCurrentUserLoggedIn() {
        String userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                userId = userDetails.getUserId();
            }
        }
        if (StringUtils.isBlank(userId)) {
            throw new GeneralException(400, "validation.access.not_logged_in");
        }
        return true;
    }

    /**
     * Check if current user an administrator
     *
     * @return true if administrator otherwise false
     */
    @Override
    public boolean isCurrentUserAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities() != null) {
            return authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().contains("ADMIN"));
        }
        return false;
    }

    /**
     * Sign Up a new User
     *
     * @param user     input user
     * @param roleName role name
     * @return user with generated id
     * @throws GeneralException 400 or 409
     */
    @Transactional
    @Override
    public User createUser(User user, String roleName) throws GeneralException {
        if (log.isDebugEnabled()) {
            log.debug("Creating new user {}", user);
        }
        if (StringUtils.isBlank(user.getEmail())) {
            throw new GeneralException(400, "validation.email.required");
        }
        // Check if user exists or not (by email)
        Optional<UserEntity> userOptional = this.userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new GeneralException(409, "validation.email.existed");
        }
        // Check if role exists or not
        Optional<RoleEntity> roleOptional = roleRepository.findByName(roleName);
        if (roleOptional.isEmpty()) {
            throw new GeneralException(400, "validation.role.not_found");
        }
        // Save user
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(user.getEmail());
        userEntity.setId(this.uuidGenerator.generateUUID(user.getEmail()));
        userEntity.setPasswordHash(this.passwordEncoder.encode(user.getPassword()));
        userEntity.setRoles(Collections.singleton(roleOptional.get()));
        UserEntity savedUserEntity = this.userRepository.save(userEntity);
        // Get the latest user and return
        User savedUser = this.userEntityConverter.convert(savedUserEntity);
        if (log.isDebugEnabled()) {
            log.debug("Created new user {}", savedUser);
        }
        return savedUser;
    }

    /**
     * Get a user by email
     *
     * @param email user email
     * @throws GeneralException 400, 404
     */
    @Override
    public User getUserByEmail(String email) throws GeneralException {
        if (log.isDebugEnabled()) {
            log.debug("Getting user by email {}", email);
        }
        // Check email
        if (StringUtils.isBlank(email)) {
            throw new GeneralException(400, "validation.email.required");
        }
        // Check if user existing
        Optional<UserEntity> userOptional = this.userRepository.findByEmailWithRolesAndPermissions(email);
        UserEntity userEntity = userOptional.orElseThrow(() ->
                new GeneralException(404, "validation.email.not_found"));
        if (log.isDebugEnabled()) {
            log.debug("Found userEntity {}", userEntity);
        }
        // Return user
        User user = this.userEntityConverter.convert(userEntity);
        if (log.isDebugEnabled()) {
            log.debug("Found user {}", user);
        }
        return user;
    }

    /**
     * Update verified flag of a user
     *
     * @param email User email
     * @throws GeneralException 400, 404
     */
    @Transactional
    @Override
    public void updateUserVerified(String email) throws GeneralException {
        if (log.isDebugEnabled()) {
            log.debug("Updating user verified status to 1 for {}", email);
        }
        // Check id
        if (StringUtils.isBlank(email)) {
            throw new GeneralException(400, "validation.email.required");
        }
        // Check if user existing
        Optional<UserEntity> userOptional = this.userRepository.findByEmail(email);
        UserEntity userEntity = userOptional.orElseThrow(() ->
                new GeneralException(404, "validation.email.not_found"));
        // Update user
        userEntity.setVerified((byte) 1);
        this.userRepository.save(userEntity);
    }

    /**
     * Update user's password
     *
     * @param email    User email
     * @param password User password
     * @throws GeneralException 400, 404
     */
    @Transactional
    @Override
    public void changePassword(String email, String password) throws GeneralException {
        if (log.isDebugEnabled()) {
            log.debug("Updating user {} with password {}", email, password);
        }
        // Check id
        if (StringUtils.isBlank(email)) {
            throw new GeneralException(400, "validation.email.required");
        }
        // Check password
        if (StringUtils.isBlank(password)) {
            throw new GeneralException(400, "validation.password.required");
        }
        // Check if user existing
        Optional<UserEntity> userOptional = this.userRepository.findByEmail(email);
        UserEntity userEntity = userOptional.orElseThrow(() ->
                new GeneralException(404, "validation.email.not_found"));
        // Update user
        userEntity.setPasswordHash(this.passwordEncoder.encode(password));
        this.userRepository.save(userEntity);
    }

    /**
     * Create a tag for a user
     *
     * @param tagName tagName
     * @param email   user email
     * @return TagEntity created
     */
    @Transactional
    @Override
    public Tag createTagForUser(String tagName, String email) throws GeneralException {
        if (log.isDebugEnabled()) {
            log.debug("Creating new tag {} for user {}", tagName, email);
        }
        Optional<UserEntity> userOptional = this.userRepository.findByEmail(email);
        UserEntity userEntity = userOptional.orElseThrow(() ->
                new GeneralException(404, "validation.email.not_found"));

        TagEntity tag = new TagEntity();
        tag.setName(tagName);
        tag.setUser(userEntity);
        TagEntity tagEntity = tagRepository.save(tag);
        return this.userEntityConverter.convert(tagEntity);
    }

//    /**
//     * Verify login user is valid or not
//     * @param user login user
//     * @return true if valid otherwise false
//     */
//    public boolean isValidUser(UserInfo user) {
//        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
//        return authentication.isAuthenticated();
//    }
//
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    /**
//     * Get all users
//     * @return user list
//     */
//    public List<UserInfo> findAllUsers() {
//        Iterable<UserEntity> users = this.userRepository.findAll();
//        List<UserInfo> userList = new ArrayList<>();
//        for (UserEntity userEntity : users) {
//            UserInfo user = this.modelMapper.map(userEntity, UserInfo.class);
//            user.setPasswordHash(null);
//            userList.add(user);
//        }
//        if (log.isDebugEnabled()) {
//            log.debug("Found {} users, details: {}", userList.size(), userList);
//        }
//        return userList;
//    }
//
//    /**
//     * Get a user by id
//     * @param id user id
//     * @throws GeneralException 1002 or 3002
//     */
//    public UserInfo getUser(String id) throws GeneralException {
//        if (log.isDebugEnabled()) {
//            log.debug("Getting user with id {}", id);
//        }
//        // Check id
//        if (StringUtils.isBlank(id)) {
//            throw new GeneralException(1002, "User id is blank");
//        }
//        // Check if user existing
//        UserEntity userEntity = this.userRepository.findUserEntityById(id);
//        if (userEntity == null) {
//            throw new GeneralException(3002, "User not found");
//        }
//        // Get user and remove password
//        UserInfo user = this.modelMapper.map(userEntity, UserInfo.class);
//        user.setPasswordHash(null);
//        if (log.isDebugEnabled()) {
//            log.debug("Found user {}", user);
//        }
//        return user;
//    }
//
//    /**
//     * Delete user by id
//     * @param id user id
//     * @throws GeneralException 1002 or 3002
//     */
//    @Transactional
//    public void deleteUser(String id) throws GeneralException {
//        if (log.isDebugEnabled()) {
//            log.debug("Deleting user with id {}", id);
//        }
//        // Check id
//        if (StringUtils.isBlank(id)) {
//            throw new GeneralException(1002, "User id is blank");
//        }
//        // Check if user existing
//        UserEntity userEntity = this.userRepository.findUserEntityById(id);
//        if (userEntity == null) {
//            throw new GeneralException(3002, "User not found");
//        }
//        this.userRepository.delete(userEntity);
//    }

}
