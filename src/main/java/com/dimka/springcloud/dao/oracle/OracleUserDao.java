package com.dimka.springcloud.dao.oracle;

import com.dimka.springcloud.dao.UserDao;
import com.dimka.springcloud.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Component
@Transactional(rollbackFor = {Exception.class })
public class OracleUserDao implements UserDao {

    private final SessionFactory sessionFactory;

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
