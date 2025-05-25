package br.com.fiap.granfinale.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {
    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER = "RM560802";
    private static final String PASSWORD = "300793";

    public static Connection getConnection() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
