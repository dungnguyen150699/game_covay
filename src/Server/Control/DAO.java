/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class DAO {
    Connection con;
    public DAO() throws ClassNotFoundException, SQLException{
        String user ="root", password="", url="jdbc:mysql://localhost:3306/btl_ltm"+"?useSSL=false";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, user, password);
    }
}
