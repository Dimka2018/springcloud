package com.dimka.springcloud.dao;


import com.dimka.springcloud.entity.User;

public interface UserDao {
    /**
     * 
     * @param user
     *            which you want to add to data source
     * 
     */
    User registrateUser(User user) throws Exception;

    /**
     * 
     * @param user
     *            which you want to get
     * @return user or null if user does not exist
     */
    User getRegistredUser(User user) throws Exception;

}
