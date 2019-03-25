package com.epam.springcloud.dao.oracle;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.epam.springcloud.dao.UserDao;
import com.epam.springcloud.entity.user.User;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@Transactional(rollbackFor = { Exception.class })
public class OracleUserDao implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = {
            Exception.class })
    @Override
    public User registrateUser(User user) throws Exception {
        if (!isLoginExists(user.getLogin())) {
            log.debug("user is not registred: " + user);
            Integer userId = (Integer) sessionFactory.getCurrentSession()
                    .save(user);
            user.setId(userId);
            return user;
        }
        log.debug("user already eists: " + user);
        return null;
    }

    public boolean isLoginExists(String userLogin) {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(User.class);
        criteria.add(Restrictions.eq("login", userLogin));
        return criteria.uniqueResult() != null;
    }

    @Transactional(readOnly = true)
    @Override
    public User getRegistredUser(User user) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("login", user.getLogin()))
                .add(Restrictions.eq("password", user.getPassword()));
        return (User) criteria.uniqueResult();

    }
}
