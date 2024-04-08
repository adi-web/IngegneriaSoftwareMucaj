package com.company.domainModel;

public class Address {

    public int id;
    public String name ;
    public int number;
    public int city;

    public Address(int id,String address, int number, int city) {
        this.id=id;
        this.name = address;
        this.number = number;
        this.city = city;
    }

    public Address() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", city=" + city +
                '}';
    }
}
