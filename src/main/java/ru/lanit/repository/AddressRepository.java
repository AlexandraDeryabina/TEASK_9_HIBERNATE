package ru.lanit.repository;

import org.hibernate.Session;
import ru.lanit.entity.Address;
import ru.lanit.entity.Person;
import ru.lanit.provider.SessionProvider;

public class AddressRepository {
    private static AddressRepository instance;

    private AddressRepository() {}

    public static AddressRepository getInstance() {
        if (instance == null) {
            instance = new AddressRepository();
        }
        return instance;
    }

    public void save(Address address) {
        try (Session session = SessionProvider.getInstance().getSession()) {
            session.beginTransaction();
            session.save(address);
            session.getTransaction().commit();
        }
    }
}
