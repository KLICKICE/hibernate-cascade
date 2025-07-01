package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            List<Message> messages = session
                    .createQuery("from Message", Message.class).getResultList();
            session.getTransaction().commit();
            return messages;
        }
    }

    @Override
    public void remove(Message entity) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Message attached = session.get(Message.class, entity.getId());
            if (attached != null) {
                session.delete(attached);
            }
            session.getTransaction().commit();
        }
    }
}
