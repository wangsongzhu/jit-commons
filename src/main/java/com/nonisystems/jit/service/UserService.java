package com.nonisystems.jit.service;

import com.nonisystems.jit.common.dto.Tag;
import com.nonisystems.jit.common.dto.User;
import com.nonisystems.jit.common.exception.GeneralException;

public interface UserService {
    /**
     * Get current login user id
     *
     * @return user id
     */
    String getLoggedInUserId();

    /**
     * Check if current user logged in
     *
     * @return true if logged in otherwise false
     */
    boolean checkIfCurrentUserLoggedIn();

    /**
     * Check if current user an administrator
     *
     * @return true if administrator otherwise false
     */
    boolean isCurrentUserAdmin();

    /**
     * Sign Up a new User
     *
     * @param user input user
     * @return user with generated id
     * @throws GeneralException 400 or 409
     */
    User createUser(User user) throws GeneralException;

    /**
     * Sign Up a new User
     *
     * @param user     input user
     * @param roleName role name
     * @return user with generated id
     * @throws GeneralException 400 or 409
     */
    User createUser(User user, String roleName) throws GeneralException;

    /**
     * Get a user by user sub
     *
     * @param subject user sub of Logto
     * @throws GeneralException 400, 404
     */
    User getUserBySub(String subject) throws GeneralException;

    /**
     * Get a user by email
     *
     * @param email user email
     * @throws GeneralException 400, 404
     */
    User getUserByEmail(String email) throws GeneralException;

//    /**
//     * Update verified flag of a user
//     *
//     * @param email User email
//     * @throws GeneralException 400, 404
//     */
//    void updateUserVerified(String email) throws GeneralException;

//    /**
//     * Update user's password
//     *
//     * @param email    User email
//     * @param password User password
//     * @throws GeneralException 400, 404
//     */
//    void changePassword(String email, String password) throws GeneralException;

    /**
     * Create a tag for a user
     *
     * @param tagName tagName
     * @param email   user email
     * @return TagEntity created
     */
    Tag createTagForUser(String tagName, String email) throws GeneralException;
}
