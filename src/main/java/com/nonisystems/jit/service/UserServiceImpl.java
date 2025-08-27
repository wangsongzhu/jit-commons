package com.nonisystems.jit.service;

import com.nonisystems.jit.common.converter.UserEntityToUserConverter;
import com.nonisystems.jit.common.config.util.UUIDGenerator;
import com.nonisystems.jit.common.dto.User;
import com.nonisystems.jit.common.exception.GeneralException;
import com.nonisystems.jit.domain.entity.RoleEntity;
import com.nonisystems.jit.domain.entity.UserEntity;
import com.nonisystems.jit.domain.repository.RoleRepository;
import com.nonisystems.jit.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    private final UUIDGenerator uuidGenerator;
    private final PasswordEncoder passwordEncoder;

    private final UserEntityToUserConverter  userEntityToUserConverter;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UUIDGenerator uuidGenerator, PasswordEncoder passwordEncoder, UserEntityToUserConverter  userEntityToUserConverter) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.uuidGenerator = uuidGenerator;
        this.passwordEncoder = passwordEncoder;
        this.userEntityToUserConverter = userEntityToUserConverter;
    }

    /**
     * Sign Up a new User
     *
     * @param user input user
     * @param roleName role name
     * @return user with generated id
     * @throws GeneralException 400 or 409
     */
    @Transactional
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
        User savedUser = this.userEntityToUserConverter.convert(savedUserEntity);
        if (log.isDebugEnabled()) {
            log.debug("Created new user {}", savedUser);
        }
        return savedUser;
    }

    /**
     * Get a user by email
     *
     * @param email user email
     * @throws GeneralException 1002 or 3002
     */
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
        User user = this.userEntityToUserConverter.convert(userEntity);
        if (log.isDebugEnabled()) {
            log.debug("Found user {}", user);
        }
        return user;
    }

    /**
     * Update verified flag of a user
     *
     * @param email User email
     * @throws GeneralException 1002 or 3002
     */
    @Transactional
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

//
//    /**
//     * Update user's password
//     *
//     * @param id User id
//     * @param user User with changed password
//     * @return Updated User
//     * @throws GeneralException 1002 or 3002
//     */
//    @Transactional
//    public UserInfo changePassword(String id, UserInfo user) throws GeneralException {
//        if (log.isDebugEnabled()) {
//            log.debug("Updating user with id {}, user details: {}", id, user);
//        }
//        // Check id
//        if (StringUtils.isBlank(id)) {
//            throw new GeneralException(1002, "User id is blank");
//        }
//        if (!StringUtils.equals(user.getNewPassword(), user.getNewPasswordConfirmation())) {
//            throw new GeneralException(1003, "The password and confirmation password do not match. Please re-enter your password.");
//        }
//        // Check if user existing
//        UserEntity existedUserEntity = this.userRepository.findUserEntityById(id);
//        if (existedUserEntity == null) {
//            throw new GeneralException(3002, "User not found");
//        }
//        // Check if current password matches
//        if (!this.passwordEncoder.matches(user.getPassword(), existedUserEntity.getPasswordHash())) {
//            throw new GeneralException(1004, "The password you entered is incorrect. Please re-enter your password.");
//        }
//        // Update user
//        existedUserEntity.setPasswordHash(this.passwordEncoder.encode(user.getNewPassword()));
//        this.userRepository.save(existedUserEntity);
//        // Get the latest user and return
//        UserEntity savedUserEntity = this.userRepository.findUserEntityById(id);
//        UserInfo savedUser = this.modelMapper.map(savedUserEntity, UserInfo.class);
//        if (log.isDebugEnabled()) {
//            log.debug("Updated user {}", savedUser);
//        }
//        return savedUser;
//    }
//
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
