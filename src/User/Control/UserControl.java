/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User.Control;

import Model.ServerReceiveData;
import Model.ServerSendData;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class UserControl {

    public Socket userSocket =null;
    public String serverHost = "192.168.1.5";
    public int serverPort;
    public DatagramSocket userSocket_UDP=null;
    public int userPort;
    public int lengthdata = 2048;
    public DatagramPacket receivePacket;

    public UserControl(int severPort) {
        this.serverPort = severPort;
        this.userPort = severPort+101;
    }

    public void open_UDP(){
        try {
            Socket_TCPClose();
            userSocket_UDP = new DatagramSocket(userPort);
        } catch (SocketException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void open_TCP(){
        try {
            Socket_UDPClose();
            userSocket = new Socket(serverHost, serverPort);
        } catch (IOException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ServerSendData ReceiveData() {
        ObjectInputStream ois = null;
        ServerSendData ssd = null;
        try {
            ois = new ObjectInputStream(userSocket.getInputStream());
            ssd = (ServerSendData) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ssd;
    }

    public ServerSendData receivedata_UDP() throws IOException, ClassNotFoundException {
        ServerSendData x = new ServerSendData();
        byte[] receiveData = new byte[lengthdata];
        receivePacket = new DatagramPacket(receiveData, lengthdata);
        userSocket_UDP.receive(receivePacket);
        ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
        ObjectInputStream ois = new ObjectInputStream(bais);
        x = (ServerSendData) ois.readObject();
        return x;
    }

    public void SendData(ServerReceiveData srd) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(userSocket.getOutputStream());
            oos.flush();
            try {
                oos.writeObject(srd);
            } catch (IOException ex) {
                Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void senddata_UDP(ServerReceiveData srd) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(srd);
        oos.flush();
        InetAddress IPAddress = InetAddress.getByName(serverHost);
        byte[] sendData = baos.toByteArray();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);
        userSocket_UDP.send(sendPacket);
    }

    public void Socket_TCPClose() {
        try {
            if(userSocket!=null)
             userSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Socket_UDPClose(){
        if(userSocket_UDP!=null)
        userSocket_UDP.close();
    }
    
}
