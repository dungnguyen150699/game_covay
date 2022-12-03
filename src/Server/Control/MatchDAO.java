/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Control;

import Model.DetailMatch;
import Model.Match;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class MatchDAO extends DAO{
    public MatchDAO() throws ClassNotFoundException, SQLException{
        super();
    }
    
    public int countRowMatch() throws SQLException{
        String sql = "Select * From btl_ltm.match ";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.next()){
            rs.last();
            int countrow = rs.getInt("id");
            return countrow;
        }
        else {
            return 0;
        }
    }
    
    public boolean insertMatch(Match m){
        boolean result = true;
        String sqlMatch = "INSERT INTO btl_ltm.match (dateMatch) value(?)";
        String sqlDetailMatch = "INSERT INTO btl_ltm.detailmatch (matchresult,idmatch,iduser) value(?,?,?)";
        String sqlUser = "UPDATE btl_ltm.user SET totalMatch=? , totalWin=? , totalLost=? WHERE id=?";
        
        try {
            con.setAutoCommit(false);
            PreparedStatement stmt = con.prepareStatement(sqlMatch);
            stmt.setDate(1,m.dateMatch);
            stmt.executeUpdate(); 
            
            for(DetailMatch i : m.getDm()){
                stmt = con.prepareStatement(sqlDetailMatch);  
                stmt.setString(1, i.getMatchResult());
                stmt.setInt(2, countRowMatch());
                stmt.setInt(3, i.getU().getId());
                stmt.executeUpdate(); 
                
                stmt = con.prepareStatement(sqlUser);
                stmt.setInt(1, i.getU().getTotalMatch());
                stmt.setInt(2, i.getU().getTotalWin());
                stmt.setInt(3, i.getU().getTotalLost());
                stmt.setInt(4, i.getU().id);
                stmt.executeUpdate();
            }
            con.commit();
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(MatchDAO.class.getName()).log(Level.SEVERE, null, ex1);
                return false;
            }
            result = false;
            Logger.getLogger(MatchDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(MatchDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return result;
    }
}
