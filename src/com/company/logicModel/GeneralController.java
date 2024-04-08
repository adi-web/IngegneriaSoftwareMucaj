package com.company.logicModel;


import com.company.daoModel.DaoGeneral;

import java.sql.SQLException;

public abstract class GeneralController<T> {

    public final DaoGeneral<T> dao;

    public GeneralController(DaoGeneral<T> dao) {
        this.dao = dao;
    }

    public  void insert(T newInsert) throws SQLException, ClassNotFoundException {
        this.dao.insert(newInsert);
    };
    public  void delete(int id) throws SQLException, ClassNotFoundException {

        this.dao.delete(id);
    };
    public T get(int id) throws SQLException, ClassNotFoundException {

        return this.dao.get(id);
    };



}
