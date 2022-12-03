/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.View;

import Model.DetailMatch;
import Model.Match;
import Model.ServerReceiveData;
import Model.ServerSendData;
import Model.User;
import Server.Control.MatchDAO;
import Server.Control.UserDAO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
public class ServerThread extends Thread implements Serializable {

    ServerControl sc;
    int serverPort, next = 1, serverAccept = 1, lengthdata = 2048;
    DatagramPacket receivePacket;
    DatagramSocket serverSocket = null;

    User u;
    ServerSocket Server = null;
    Socket userSocket;
    ServerThread serverRival = null;

    MatchDAO md;
    UserDAO ud;

    public ServerThread() {
    }

    public ServerThread(ServerControl sc, int serverPort, User u) {
        try {
            this.sc = sc;
            this.serverPort = serverPort;
            this.u = u;
            md = new MatchDAO();
            ud = new UserDAO();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ServerReceiveData ReceiveData() {
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

    public ServerReceiveData receivedata_UDP() throws IOException, ClassNotFoundException {
        ServerReceiveData srd = new ServerReceiveData();
        byte[] receiveData = new byte[lengthdata];
        receivePacket = new DatagramPacket(receiveData, lengthdata);
        serverSocket.receive(receivePacket);
        ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
        ObjectInputStream ois = new ObjectInputStream(bais);
        srd = (ServerReceiveData) ois.readObject();
        return srd;
    }

    public void sendData(ServerSendData ssd) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(userSocket.getOutputStream());
            oos.writeObject(ssd);
        } catch (IOException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void senddata_UDP(ServerSendData ssd) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.flush();
        oos.writeObject(ssd);
        InetAddress IPAddress = receivePacket.getAddress();
        int clientPort = receivePacket.getPort();
        byte[] sendData = baos.toByteArray();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, clientPort);
        serverSocket.send(sendPacket);
    }

    public void run() {
        System.out.println(this.getU().getUserName());
        try {
            while (true) {
                if (next == 1) {
                    if (serverAccept == 1) {
                        if(serverSocket!=null){
                            serverSocket.close();
                        }
                        Server = new ServerSocket(serverPort);
                        userSocket = Server.accept();
                        serverAccept = 2;
                    }
                    ServerReceiveData srd = new ServerReceiveData();
                    srd = ReceiveData();

                    if (srd.getSendto().equals("u_close")) {
                        int port = srd.getUser().getUserPort() - 9000;
                        System.out.println(port);
                        Server.close();
                        sc.x.remove(this);
                        sc.clearPort(port);
                        this.stop();
                    }

                    FOR_SelectPlayerFRM(srd);
                    For_Statist(srd);
                } else {
                    if (serverAccept == 2) {
                        ServerReceiveData srd = new ServerReceiveData();
                        srd = ReceiveData();
                        if(srd.getSendto().equals("u_getnewPort")){
                            sc.clearPort(u.getUserPort()-9000);
                            serverPort = sc.findNewPort();
                            ServerSendData ssd = new ServerSendData();
                            ssd.setSendto("sv_getnewPort");
                            this.u.setUserPort(serverPort); 
                            ssd.setUser(u);
                            sendData(ssd);
                        }
                        Server.close();
                        serverSocket = new DatagramSocket(serverPort);
                        serverAccept =3;
                    }
                    ServerReceiveData srd = new ServerReceiveData();
                    try {
                        srd = receivedata_UDP();
                        
                        if(srd.getSendto().equals("u_getnewPort")){
                            serverPort = sc.findNewPort();
                            ServerSendData ssd = new ServerSendData();
                            ssd.setSendto("sv_getnewPort");
                            this.u.setUserPort(serverPort); 
                            this.u.setStatus("waitting");
                            serverRival.getU().setStatus("waitting");
                            ssd.setUser(u);
                            senddata_UDP(ssd);
                            
                            next=1;
                            serverAccept=1;
                        }
                        if (srd.getSendto().equals("u_close")) {
                            int port = srd.getUser().getUserPort() - 9000;
                            Server.close();
                            sc.x.remove(this);
                            sc.clearPort(port);
                            this.stop();
                        }
                        FOR_PlayFRM(srd);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void FOR_SelectPlayerFRM(ServerReceiveData srd) {

        if (srd.getSendto().equals("u_searchPlayer")) {
            ArrayList<User> ar = new ArrayList<>();
            ar = sc.SearchPlayer();
            ServerSendData ssd = new ServerSendData();
            ssd.setSendto("sv_searchPlayer");
            ssd.setAr(ar);
            sendData(ssd);
        }

        if (srd.getSendto().equals("u_checkInvite")) {
            ServerSendData ssd = new ServerSendData();
            ssd.setSendto("sv_checkInvite");
            ssd.setUser(u);
            if (serverRival != null) {
                ssd.setUserRival(serverRival.getU());
            }
            sendData(ssd);
        }

        if (srd.getSendto().equals("u_ok")) {
            if (this.getU().getStatus().equals("waitting") && serverRival.getU().getStatus().equals("waitting")) {
                serverRival.serverRival = this;
                serverRival.getU().setStatus("playing");
                this.getU().setStatus("playing");
                serverRival.getU().setAccept("yes");

                System.out.println(this.getU().getUserName() + "---" + serverRival.getU().getUserName());
                ServerSendData ssd = new ServerSendData();
                ssd.setSendto("sv_ok");
                ssd.setUser(u);
                ssd.setUserRival(serverRival.getU());
                sendData(ssd);
                this.getU().setCheckInvite("null");
                next = 2;
            } else {
                System.out.println("played");
                ServerSendData ssd = new ServerSendData();
                ssd.setSendto("sv_played");
                ssd.setUser(u);
                ssd.setUserRival(serverRival.getU());
                sendData(ssd);
                this.getU().setCheckInvite("null");
            }
        }

        if (srd.getSendto().equals("u_notok")) {
            System.out.println("u_notok");
            User u = srd.getUserRival();
            this.getU().setCheckInvite("null");
            serverRival = sc.find_other_player(u.getUserName());
            System.out.println("serverRival_------" + serverRival.getU().getUserName());
            serverRival.getU().setAccept("no");
        }

        if (srd.getSendto().equals("u_checkAccept")) {
            if (this.getU().getAccept().equals("yes")) {
                ServerSendData ssd = new ServerSendData();
                ssd.setSendto("sv_ok");
                ssd.setUser(this.getU());
                sendData(ssd);
                serverRival.serverRival = this;
                this.getU().setAccept("null");

                this.getU().getStepPlay().setColor(1);
                this.getU().getStepPlay().setCheckTurn(false);
                this.getU().setPlayagain("null");

                serverRival.getU().getStepPlay().setColor(2);
                serverRival.getU().getStepPlay().setCheckTurn(true);
                serverRival.getU().setPlayagain("null");

                next = 2;
                System.out.println("u_checkAccept_yes");
            } else {
                if (this.getU().getAccept().equals("no")) {
                    ServerSendData ssd = new ServerSendData();
                    ssd.setSendto("sv_no");
                    ssd.setUser(u);
                    ssd.setUserRival(serverRival.getU());
                    sendData(ssd);
                    this.getU().setAccept("null");
                    System.out.println("u_checkAccept_no");
                } else {
                    ServerSendData ssd = new ServerSendData();
                    ssd.setSendto("sv_nullInvite");
                    ssd.setUser(u);
                    sendData(ssd);
                }
            }
        }

        if (srd.getSendto().equals("u_selectPlayer")) {
            this.u.getStepPlay().setCount(srd.getUser().getStepPlay().getCount());
            User u = srd.getUserRival();
            serverRival = sc.find_other_player(u.getUserName());
            serverRival.serverRival = this;
            serverRival.getU().setCheckInvite("yes");
            System.out.println("This_u_______" + this.getU().getUserName() + "___" + serverRival.getU().getUserName());
            System.out.println("This_Rival______" + serverRival.getU().getUserName() + "___" + serverRival.serverRival.getU().getUserName());
        }
    }

    public void FOR_PlayFRM(ServerReceiveData srd) {
        if (srd.getSendto().equals("u_Back")) {
            this.getU().setStatus("waitting");
            serverRival.getU().setStatus("waitting");
            this.getU().getStepPlay().setRetreat(true);

            this.getU().setTotalMatch(this.getU().getTotalMatch() + 1);
            this.getU().getStepPlay().setCheckResult("thua");
            this.getU().setTotalLost(this.getU().getTotalLost() + 1);

            serverRival.getU().setTotalMatch(serverRival.getU().getTotalMatch() + 1);
            serverRival.getU().getStepPlay().setCheckResult("thua");
            serverRival.getU().setTotalWin(serverRival.getU().getTotalWin() + 1);

            ArrayList<DetailMatch> ar = new ArrayList();//create array
            DetailMatch dm1 = new DetailMatch();
            dm1.setMatchResult("thua");
            dm1.setU(u);

            DetailMatch dm2 = new DetailMatch();
            dm2.setMatchResult("thắng");
            dm2.setU(serverRival.getU());

            ar.add(dm1);
            ar.add(dm2);

            Match m = new Match();
            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            m.setDateMatch(date);
            m.setDm(ar);
            md.insertMatch(m);
        }
        if (srd.getSendto().equals("u_checkRetreat")) {
            try {
                ServerSendData ssd = new ServerSendData();
                ssd.setSendto("sv_checkRetreat");
                ssd.setUserRival(serverRival.getU());
                senddata_UDP(ssd);
                serverRival.getU().getStepPlay().setRetreat(false);
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (srd.getSendto().equals("u_outTime")) {
            this.getU().getStepPlay().setCheckTurn(false);
            this.getU().getStepPlay().setNews(false);
            serverRival.getU().getStepPlay().setCheckTurn(true);
        }
        if (srd.getSendto().equals("u_CheckTurn")) {
            try {
                ServerSendData ssd = new ServerSendData();
                ssd.setSendto("sv_CheckTurn");
                ssd.setUser(this.getU());
                ssd.setUserRival(serverRival.getU());
                senddata_UDP(ssd);
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (srd.getSendto().equals("u_setimg") && this.getU().getStepPlay().isCheckTurn()) {
            System.out.println("u_setimg");
            this.getU().getStepPlay().setFi(
                    srd.getUser().getStepPlay().getFi());
            this.getU().getStepPlay().setSe(srd.getUser().getStepPlay().getSe());
            this.getU().getStepPlay().setCheckTurn(false);
            this.getU().getStepPlay().setNews(true);
            System.out.println(this.getU().getUserName() + "--------" + serverRival.getU().getUserName());
            serverRival.getU().getStepPlay().setCheckTurn(true);
        }
        if (srd.getSendto().equals("u_checkStep")) {
            try {
                ServerSendData ssd = new ServerSendData();
                ssd.setSendto("sv_checkStep");
                ssd.setUserRival(serverRival.getU());
                senddata_UDP(ssd);
                serverRival.getU().getStepPlay().setNews(false);
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (srd.getSendto().equals("u_checkChat")) {
            try {
                ServerSendData ssd = new ServerSendData();
                ssd.setSendto("sv_checkChat");
                ssd.setUserRival(serverRival.getU());
                senddata_UDP(ssd);
                serverRival.getU().getUserChat().setNews(false);
                serverRival.getU().getUserChat().setSended(true);
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (srd.getSendto().equals("u_checkSend")) {
            try {
                ServerSendData ssd = new ServerSendData();
                ssd.setSendto("sv_checkSend");
                ssd.setUser(this.getU());
                senddata_UDP(ssd);
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (srd.getSendto().equals("u_sendchat")) {
            this.getU().getUserChat().setContentChat(srd.getUser().getUserChat().getContentChat());
            this.getU().getUserChat().setSended(false);
            this.getU().getUserChat().setNews(true);
        }
        if (srd.getSendto().equals("u_Result")) {
            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);

            this.getU().getStepPlay().setCheckResult(srd.getUser().getStepPlay().getCheckResult());
            serverRival.getU().getStepPlay().setCheckResult(srd.getUser().getStepPlay().getCheckResult());

            this.getU().setTotalMatch(this.getU().getTotalMatch() + 1);
            serverRival.getU().setTotalMatch(serverRival.getU().getTotalMatch() + 1);

            if (srd.getUser().getStepPlay().getCheckResult().equals("thắng")) {
                this.getU().setTotalWin(this.getU().getTotalWin() + 1);//add totalmatch
                serverRival.getU().setTotalLost(serverRival.getU().getTotalLost() + 1);
            } else {
                if (srd.getUser().getStepPlay().getCheckResult().equals("thua")) {
                    this.getU().setTotalLost(this.getU().getTotalLost() + 1);
                    serverRival.getU().setTotalWin(serverRival.getU().getTotalWin() + 1);
                }
            }

            ArrayList<DetailMatch> ar = new ArrayList();//create array
            DetailMatch dm1 = new DetailMatch();
            dm1.setMatchResult(srd.getUser().getStepPlay().checkResult);
            dm1.setU(u);

            DetailMatch dm2 = new DetailMatch();
            dm2.setMatchResult(srd.getUserRival().getStepPlay().checkResult);
            dm2.setU(serverRival.getU());

            ar.add(dm1);
            ar.add(dm2);

            Match m = new Match();
            m.setDateMatch(date);
            m.setDm(ar);
            md.insertMatch(m);
        }
        if (srd.getSendto().equals("u_yesAgain")) {
            this.getU().setPlayagain("yes");
        }
        if (srd.getSendto().equals("u_noAgain")) {
            this.getU().setPlayagain("no");
        }
        if (srd.getSendto().equals("u_checkAgain")) {
            try {
                ServerSendData ssd = new ServerSendData();
                ssd.setSendto("sv_checkAgain");
                ssd.setUserRival(serverRival.getU());
                senddata_UDP(ssd);
                serverRival.getU().setPlayagain("null");
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void For_Statist(ServerReceiveData srd) {
        if (srd.getSendto().equals("Tổng số điểm")) {
            try {
                ServerSendData ssd = new ServerSendData();
                ssd.setAr(ud.Statist1());

                sendData(ssd);
                System.out.println("Server.View.ServerThread.For_Statist()");
            } catch (SQLException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (srd.getSendto().equals("Trung bình điểm đối thủ đã gặp")) {
            try {
                ServerSendData ssd = new ServerSendData();
                ssd.setAr(ud.Statist2(this.getU().getId()));
                sendData(ssd);
            } catch (SQLException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (srd.getSendto().equals("u_History")) {
            try {
                ServerSendData ssd = new ServerSendData();
                ssd.setAr(ud.History(srd.getUser()));
                sendData(ssd);
            } catch (SQLException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (srd.getSendto().equals("u_otherHistory")){
            try {
                User otherUser = ud.getUser(srd.getUserRival());
                ServerSendData ssd = new ServerSendData();
                ssd.setUserRival(otherUser);
                sendData(ssd);
            } catch (SQLException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ServerControl getSc() {
        return sc;
    }

    public void setSc(ServerControl sc) {
        this.sc = sc;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

}
