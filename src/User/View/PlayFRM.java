/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User.View;

import Model.ServerReceiveData;
import Model.ServerSendData;
import Model.User;
import User.Control.Play_Thread;
import User.Control.UserControl;
import User.Control.pairx;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class PlayFRM extends javax.swing.JFrame implements ActionListener{

    /**
     * Creates new form PlayFRM
     */
    int w,h;
    public UserControl contr;
    public User u;
    public Play_Thread pt;
    
    public int k=1 , m=4 , n=m+2;
    JButton b[][];
    int a[][];
    boolean vistor[][];
    int dx[]={-1,0,1,0};
    int dy[]={0,1,0,-1};
    
    public PlayFRM(UserControl contr, User u , int count){
        initComponents(); 
        contr.open_UDP();
//        init0
        this.m = count;
        this.n = m+2;
        this.b = new JButton[n][n];
        this.a = new int[n][n];
        this.vistor = new boolean[n][n] ; 
//        init1
        this.contr = contr;
        this.u = u;
        this.setTitle("User: "+u.getUserName());
        pt = new Play_Thread(this);
        pt.start();
        
        this.setLocationRelativeTo(null);
        
        for (int i=0 ; i<n ; i++) {
            for (int j=0; j<n ; j++) {
                a[i][j] = 0;
                vistor[i][j] = false;
            }
        }
        for (int i=1 ; i<n-1 ; i++) {
            for (int j=1 ; j<n-1 ; j++) {
                b[i][j] = new JButton();
                b[i][j].setSize(10,10);
                b[i][j].setActionCommand(i + " " + j);
                b[i][j].addActionListener(this);
                b[i][j].setBackground(new Color(255,102,51));
                p2.add(b[i][j]);
            }
        }
        p2.setLayout(new GridLayout(n-2, n-2, 0, 0));
        if(m==4){
            w = 185;
            h = 180;
        }
        if(m==9){
            w = 95;
            h = 93;
        }
        if(m==18){
            w = 50;
            h = 45;
        }
    }
    
    public void setLable(int second){
        if(second>5){
            tg.setForeground(Color.blue);
            tg.setText(second+"s");
        }
        else{
            tg.setForeground(Color.red);
            tg.setText(second+"s");
        }
    }
    
    public void setPlayer(String u){
        l2.setText(u);
    }

    
    public ArrayList BFS(pairx p1 , int color){
        ArrayList <pairx> ar = new ArrayList();
        ArrayList <pairx> ar1 = new ArrayList();
        boolean ok = false; 
        ar.add(p1);
        ar1.add(p1); 
        vistor[p1.fi][p1.se] = true;
//        System.out.println(ar.size());
        while(ar.size()>0){          
            pairx t = (pairx) ar.get(0); 
            ar.remove(0);
            int x = t.fi ; int y = t.se;
            for(int i=0 ; i<4 ;i++){
                int u1=x+dx[i] ; int u2=y+dy[i];
                if(a[u1][u2]==color && vistor[u1][u2]==false){
                    vistor[u1][u2] = true;
                    ar.add(new pairx(u1, u2));
                    ar1.add(new pairx(u1, u2));
                }
                else{
                    if(a[u1][u2]==0 && vistor[u1][u2]==false) ok=true;
                }
            }
        }
        if(ok==true) return null;
        else{
            return ar1;
        }
    }
    
    public ArrayList BFS1(int color){
        ArrayList <pairx> ar1 = new ArrayList();
        System.out.println(ar1.size());
        inspect_empty();
        for(int i=1 ; i<n-1 ; i++){
           for(int j=1 ; j<n-1 ;j++){
                boolean ok = false; 
                if(a[i][j]==0){
                    for(int v=0 ; v<4 ; v++){
                        int u1=i+dx[v] ; int u2=j+dy[v];
                        if(a[u1][u2]==0&&vistor[u1][u2]==false || a[u1][u2]==color) {ok = true; break;}
                    }
                    if(ok==false) {
                        pairx a = new pairx(i, j);
                        vistor[i][j] = true;
                        ar1.add(a);
                    }
                }
                
            } 
        }
        return ar1;
    }
    

        
    public void setimg(JButton b,int i,int j,int color) throws IOException {
        b.setBackground(Color.white);
        int k = color;
        if (k==1 && this.a[i][j]==0) {
            BufferedImage image = ImageIO.read(new File("./anh/quanden.png"));
            ImageIcon icon = new ImageIcon(image.getScaledInstance(w,h,20));
            b.setIcon(icon);
//            k=2;
            a[i][j]=1;
        }
        else{
            if(k==2 && this.a[i][j]==0){
                BufferedImage image = ImageIO.read(new File("./anh/quantrang.jpg"));
                ImageIcon icon = new ImageIcon(image.getScaledInstance(w,h,20));
                b.setIcon(icon);
//                k=1;
                a[i][j]=2;
            }
        }
    }
    
    public void change_color(ArrayList <pairx>ar, int color) throws IOException{
        int k = color;
        System.out.println(ar.size()+"tcp_ip.view.playFRM.change_color()" + color);
        while(!ar.isEmpty()){
            pairx p1 = ar.get(0); ar.remove(0);
            System.out.println(p1.getFi()+"_" +p1.getSe()+"___k="+k);
            if(k==2){
                BufferedImage image = ImageIO.read(new File("./anh/quanden.png"));
                ImageIcon icon = new ImageIcon(image.getScaledInstance(w,h, 20));
                b[p1.getFi()][p1.getSe()].setIcon(icon);
                a[p1.getFi()][p1.getSe()]=1;
            }
            else{
                BufferedImage image = ImageIO.read(new File("./anh/quantrang.jpg"));
                ImageIcon icon = new ImageIcon(image.getScaledInstance(w,h, 20));
                b[p1.getFi()][p1.getSe()].setIcon(icon);
                a[p1.getFi()][p1.getSe()]=2;
            }
        }
    }
    public boolean inspect_empty(){
        for(int i=0 ; i<n ; i++){
            for(int j=0 ; j<n ;j++){
                vistor[i][j]=false;              
            }
        }
        for(int i=0 ; i<n ; i++){
            vistor[0][i]=true;
            vistor[i][0]=true;
            vistor[n-1][i]=true;
            vistor[i][n-1]=true;
        }
        return false;
    }
    public void inspect_color(int color) throws IOException{
        inspect_empty();
        int count=0;
        for(int i=1 ; i<n-1 ; i++){
            for(int j=1 ; j<n-1 ; j++){
                if(a[i][j]==color && vistor[i][j]==false){
                    ArrayList inpect = new ArrayList();
                    pairx p1 = new pairx(i, j);
                    inpect = BFS(p1,color);
                    count++;
                    if(inpect!=null) change_color(inpect, color);
                }
            }
        }
        System.err.println(color+"__"+count);
    }
    public int convertColor(int color){
        if(color == 1) return 2;
        else return 1;
    }
    public int result(){
        int cw=0 , cb=0;
        for(int i=1 ; i<n-1 ; i++){
            for(int j=1 ; j<n-1 ; j++){
                if(a[i][j]==0 && vistor[i][j]==false) return 0;
                else{
                    if(a[i][j]==1) cb++;
                    else cw++; 
                }
            }
        }
        if(cb>cw) return 1;
        else{
            if(cb<cw) return 2;
            else return 3;
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        p2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btsend = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        ta1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        ta2 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        l2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        p2.setPreferredSize(new java.awt.Dimension(994, 916));

        javax.swing.GroupLayout p2Layout = new javax.swing.GroupLayout(p2);
        p2.setLayout(p2Layout);
        p2Layout.setHorizontalGroup(
            p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1072, Short.MAX_VALUE)
        );
        p2Layout.setVerticalGroup(
            p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 898, Short.MAX_VALUE)
        );

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Quay Lại");
        jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btsend.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btsend.setText("Gửi");
        btsend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsendActionPerformed(evt);
            }
        });

        ta1.setColumns(20);
        ta1.setRows(5);
        jScrollPane3.setViewportView(ta1);

        ta2.setColumns(20);
        ta2.setRows(5);
        jScrollPane2.setViewportView(ta2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btsend, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(136, 136, 136))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btsend, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Lượt của :");

        l2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        l2.setForeground(new java.awt.Color(51, 102, 255));
        l2.setText("jLabel2");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Thời gian:");

        tg.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tg.setForeground(new java.awt.Color(51, 102, 255));
        tg.setText("jLabel2");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(l2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(tg, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tg, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(l2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(p2, javax.swing.GroupLayout.PREFERRED_SIZE, 1072, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(p2, javax.swing.GroupLayout.PREFERRED_SIZE, 898, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            ServerReceiveData srd = new ServerReceiveData();
            srd.setSendto("u_close"); srd.setUser(u);
            contr.senddata_UDP(srd);
            System.out.println("Game_online.User.View.PlayFRM.formWindowClosed()");
        } catch (IOException ex) {
            Logger.getLogger(PlayFRM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    public void requestNewPort(){
        try {
            ServerReceiveData srd = new ServerReceiveData();
            srd.setSendto("u_getnewPort");
            contr.senddata_UDP(srd);
            ServerSendData ssd = contr.receivedata_UDP();
            if(ssd.getSendto().equals("sv_getnewPort")){
                this.contr = new UserControl(ssd.getUser().getUserPort());
            }
            contr.open_TCP();
        } catch (IOException ex) {
            Logger.getLogger(PlayFRM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PlayFRM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (pt.stop == 4) {
            requestNewPort();
            ServerReceiveData srd = new ServerReceiveData();
            srd.setSendto("u_Back");
            new SelectPlayerFRM(contr, u).setVisible(true);
            pt.stop();
            this.dispose();
        } else {
            int i = JOptionPane.showConfirmDialog(rootPane, "Nếu bạn thoát sẽ tính thua ! \n Vẫn Thoát?");
            if (i == 0) {
                try {
                    ServerReceiveData srd = new ServerReceiveData();
                    srd.setSendto("u_Back");
                    contr.senddata_UDP(srd);
                    requestNewPort();
                    new SelectPlayerFRM(contr, u).setVisible(true);
                    pt.stop();
                    this.dispose();
                } catch (IOException ex) {
                    Logger.getLogger(PlayFRM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    public boolean checksended() throws IOException, ClassNotFoundException{
        ServerReceiveData srd = new ServerReceiveData();
        srd.setSendto("u_checkSend");
        contr.senddata_UDP(srd);
        
        ServerSendData ssd = new ServerSendData();
                ssd = contr.receivedata_UDP();
        if(ssd.getSendto().equals("sv_checkSend")){
            if(ssd.getUser().getUserChat().isSended()) return true;
            else return false;
        }
        else return false;
    }
    public void send_chat(String str) throws IOException{
        ServerReceiveData srd = new ServerReceiveData();
        srd.setSendto("u_sendchat");
        
        User u = new User();
        u.getUserChat().setContentChat(str);
        srd.setUser(u);
        
        contr.senddata_UDP(srd);
    }
    
    private void btsendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsendActionPerformed
         if(!ta1.getText().equals("")){
             try {
                 if(checksended()){
                     send_chat(ta1.getText());
                     if(ta1.getText().length()<=50){
                         ta2.append("\n" + "User: "+this.u.getUserName() + " :" + ta1.getText());
                     }
                     else{
                         int space = 0;
                         space = ta1.getText().indexOf(" ");
                         System.out.println(space);
                         if(space !=-1){
                             int count = ta1.getText().length()/50+1;
                             int dem = 1;
                             for(String str : ta1.getText().split("\\s", count)){
                                 if(dem==1){
                                     ta2.append("\n" + "User: "+this.u.getUserName() + " :" + str);
                                     dem = 2;
                                 }
                                 else ta2.append("\n" + str);
                             }
                         }
                         else{
                             ta2.append("\n" + "User: "+this.u.getUserName() + " :" + ta1.getText());
                         }
                     }
                     ta1.setText("");
                 }
                 else JOptionPane.showMessageDialog(rootPane, "Gửi chậm lại !");
             } catch (IOException ex) {
                 Logger.getLogger(PlayFRM.class.getName()).log(Level.SEVERE, null, ex);
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(PlayFRM.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
    }//GEN-LAST:event_btsendActionPerformed

    public void rivalChat(String userName,String str){
        if(!str.equals("")){
            if(str.length()<=50){
                ta2.append("\n" + "User: "+userName + " :" + str);
            }   
            else{
                int space = 0;
                space = str.indexOf(" ");
                System.out.println(space);
                if(space !=-1){
                    int count = ta1.getText().length()/50+1;
                    int dem = 1;
                    for(String str1 : str.split("\\s", count)){
                        if(dem==1){
                            ta2.append("\n" + "User: "+userName + " :" + str1);
                            dem = 2;
                        }
                        else ta2.append("\n" + str1);
                    }
                }
                else{
                    ta2.append("\n" + "User: "+userName+ " :" + str);
                }
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PlayFRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlayFRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlayFRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlayFRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new PlayFRM().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btsend;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel l2;
    private javax.swing.JPanel p2;
    private javax.swing.JTextArea ta1;
    private javax.swing.JTextArea ta2;
    private javax.swing.JLabel tg;
    // End of variables declaration//GEN-END:variables

    public void send_setimg(int i,int j) throws IOException{
        System.out.println("sending_actionbutton");
        ServerReceiveData srd = new ServerReceiveData();
        this.u.getStepPlay().setFi(i); u.getStepPlay().setSe(j);
        srd.setSendto("u_setimg"); srd.setUser(u);
        this.u.getStepPlay().setCheckTurn(false); 
        pt.second=30;
        contr.senddata_UDP(srd);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int k = this.k;
        int k1 = convertColor(k);
        System.out.println("User.View.PlayFRM.actionPerformed()______" + e.getActionCommand());
        boolean ok=false;
        if (!e.getActionCommand().equals("") && u.getStepPlay().isCheckTurn()) {
            // setimg
            String a[] = e.getActionCommand().split(" ");
            int i = Integer.valueOf(a[0]);
            int j = Integer.valueOf(a[1]);
            if(this.a[i][j]==0){
                System.out.println("---------");
                try { 
                    ArrayList <pairx> x = BFS1(k);
                    if(x.size()>0){
                        for(int v=0 ; v<x.size() ; v++){
                            pairx a1 = (pairx) x.get(v);
                            if(i==a1.fi && j==a1.se){
                                ok = true;
                                if(result()!=0){
                                    System.out.println("???");
                                    if(result()==1){
                                        setimg(b[i][j],i,j,k);
                                        send_setimg(i, j);
                                        JOptionPane.showMessageDialog(rootPane, "Người chơi quân đen thắng");
                                        sendResult(1);
                                    }
                                    else{
                                        if(result()==2){
                                            setimg(b[i][j],i,j,k);
                                            send_setimg(i, j);
                                            JOptionPane.showMessageDialog(rootPane, "Người chơi quân trắng thắng"); 
                                            sendResult(2);
                                        }
                                        else {
                                            setimg(b[i][j],i,j,k);
                                            send_setimg(i, j);
                                            JOptionPane.showMessageDialog(rootPane, "Hòa");
                                            sendResult(3);
                                        }
                                    }
                                }
                                else JOptionPane.showMessageDialog(rootPane, "Bạn không thể đi nước đi không có khí"); 
                            }
                        }
                        if(ok==false){
                            setimg(b[i][j],i,j,k);
                            inspect_color(k1);
                            send_setimg(i, j);
                        }
                    }
                    else{
                        setimg(b[i][j],i,j,k);
                        send_setimg(i, j);
                        if(result()!=0){
                            System.out.println("???");
                            if(result()==1){
                                        setimg(b[i][j],i,j,k);
                                        send_setimg(i, j);
                                        JOptionPane.showMessageDialog(rootPane, "Người chơi quân đen thắng");
                                        sendResult(1);
                                    }
                                    else{
                                        if(result()==2){
                                            setimg(b[i][j],i,j,k);
                                            send_setimg(i, j);
                                            JOptionPane.showMessageDialog(rootPane, "Người chơi quân trắng thắng"); 
                                            sendResult(2);
                                        }
                                        else {
                                            setimg(b[i][j],i,j,k);
                                            send_setimg(i, j);
                                            JOptionPane.showMessageDialog(rootPane, "Hòa"); 
                                            sendResult(3);
                                        }
                                    }
                        }
                        else{
                            setimg(b[i][j],i,j,k);  
                            inspect_color(k1);
                            send_setimg(i, j);
                            
                        }
                        
                    }             
                } catch (IOException ex) {
                    Logger.getLogger(PlayFRM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
        }
    }
 
    public void sendResult(int color_win) throws IOException{
        ServerReceiveData srd = new ServerReceiveData();
        User ur = new User();
        if(k == color_win){
           u.getStepPlay().setCheckResult("thắng");
           ur.getStepPlay().setCheckResult("thua");
        }
        else{
            if(color_win==3){
                u.getStepPlay().setCheckResult("hòa");
                ur.getStepPlay().setCheckResult("hòa");;
            }
            else{
                u.getStepPlay().setCheckResult("thua");
                ur.getStepPlay().setCheckResult("thắng");
            }
        }
        if(k==1){
            srd.setSendto("u_Result");
            srd.setUser(u);
            srd.setUserRival(ur);
            contr.senddata_UDP(srd);
        }
        pt.stop=2;
    }
    
    public void update_step(int i ,int j){
        int k = convertColor(this.k);
        int k1 = convertColor(k);
        boolean ok=false;
        if(this.a[i][j]==0){
                System.out.println("---------");
                try { 
                    ArrayList <pairx> x = BFS1(k);
                    System.out.println(x.size());
                    if(x.size()>0){
                        for(int v=0 ; v<x.size() ; v++){
                            pairx a1 = (pairx) x.get(v);
                            if(i==a1.fi && j==a1.se){
                                ok = true;
//                                this.a[i][j] = convertColor(k);
                                if(result()!=0){
                                    System.out.println("???");
                                    if(result()==1){
                                        setimg(b[i][j],i,j,k);
                                        send_setimg(i, j);
                                        JOptionPane.showMessageDialog(rootPane, "Người chơi quân đen thắng");
                                        sendResult(1);
                                    }
                                    else{
                                        if(result()==2){
                                            setimg(b[i][j],i,j,k);
                                            send_setimg(i, j);
                                            
                                            JOptionPane.showMessageDialog(rootPane, "Người chơi quân trắng thắng"); 
                                            sendResult(2);
                                        }
                                        else {
                                            setimg(b[i][j],i,j,k);
                                            send_setimg(i, j);
                                            JOptionPane.showMessageDialog(rootPane, "Hòa");
                                            sendResult(3);
                                        }
                                    }
                                }
                                else JOptionPane.showMessageDialog(rootPane, "Bạn không thể đi nước đi không có khí"); 
//                                this.a[i][j] = 0;
                            }
                        }
                        if(ok==false){
                            setimg(b[i][j],i,j,k);   
                            inspect_color(k1);
                        }
                    }
                    else{
                        setimg(b[i][j],i,j,k);
                        if(result()!=0){
                            System.out.println("???");
                            if(result()==1){
                                        setimg(b[i][j],i,j,k);
                                        send_setimg(i, j);
                                        JOptionPane.showMessageDialog(rootPane, "Người chơi quân đen thắng");
                                        sendResult(1);
                                    }
                                    else{
                                        if(result()==2){
                                            setimg(b[i][j],i,j,k);
                                            send_setimg(i, j);
                                            JOptionPane.showMessageDialog(rootPane, "Người chơi quân trắng thắng"); 
                                            sendResult(2);
                                        }
                                        else {
                                            setimg(b[i][j],i,j,k);
                                            send_setimg(i, j);
                                            JOptionPane.showMessageDialog(rootPane, "Hòa"); 
                                            sendResult(3);
                                        }
                                    }
                        }
                        else{
                            setimg(b[i][j],i,j,k);   
                            inspect_color(k1);
                        }
                        
                    }             
                } catch (IOException ex) {
                    Logger.getLogger(PlayFRM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  
    }
    public int playAgain(){
        int i = JOptionPane.showConfirmDialog(rootPane, "Mời đối thủ chơi tiếp?");
        return i;
    }
    public void UserRivalReject(String ur){
        JOptionPane.showMessageDialog(rootPane, "Người chơi: " + ur +" Từ chối đấu lại !");
    }
    public void urRetreat(){
        JOptionPane.showMessageDialog(rootPane, "Đối thủ bỏ cuộc bạn thắng !");
    }
    
}
