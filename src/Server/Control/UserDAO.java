/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Control;

import Model.DetailMatch;
import Model.Match;
import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class UserDAO extends DAO{
    
    public UserDAO() throws ClassNotFoundException, SQLException{
        super();
    }

    public boolean checkLogin(User u) throws ClassNotFoundException, SQLException{
        String checklogin = " Select * from btl_ltm.user where userName = ? and password = ?";
        PreparedStatement p = con.prepareStatement(checklogin);
        p.setString(1, u.getUserName());
        p.setString(2, u.getPassword());
        ResultSet rs = p.executeQuery();
        if(rs.next()) {
            p.close();rs.close();return true;
        }
        else {
            p.close();rs.close();return false;
        }
    }
    public User getUser(User x) throws ClassNotFoundException, SQLException{
        
        String checklogin = " Select * from btl_ltm.user where userName = ? and password = ?";
        PreparedStatement p = con.prepareStatement(checklogin);
        p.setString(1, x.getUserName());
        p.setString(2, x.getPassword());
        ResultSet rs = p.executeQuery();
        
        User u = new User();
        if(rs.next()){
            u.setId(rs.getInt("id"));
            u.setPassword(rs.getString("password"));
            u.setUserName(rs.getString("userName"));
            u.setTotalLost(rs.getInt("totalLost"));
            u.setTotalMatch(rs.getInt("totalMatch"));
            u.setTotalWin(rs.getInt("totalWin")); 
        }
        return u;
    }
    public ArrayList Statist1() throws SQLException{
        ArrayList <User> ar = new ArrayList();
        String checklogin = " Select * from btl_ltm.user";
        PreparedStatement p = con.prepareStatement(checklogin);
        ResultSet rs = p.executeQuery();
        while(rs.next()){
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setPassword(rs.getString("password"));
            u.setUserName(rs.getString("userName"));
            u.setTotalLost(rs.getInt("totalLost"));
            u.setTotalMatch(rs.getInt("totalMatch"));
            u.setTotalWin(rs.getInt("totalWin")); 
            ar.add(u);
        }
        return ar;
    }
    
    public ArrayList Removedupli(ArrayList <Integer>ar){
        ArrayList<Integer> x = new ArrayList();
        x.add(ar.get(0));
        int k=1;
        for(int i=0 ; i<ar.size() ; i++){
            for(int j=0 ; j<x.size() ; j++){
                if(ar.get(i)==x.get(j)) k=2;
            }
            if(k==1) x.add(ar.get(i));
            else k =1;
        }
        return x;
    }
    
    public ArrayList Statist2(int id) throws SQLException{
        ArrayList <DetailMatch> ar = new ArrayList();
        ArrayList <Integer> ar1 = new ArrayList();
        String checklogin = " Select idmatch from btl_ltm.detailmatch where iduser = ? ";
        PreparedStatement p = con.prepareStatement(checklogin);
        p.setInt(1, id);
        ResultSet rs = p.executeQuery();
        while(rs.next()){
            DetailMatch dm = new DetailMatch();
            dm.setIdMatch(rs.getInt("idmatch"));
            ar.add(dm);
        }
        for(int i=0 ; i<ar.size() ; i++){
            String sqlmatch = " Select iduser from btl_ltm.detailmatch where idmatch = ?";
            PreparedStatement p1 = con.prepareStatement(sqlmatch);
            System.out.println("this__"+ ar.get(i).getIdMatch());
            p1.setInt(1, ar.get(i).getIdMatch());
            ResultSet rs1 = p1.executeQuery();
            while (rs1.next()) {
                if(id != rs1.getInt("iduser")){
                    ar1.add(rs1.getInt("iduser"));
                }
            }
        }
        
        ar1 = Removedupli(ar1);
        ArrayList <User> ar2 = new ArrayList<>();
        for(int i=0 ; i<ar1.size() ; i++){
            String sqlmatch = " Select * from btl_ltm.user where id = ?";
            PreparedStatement p2 = con.prepareStatement(sqlmatch);
            p2.setInt(1, ar1.get(i));
            ResultSet rs2 = p2.executeQuery();
            while (rs2.next()) {
                User u = new User();
                u.setId(rs2.getInt("id"));
                u.setPassword(rs2.getString("password"));
                u.setUserName(rs2.getString("userName"));
                u.setTotalLost(rs2.getInt("totalLost"));
                u.setTotalMatch(rs2.getInt("totalMatch"));
                u.setTotalWin(rs2.getInt("totalWin")); 
                ar2.add(u);
            }
        }
        return ar2;
    }
    
    public ArrayList<Match> History(User u) throws SQLException{
        ArrayList <Match> armatch = new ArrayList();
        ArrayList <DetailMatch> ar = new ArrayList();
        ArrayList <DetailMatch> ar1 = new ArrayList();
        
        
        String sql0 = " Select id from btl_ltm.user where userName = ? ";
        PreparedStatement p = con.prepareStatement(sql0);
        p.setString(1, u.getUserName());
        ResultSet rs = p.executeQuery();
        if(rs.next()){
            u.setId(rs.getInt("id"));
        }
        
        String sql1 = " Select idmatch from btl_ltm.detailmatch where iduser = ? ORDER BY id DESC Limit 15 ";
        p = con.prepareStatement(sql1);
        p.setInt(1, u.getId());
        rs = p.executeQuery();
        while(rs.next()){
            DetailMatch dm = new DetailMatch();
            dm.setIdMatch(rs.getInt("idmatch"));
            ar.add(dm);
        }
        
        for(int i=0 ; i<ar.size() ; i++){
            String sql2 = " Select * from btl_ltm.detailmatch where idmatch = ?";
            p = con.prepareCall(sql2);
            p.setInt(1, ar.get(i).getIdMatch());
            rs = p.executeQuery();
            while (rs.next()) {
                if(rs.getInt("iduser") != u.getId()){
                    DetailMatch dm = new DetailMatch();
                    dm.setIdMatch(rs.getInt("idmatch"));
                    dm.setId(rs.getInt("id"));
                    dm.setMatchResult(rs.getString("matchresult"));
                    User ur = new User();
                    ur.setId(rs.getInt("iduser"));
                    dm.setU(ur);
                    ar1.add(dm);
                }
            }
        }
        for(int i =0 ; i<ar1.size() ; i++){
            String sql3 = "Select userName from btl_ltm.user where id = ?";
            p = con.prepareCall(sql3);
            p.setInt(1, ar1.get(i).getU().getId());
            rs = p.executeQuery();
            while(rs.next()){
                ar1.get(i).getU().setUserName(rs.getString("username"));
            }
        }
        for(int i = 0; i<ar1.size() ;i++){
            String sql4 = "Select dateMatch from btl_ltm.match where id = ?";
            p = con.prepareCall(sql4);
            p.setInt(1, ar1.get(i).getIdMatch());
            rs = p.executeQuery();
            while(rs.next()){
                Match m = new Match();
                ArrayList <DetailMatch> dm = new ArrayList<>();
                dm.add(ar1.get(i));
                
                m.setDm(dm);
                m.setDateMatch(rs.getDate("dateMatch"));
                armatch.add(m);
            }
        }
        return armatch;
    }
    
    public Boolean Register(User u){
        try {
            boolean result = true;
            String sql = "INSERT INTO btl_ltm.user (userName,password,totalMatch,totalWin,totalLost) values(?,?,?,?,?)";
            PreparedStatement p2 = con.prepareStatement(sql);
            p2.setString(1, u.getUserName());
            p2.setString(2, u.getPassword());
            p2.setInt(3,0);
            p2.setInt(4,0);
            p2.setInt(5,0);
            p2.execute();
        } catch (SQLException ex) {  
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
