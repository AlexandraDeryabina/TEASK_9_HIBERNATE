package ru.lanit.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.lanit.entity.Person;
import ru.lanit.provider.SessionProvider;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

public class PersonRepository {
    private static PersonRepository instance;

    private PersonRepository() {}

    public static PersonRepository getInstance() {
        if (instance == null) {
            instance = new PersonRepository();
        }
        return instance;
    }

    public List<Person> getList(boolean needFullFetch) {
        try (Session session = SessionProvider.getInstance().getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Person> query = builder.createQuery(Person.class);
            Root<Person> root = query.from(Person.class);
            if (needFullFetch) {
                root.fetch("addressList", JoinType.LEFT);
            }
            query.distinct(true);
            query.orderBy(builder.asc(root.get("fullName")));
            Query<Person> q = session.createQuery(query);
            return q.getResultList();
        }
    }

    public List<Person> getList() {
        return getList(false);
    }

    public Person getById(int id) {
        try (Session session = SessionProvider.getInstance().getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Person> query = builder.createQuery(Person.class);
            Root<Person> root = query.from(Person.class);
            query.select(root).where(builder.equal(root.get("id"), id));
            Query<Person> q = session.createQuery(query);
            return q.getSingleResult();
        }
    }

    public void save(Person person) {
        try (Session session = SessionProvider.getInstance().getSession()) {
            session.beginTransaction();
            session.save(person);
            session.getTransaction().commit();
        }
    }
}
