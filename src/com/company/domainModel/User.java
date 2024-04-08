package com.company.domainModel;

public abstract class User {
    public String name;
    public String surname;
    public String phone;
    public int idAdrress;

    public User() {

    }

    public int getIdAdrress() {
        return idAdrress;
    }

    public void setIdAdrress(int idAdrress) {
        this.idAdrress = idAdrress;
    }

    public User(String name, String surname, String phone, int idAdrress) {
        this.surname = surname;
        this.phone = phone;
        this.name = name;
        this.idAdrress=idAdrress;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }


}

