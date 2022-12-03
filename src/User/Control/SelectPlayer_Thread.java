package User.Control;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Model.ServerReceiveData;
import Model.ServerSendData;
import Model.User;
import User.View.SelectPlayerFRM;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class SelectPlayer_Thread extends Thread {
    public SelectPlayerFRM sp;
    
    public SelectPlayer_Thread(SelectPlayerFRM sp){
        this.sp = sp;
    }
    
    public void updateSearchPlayer() throws InterruptedException{
        ServerReceiveData srd = new ServerReceiveData();
        srd.setSendto("u_searchPlayer"); srd.setUser(sp.u);
        sp.contr.SendData(srd);
        ServerSendData ssd = null;
        ssd = sp.contr.ReceiveData();
        
        System.out.println("searchPlayer_______"+ ssd.getSendto());
        
        if(ssd.getSendto().equals("sv_searchPlayer"))
        sp.updatetable(ssd.getAr());
    }
    
    public void listening1() throws InterruptedException{
        //send u_checkinvite
        ServerReceiveData srd = new ServerReceiveData();
        srd.setSendto("u_checkInvite");
        sp.contr.SendData(srd);
        
        //receiveData u_checkinvite
        ServerSendData ssd = new ServerSendData();
        ssd = sp.contr.ReceiveData();
        
        System.out.println("checkinvite_____"+ssd.getSendto()); 
        
        
        User u = new User();
        u = (User) ssd.getUserRival();
        if(ssd.getSendto().equals("sv_checkInvite") && ssd.getUser().getCheckInvite().equals("yes")){
            int count = ssd.getUserRival().getStepPlay().getCount();
            if(sp.showMassenge(u.getUserName(),count)==0){
                ServerReceiveData srd1 = new ServerReceiveData();
                srd.setSendto("u_ok"); srd.setUser(sp.u); srd.setUserRival(u);
                sp.contr.SendData(srd);
                
                ServerSendData ssd1 = new ServerSendData();
                
                ssd1 = sp.contr.ReceiveData();
                System.out.println(ssd1.getSendto()+"___"+ssd1.getUser().getUserName()+" vs " + ssd1.getUserRival().getUserName());
                if(ssd1.getSendto().equals("sv_played")) sp.You_Played();
                else{
                    sp.PlayFRM(sp.contr, sp.u,count);
                }
            }
            else{
                ServerReceiveData srd1 = new ServerReceiveData();
                srd1.setSendto("u_notok"); srd1.setUser(sp.u); srd1.setUserRival(u);
                sp.contr.SendData(srd1);
            }
        }
    }
    public void listening2() throws InterruptedException{
        //send Check_Accept
        ServerReceiveData srd = new ServerReceiveData();

        srd.setSendto("u_checkAccept");  srd.setUser(sp.u);
        sp.contr.SendData(srd);
        
        //receive Check_Accept
        ServerSendData ssd1 = new ServerSendData();
        ssd1 = sp.contr.ReceiveData();
        
        System.out.println("checkAccept_____"+ssd1.getSendto());
        
        
        if(ssd1.getSendto().equals("sv_ok")) {
            sp.PlayFRM(sp.contr, sp.u, sp.count);
        }
        if(ssd1.getSendto().equals("sv_no")) {
            User u = (User) ssd1.getUserRival();
            sp.Massenge_reject(u.getUserName());
        } 
      }
    
    
    public void run(){
        while(true){
            try {
                sleep(1000);
                updateSearchPlayer();
                listening1();
                listening2();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(SelectPlayer_Thread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
