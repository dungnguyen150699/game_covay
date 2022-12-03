/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User.Control;

import Model.ServerReceiveData;
import Model.ServerSendData;
import Model.User;
import User.View.PlayFRM;
import User.View.SelectPlayerFRM;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class Play_Thread extends Thread{
    public int second=30, stop = 1;
    public PlayFRM p;
 
    public Play_Thread(PlayFRM p){
        this.p = p ;
    }
    public void checkTurn() throws IOException{
        
        try {
            ServerReceiveData srd = new ServerReceiveData();
            srd.setSendto("u_CheckTurn");
            p.contr.senddata_UDP(srd);
            
            ServerSendData ssd = p.contr.receivedata_UDP();
            System.out.println(ssd.getUser().getUserName() + "__playFrM_"+ ssd.getUserRival().getUserName());
            if(ssd.getSendto().equals("sv_CheckTurn")){
                if(ssd.getUser().getStepPlay().isCheckTurn()){
                    p.setPlayer(ssd.getUser().getUserName());
                    p.u.getStepPlay().setCheckTurn(true);
                    p.k = ssd.getUser().getStepPlay().getColor();
                }
                else {
                    p.setPlayer(ssd.getUserRival().getUserName());
                    p.u.getStepPlay().setCheckTurn(false);
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void turn15s(){
        if(stop==1){
            second--;
            if(second == 0 && p.u.getStepPlay().isCheckTurn()){
                try {
                    p.setLable(second/2);
                    ServerReceiveData srd = new ServerReceiveData();
                    srd.setSendto("u_outTime");
                    p.contr.senddata_UDP(srd);
                    
                    p.u.getStepPlay().setCheckTurn(false);
                    second = 30;
                } catch (IOException ex) {
                    Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                if(second == 0){
                    p.setLable(second/2);
                    second = 30;
                }
                else p.setLable(second/2);
            }
        }
    }
    
    public void checkRetreat(){
        try {
            ServerReceiveData srd = new ServerReceiveData();
            srd.setSendto("u_checkRetreat");
            p.contr.senddata_UDP(srd);
            
            ServerSendData ssd = p.contr.receivedata_UDP();
            if(ssd.getSendto().equals("sv_checkRetreat") && ssd.getUserRival().getStepPlay().isRetreat()){
                p.urRetreat();
                stop=4;
            }
        } catch (IOException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void check_Step() throws IOException, ClassNotFoundException{
          ServerReceiveData srd = new ServerReceiveData();
          srd.setSendto("u_checkStep");
          p.contr.senddata_UDP(srd);          
          ServerSendData ssd = p.contr.receivedata_UDP();
//          System.out.println("here" + ssd.getUserRival().getStepPlay().isNews());
          if(ssd.getSendto().equals("sv_checkStep") && ssd.getUserRival().getStepPlay().isNews()){
              second = 30;
              p.update_step(ssd.getUserRival().getStepPlay().getFi(), ssd.getUserRival().getStepPlay().getSe());
          }
    }
    
    public void check_chat() throws IOException{
        try {
            ServerReceiveData srd = new ServerReceiveData();
            srd.setSendto("u_checkChat");
            p.contr.senddata_UDP(srd);
            
            ServerSendData ssd = p.contr.receivedata_UDP();
            if(ssd.getSendto().equals("sv_checkChat") && ssd.getUserRival().getUserChat().isNews()){
                p.rivalChat(ssd.getUserRival().getUserName(), ssd.getUserRival().getUserChat().getContentChat());
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
           
    public void run(){
        try {
            while(true){
                if(stop==1){
                    sleep(500);
                    turn15s();
                    checkTurn();
                    System.out.println("checkTurnok");
                    check_Step();
                    System.out.println("checkStepok");
                    check_chat();
                    System.out.println("checkChatok");
                    checkRetreat();
                    System.out.println("checkRetreatok");
                }
                if(stop==2){
                    int i = p.playAgain();
                    if(i==0){
                        ServerReceiveData srd = new ServerReceiveData();
                        srd.setSendto("u_yesAgain");
                        p.contr.senddata_UDP(srd); 
                        stop=3;
                    }
                    else{
                        ServerReceiveData srd = new ServerReceiveData();
                        srd.setSendto("u_noAgain");
                        p.contr.senddata_UDP(srd);
                        stop=4;                           
                    }
                   
                }
                if(stop==3){
                    ServerReceiveData srd = new ServerReceiveData();
                    srd.setSendto("u_checkAgain");
                    p.contr.senddata_UDP(srd);
                    
                    ServerSendData ssd = p.contr.receivedata_UDP();
                    if(ssd.getUserRival().getPlayagain().equals("yes")){
                        new PlayFRM(p.contr,p.u,p.m).setVisible(true);
                        p.dispose();
                        this.stop();
                    }
                    else{
                        if(ssd.getUserRival().getPlayagain().equals("no")){
                            p.UserRivalReject(ssd.getUserRival().getUserName());
                            stop=4;
                        }
                    }
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
