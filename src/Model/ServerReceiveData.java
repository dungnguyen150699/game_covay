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
public class ServerReceiveData implements Serializable{
    public String Sendto;
    public User user,userRival;
//    public ArrayList ar;

    public ServerReceiveData() {
    }

    public ServerReceiveData(String Sendto, User user, User userRival) {
        this.Sendto = Sendto;
        this.user = user;
        this.userRival = userRival;
//        this.ar = ar; ----> thừa 
    }
    
    public String getSendto() {
        return Sendto;
    }

    public void setSendto(String Sendto) {
        this.Sendto = Sendto;
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

//    public ArrayList getAr() {
//        return ar;
//    }
//
//    public void setAr(ArrayList ar) {
//        this.ar = ar;
//    }

    
}
