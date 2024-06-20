package com.spring.todo.domain.project.repository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.spring.todo.domain.project.entity.Project;
import com.spring.todo.domain.project.entity.QProject;

@Repository
public class ProjectQueryDSLRepository extends QuerydslRepositorySupport {

	@Autowired
	private JPAQueryFactory jpaQueryFactory;

	private QProject qProject;

	public ProjectQueryDSLRepository() {
		super(Project.class);
		this.qProject = QProject.project;
	}

	@Transactional
    public long updateProject(Long id, String name, String description) {
        QProject qProject = QProject.project;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qProject)
                .where(qProject.id.eq(id));

        boolean isUpdated = false;

        if (name != null) {
            updateClause.set(qProject.name, name);
            isUpdated = true;
        }
        if (description != null) {
            updateClause.set(qProject.description, description);
            isUpdated = true;
        }
        if (isUpdated) {
            updateClause.set(qProject.updatedAt, LocalDateTime.now());
            return updateClause.execute();
        }

        return 0;
    }

}
