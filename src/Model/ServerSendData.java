/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ServerSendData implements Serializable{
    public User user,userRival;
    public String Sendto;
    public ArrayList ar;
    public boolean checkLogin;

    public ServerSendData() {
    }

    public ServerSendData(User user, User userRival, String Sendto, ArrayList ar, boolean checkLogin) {
        this.user = user;
        this.userRival = userRival;
        this.Sendto = Sendto;
        this.ar = ar;
        this.checkLogin = checkLogin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserRival() {
        return userRival;
    }

    public void setUserRival(User userRival) {
        this.userRival = userRival;
    }

    public String getSendto() {
        return Sendto;
    }

    public void setSendto(String Sendto) {
        this.Sendto = Sendto;
    }

    public ArrayList getAr() {
        return ar;
    }

    public void setAr(ArrayList ar) {
        this.ar = ar;
    }

    public boolean isCheckLogin() {
        return checkLogin;
    }

    public void setCheckLogin(boolean checkLogin) {
        this.checkLogin = checkLogin;
    }
    
}
