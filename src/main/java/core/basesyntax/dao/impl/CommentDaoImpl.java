package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            List<Comment> comments = session
                    .createQuery("from Comment", Comment.class).getResultList();
            session.getTransaction().commit();
            return comments;
        }
    }

    @Override
    public void remove(Comment entity) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Comment attached = session.get(Comment.class, entity.getId());
            if (attached != null) {
                session.delete(attached);
            }
            session.getTransaction().commit();
        }
    }
}
