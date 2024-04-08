package com.company.daoModel;

import com.company.domainModel.Rider;

import java.sql.SQLException;

public interface RiderDao extends DaoGeneral<Rider> {

    public void changePlate(int id,String plate);
    public void deletePlate(int id);
    //int getidRider(int idRider) throws SQLException, ClassNotFoundException;

}
