package com.company.logicModel;

import com.company.daoModel.RiderDao;
import com.company.domainModel.Rider;

import java.sql.SQLException;

public class RiderController extends GeneralController<Rider> {

    //RiderDao riderDao;
    public RiderController(RiderDao riderDao) {

        super(riderDao);

    }

    public void insertRider(Rider rider) throws SQLException, ClassNotFoundException {
        super.insert(rider);
    }
}
