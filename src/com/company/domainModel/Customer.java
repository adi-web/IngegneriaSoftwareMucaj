package com.company.domainModel;

public class Customer extends User {
    private int id;
    private int totOrder;
    private boolean discount;


    public Customer(int id,String nome, String cognome, String telefono,int idAddress) {
        super(nome, cognome, telefono,idAddress);
        this.id=id ;
    }

    public Customer() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public int getTotOrder() {
        return totOrder;
    }

    public void setTotOrder(int totOrder) {
        this.totOrder = totOrder;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", totOrder=" + totOrder +
                ", sconto=" + discount +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", idAdrress=" + idAdrress +
                '}';
    }
}
