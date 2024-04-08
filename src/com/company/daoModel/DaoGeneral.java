package com.company.daoModel;

import java.sql.SQLException;

public interface DaoGeneral<T> {

    public void insert(T newInsert) throws ClassNotFoundException, SQLException;//inserimento cliente o rider
    public void delete(int id) throws SQLException, ClassNotFoundException;
    public T get(int id) throws SQLException, ClassNotFoundException;
}
