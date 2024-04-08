package com.company.daoModel;

import com.company.domainModel.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.sql.*;
import java.io.*;
import java.sql.*;

/**
 * Database class that manages a database connection.
 * Implements the Singleton pattern in order to have a single instance of the Database.
 */
public class Database {

    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String connection = "jdbc:mysql://127.0.0.1:3306/ingegneriasoftware";
    private static String user = "root";
    private static String password = "root";

    /**
     * Private constructor to avoid instantiation, since this class is used only to connect to the database
     * We follow the Singleton pattern
     */
    private Database() {
    }


    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        return DriverManager.getConnection(connection, user, password);
    }

    /**
     * This method closes the given connection
     *
     * @param connection is the connection to close
     */
    public static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }


}