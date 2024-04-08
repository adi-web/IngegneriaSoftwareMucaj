package com.company.daoModel;

import com.company.domainModel.Address;

import java.sql.SQLException;

public interface AddressDao extends DaoGeneral<Address> {

    int getLastid() throws SQLException, ClassNotFoundException;
}
