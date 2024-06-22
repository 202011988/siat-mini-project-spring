package com.spring.todo.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.spring.todo.domain.user.entity.QUser;
import com.spring.todo.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class UserQueryDSLRepository extends QuerydslRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private QUser qUser;

    public UserQueryDSLRepository() {
        super(User.class);
        this.qUser = QUser.user;
    }

    public long updateUser(Long id, String email, String password, String nickname) {
        QUser qUser = QUser.user;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qUser)
                .where(qUser.id.eq(id));

        boolean isUpdated = false;

        if (email != null) {
            updateClause.set(qUser.email, email);
            isUpdated = true;
        }
        if (nickname != null) {
            updateClause.set(qUser.nickname, nickname);
            isUpdated = true;
        }
        if (password != null) {
            updateClause.set(qUser.password, password);
            isUpdated = true;
        }
        if (isUpdated) {
            return updateClause.execute();
        }

        return 0;
    }
}
