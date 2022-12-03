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
public class UserChat implements Serializable{
    public boolean news = false;
    public String contentChat = "";
    public boolean sended = false;

    public UserChat() {
    }

    public boolean isSended() {
        return sended;
    }

    public void setSended(boolean sended) {
        this.sended = sended;
    }

    public boolean isNews() {
        return news;
    }

    public void setNews(boolean news) {
        this.news = news;
    }

    public String getContentChat() {
        return contentChat;
    }

    public void setContentChat(String contentChat) {
        this.contentChat = contentChat;
    }
    
}
