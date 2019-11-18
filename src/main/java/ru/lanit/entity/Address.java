package ru.lanit.entity;

import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String house;

    private String flat;

    @Formula("concat(street, ', ', house, ' - ', flat)")
    private String fullAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    public Address() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getFullAddress() {
        return fullAddress;
    }
}
