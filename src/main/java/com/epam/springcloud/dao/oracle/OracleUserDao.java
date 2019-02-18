package com.epam.springcloud.dao.oracle;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.epam.springcloud.dao.UserDao;
import com.epam.springcloud.entity.user.User;

@Component
public class OracleUserDao implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    @Override
    public User registrateUser(User user) throws Exception {
        Integer userId = (Integer) sessionFactory.getCurrentSession()
                .save(user);
        user.setId(userId);
        // transaction
        return user;
    }

    @Transactional
    @Override
    public User getRegistredUser(User user) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("login", user.getLogin()))
                .add(Restrictions.eq("password", user.getPassword()));
        return (User) criteria.uniqueResult();

    }
}
