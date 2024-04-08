package com.company.domainModel;

import java.sql.Date;
import java.time.LocalDateTime;

public class Rider extends User {

    private int id;
    private String plateScooter;
    private Date startWork;
    private String finishWork;
    private int address;

    public Rider() {}


    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rider(int id,String name, String surname, String phone, String plateScooter, int address)
    {
        super(name, surname, phone,address);
        this.id=id;
        this.address=address;
        this.plateScooter=plateScooter;
        //this.startWork=startWork;
    }
    
    public String getPlateScooter() {
        return plateScooter;
    }

    public void setPlateScooter(String plateScooter) {
        this.plateScooter = plateScooter;
    }

    public Date getStartWork() {
        return startWork;
    }

    public void setStartWork(Date startWork) {
        this.startWork = startWork;
    }

    public String getFinishWork() {
        return finishWork;
    }

    public void setFinishWork(String finishWork) {
        this.finishWork = finishWork;
    }
}
