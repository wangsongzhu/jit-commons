package com.nonisystems.jit.service;

import com.nonisystems.jit.common.dto.User;
import com.nonisystems.jit.common.exception.GeneralException;

public interface UserService {

    public User getUserByEmail(String email) throws GeneralException;

    public User createUser(User user, String roleName) throws GeneralException;

    public void updateUserVerified(String email) throws GeneralException;

    public void changePassword(String email, String password) throws GeneralException;
}
