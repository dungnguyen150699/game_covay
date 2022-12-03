/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class DetailMatch implements Serializable{
    public int id;
    public User u;
    public String matchResult;
    public int idMatch;

    public DetailMatch() {
    }

    public DetailMatch(int id, User u, String matchResult, int idMatch) {
        this.id = id;
        this.u = u;
        this.matchResult = matchResult;
        this.idMatch = idMatch;
    }

    public int getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(int idMatch) {
        this.idMatch = idMatch;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }
    
}
