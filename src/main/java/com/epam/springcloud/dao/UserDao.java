package com.epam.springcloud.dao;

import com.epam.springcloud.entity.user.User;
import com.epam.springcloud.entity.user.UserDTO;

public interface UserDao {
    /**
     * 
     * @param user
     *            which you want to add to data source
     * 
     */
    UserDTO registrateUser(User user) throws Exception;

    /**
     * 
     * @param user
     *            which you want to get
     * @return user or null if user does not exist
     */
    UserDTO getRegistredUser(User user) throws Exception;

}
