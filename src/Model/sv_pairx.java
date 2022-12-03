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
public class sv_pairx implements Serializable{
    public int fi = 0,se = 0,Color = 1;
    public boolean checkTurn = false;
    public String checkResult = null;
    public boolean news = false , retreat=false;
    public int count;

    public boolean isRetreat() {
        return retreat;
    }

    public void setRetreat(boolean retreat) {
        this.retreat = retreat;
    }
    

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public int getFi() {
        return fi;
    }

    public void setFi(int fi) {
        this.fi = fi;
    }

    public int getSe() {
        return se;
    }

    public void setSe(int se) {
        this.se = se;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int Color) {
        this.Color = Color;
    }

    public boolean isCheckTurn() {
        return checkTurn;
    }

    public void setCheckTurn(boolean checkTurn) {
        this.checkTurn = checkTurn;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public boolean isNews() {
        return news;
    }

    public void setNews(boolean news) {
        this.news = news;
    }

    
    
}
