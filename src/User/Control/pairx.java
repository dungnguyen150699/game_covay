/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User.Control;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class pairx implements Serializable{
    public int fi,se;

    public pairx(int fi, int se) {
        this.fi = fi;
        this.se = se;
    }

    public int getFi() {
        return fi;
    }

    public void setFi(int fi) {
        this.fi = fi;
    }

    public int getSe() {
        return se;
    }

    public void setSe(int se) {
        this.se = se;
    }
    
}