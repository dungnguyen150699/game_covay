/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.View;

import Model.ServerReceiveData;
import Model.ServerSendData;
import Model.User;
import Server.Control.UserDAO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ServerControl {
    UserDAO ud = new UserDAO();
    ServerSocket serverSocket ;
    Socket userSocket;
    int serverPort;
    boolean port[] = new boolean[100];
    ArrayList <ServerThread> x = new ArrayList<>();
    public ServerControl(int serverPort) throws IOException, ClassNotFoundException, SQLException{
        this.serverPort = serverPort;
        for(int i=1 ; i<100 ; i++) port[i]=false;
        serverSocket = new ServerSocket(serverPort);
        while(true){
            userSocket = serverSocket.accept();
            ServerReceiveData srd = new ServerReceiveData();
            srd = ReceiveData();
            if(srd.getSendto().equals("u_checkLogin")){
                User u = new User();
                u = srd.getUser();
                if(ud.checkLogin(u)){
                    int newPort = findNewPort();
                    u = ud.getUser(u);
                    //u
                    
                    u.setStatus("waitting");
                    u.setCheckInvite("null");
                    u.setAccept("null");
                    u.setUserPort(newPort);
                    ServerThread k = new ServerThread(this,newPort,u);
                    k.start();
                    x.add(k);
                    
                    //ssd
                    ServerSendData ssd = new ServerSendData();
                    ssd.setUser(u); ssd.setCheckLogin(true); ssd.setSendto("sv_checkLogin");
                    sendData(ssd);
                }
                else{
                    ServerSendData ssd = new ServerSendData();
                    ssd.setCheckLogin(false); ssd.setSendto("sv_checkLogin");
                    sendData(ssd);
                }
            }
            if(srd.getSendto().equals("u_register")){
                if(ud.Register(srd.getUser())){
                    ServerSendData ssd = new ServerSendData();
                    ssd.setSendto("sv_RegistSuccess");
                    sendData(ssd);
                }
                else{
                    ServerSendData ssd = new ServerSendData();
                    ssd.setSendto("sv_RegistFail");
                    sendData(ssd);
                }
            }
        }
    }
    
    public ServerReceiveData ReceiveData(){
      ServerReceiveData srd = new ServerReceiveData();
        try {
            ObjectInputStream ois = new ObjectInputStream(userSocket.getInputStream());
            srd = (ServerReceiveData) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return srd;
    }
    
    public void sendData(ServerSendData ssd){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(userSocket.getOutputStream());
            oos.writeObject(ssd);
        } catch (IOException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ServerThread find_other_player(String userName){
        ServerThread a = null;
        for(int i=0 ; i<x.size() ; i++){
            User u = x.get(i).getU();
            if(u.getUserName().equals(userName) && u.getStatus().equals("waitting")){
                a=x.get(i);
                break;
            }
        }
        return a;
    }
    
    public ArrayList<User> SearchPlayer(){
        ArrayList <User> a = new ArrayList();
        for(int i=0 ; i<x.size() ;i++){
            ServerThread st = x.get(i);
            a.add(st.getU());
        }
        return a;
    }
    
    public void clearPort(int i){
        port[i] = false;
    }
    
    public int findNewPort() throws IOException{
        int newPort=9000;
        for(int i=1 ; i<100 ;i++){
            if(port[i]==false){
                newPort += i;
                port[i]=true;
                break;
            }
        }
        return newPort;
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            new ServerControl(9000);
        } catch (IOException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
