/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Match implements Serializable {

    public int id;
    public Date dateMatch;
    public ArrayList <DetailMatch> dm;

    public Match() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateMatch() {
        return dateMatch;
    }

    public void setDateMatch(Date dateMatch) {
        this.dateMatch = dateMatch;
    }

    public ArrayList<DetailMatch> getDm() {
        return dm;
    }

    public void setDm(ArrayList<DetailMatch> dm) {
        this.dm = dm;
    }


}