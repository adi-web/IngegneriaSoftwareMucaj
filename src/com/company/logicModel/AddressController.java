package com.company.logicModel;

import com.company.daoModel.AddressDao;
import com.company.domainModel.Address;

import java.sql.SQLException;

public class AddressController extends GeneralController<Address> {

    public AddressController(AddressDao addressDao) {
        super(addressDao);
    }

    public void addAddress(Address address) throws SQLException, ClassNotFoundException {
        super.insert(address);
    }


}
