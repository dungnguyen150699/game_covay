/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User.View;

import Model.ServerReceiveData;
import Model.ServerSendData;
import Model.User;
import User.Control.UserControl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class RankFRM extends javax.swing.JFrame {

    /**
     * Creates new form Rank
     */
    public UserControl contr;
    public User u;
    public DefaultTableModel model;
    public DefaultComboBoxModel cbmodel;
    public RankFRM(UserControl contr, User u) {
        initComponents();
        model = (DefaultTableModel) tbl1.getModel();
        cbmodel = (DefaultComboBoxModel) cb.getModel();
        this.u = u;
        this.contr = contr;
        
        
        
        setTitle("User:"+u.getUserName());
        String str = cb.getSelectedItem()+"";
        str = str.trim();
        System.out.println(str);
        update_table(getInfor(str),str);
        setLocationRelativeTo(null);
    }
    
    public ArrayList getInfor(String x){
        System.out.println("send");
        ServerReceiveData srd = new ServerReceiveData();
        srd.setSendto(x);
        contr.SendData(srd);
        
        ServerSendData ssd = new ServerSendData();
        ssd = contr.ReceiveData();
        System.out.println("rend");
        return ssd.getAr();
    }
    
    public ArrayList sort1(ArrayList <User>ar , String x){
        System.out.println("'"+x+"'");
        if(x.equals("Tổng số điểm")){
            System.out.println("this");
            for (int i=0; i<ar.size()-1 ; i++){
                for(int j=i+1 ; j<ar.size() ; j++){
                    
                    User u1 = ar.get(i);
                    int reconcile1 = u1.getTotalMatch() - u1.getTotalWin() - u1.getTotalLost();
                    float point1 = (float) (u1.getTotalWin() + 0.5*reconcile1);
                    
                    User u2 = ar.get(j);
                    int reconcile = u2.getTotalMatch() - u2.getTotalWin() - u2.getTotalLost();
                    float point2 = (float) (u2.getTotalWin() + 0.5*reconcile);
                    
                    System.out.println(point1+"<"+point2);
                    if(point1 < point2){
                        Collections.swap(ar, i, j);
                    }
                }
            }
        }
        if(x.equals("Trung bình điểm đối thủ đã gặp")){
            l1.setText("");
            for (int i=0; i<ar.size()-1 ; i++){
                for(int j=i+1 ; j<ar.size() ; j++){
                    
                    User u1 = ar.get(i);
                    int reconcile1 = u1.getTotalMatch() - u1.getTotalWin() - u1.getTotalLost();
                    float point1 = (float) (u1.getTotalWin() + 0.5*reconcile1);
                    
                    User u2 = ar.get(j);
                    int reconcile = u2.getTotalMatch() - u2.getTotalWin() - u2.getTotalLost();
                    float point2 = (float) (u2.getTotalWin() + 0.5*reconcile);
                    
                    if(point1 < point2){
                        Collections.swap(ar, i, j);
                    }
                }
            }
        }
        
        return ar;
    }
    
    public void update_table(ArrayList <User>ar,String x){
        model.setRowCount(0);
        int stt=1 ;
        if(ar.size()>0){
            if(x.equals("Tổng số điểm")){
                sort1(ar,"Tổng số điểm");
                for (int i = 0; i < ar.size(); i++) {
                    User u = ar.get(i);
                    
                    if(this.u.getUserName().equals(u.getUserName())) l1.setText("Hạng của bạn : "+ stt);
                    
                    int reconcile = u.getTotalMatch() - u.getTotalWin() - u.getTotalLost();
                    float point = (float) (u.getTotalWin() + 0.5*reconcile);
                    
                    Object o[] = {stt,u.getUserName(),u.getTotalWin(),u.getTotalLost(),u.getTotalMatch(),point+""};
                    model.addRow(o);
                    stt++;  
                }
            }
            if(x.equals("Trung bình điểm đối thủ đã gặp")){
                ar = sort1(ar,"Trung bình điểm đối thủ đã gặp");
                for (int i = 0; i < ar.size(); i++) {
                    User u = ar.get(i);
                    
                    int reconcile = u.getTotalMatch() - u.getTotalWin() - u.getTotalLost();
                    float point = (float) (u.getTotalWin() + 0.5*reconcile);
                    
                    Object o[] = {stt,u.getUserName(),u.getTotalWin(),u.getTotalLost(),u.getTotalMatch(),point+""};
                    model.addRow(o);
                    stt++;  
                }
            }
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

        jScrollPane2 = new javax.swing.JScrollPane();
        tbl1 = new javax.swing.JTable();
        cb = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        l1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tbl1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stt", "Tên ", "Trận thắng", "Trận thua", "Tổng trận ", "Điểm "
            }
        ));
        tbl1.setRowHeight(25);
        tbl1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl1);
        if (tbl1.getColumnModel().getColumnCount() > 0) {
            tbl1.getColumnModel().getColumn(0).setMinWidth(70);
            tbl1.getColumnModel().getColumn(0).setMaxWidth(70);
        }

        cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tổng số điểm ", "Trung bình điểm đối thủ đã gặp" }));
        cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton1.setText("Quay lại");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(l1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addComponent(cb, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new HomeFRM(contr, u).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbActionPerformed
        String str = cb.getSelectedItem()+"";
        str = str.trim();
        update_table(getInfor(str),str);
    }//GEN-LAST:event_cbActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        ServerReceiveData srd = new ServerReceiveData();
        srd.setSendto("u_close"); srd.setUser(u);
        contr.SendData(srd);
    }//GEN-LAST:event_formWindowClosing

    private void tbl1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl1MouseClicked
        int row = tbl1.getSelectedRow();
        User ur = new User();
        ur.setUserName(model.getValueAt(row,1)+"");
        new HistoryFRM(contr, u, ur).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_tbl1MouseClicked

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
            java.util.logging.Logger.getLogger(RankFRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RankFRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RankFRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RankFRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new RankFRM().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cb;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel l1;
    private javax.swing.JTable tbl1;
    // End of variables declaration//GEN-END:variables
}
