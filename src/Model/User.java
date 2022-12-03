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
public class User implements Serializable{
    public int id,totalWin,totalLost,totalMatch;
    public int userPort;
    public String userName,password,status,checkInvite,accept,playagain="null";
    public sv_pairx stepPlay;
    public UserChat userChat;
    public User() {
        stepPlay = new sv_pairx();
        userChat = new UserChat();
    }

    public User(int totalWin, int totalLost, int totalMatch, String userName) {
        this.totalWin = totalWin;
        this.totalLost = totalLost;
        this.totalMatch = totalMatch;
        this.userName = userName;
    }
  

    public String getPlayagain() {
        return playagain;
    }

    public void setPlayagain(String playagain) {
        this.playagain = playagain;
    }

    public UserChat getUserChat() {
        return userChat;
    }

    public void setUserChat(UserChat userChat) {
        this.userChat = userChat;
    }
    
    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public sv_pairx getStepPlay() {
        return stepPlay;
    }

    public void setStepPlay(sv_pairx stepPlay) {
        this.stepPlay = stepPlay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalWin() {
        return totalWin;
    }

    public void setTotalWin(int totalWin) {
        this.totalWin = totalWin;
    }

    public int getTotalLost() {
        return totalLost;
    }

    public void setTotalLost(int totalLost) {
        this.totalLost = totalLost;
    }

    public int getTotalMatch() {
        return totalMatch;
    }

    public void setTotalMatch(int totalMatch) {
        this.totalMatch = totalMatch;
    }

    public int getUserPort() {
        return userPort;
    }

    public void setUserPort(int userPort) {
        this.userPort = userPort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckInvite() {
        return checkInvite;
    }

    public void setCheckInvite(String checkInvite) {
        this.checkInvite = checkInvite;
    }

   
}
