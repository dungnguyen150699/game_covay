/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Admin
 */
public class checkinvalid {
    public boolean check(String s){
        int index = 0;
        index = s.indexOf("'");
        if(index!=-1)  return false;
        else{
            index = s.indexOf("-");
            if(index!= -1) return false;
            else return true;
        }    
    }
    
    public static String Standard_String(String a){
        String text = a;
        text = text.trim();
        text = text.toLowerCase();    
        text = text.replaceAll("\\s+","");
        return text;
    }
    
    public static void main(String[] args) {
        checkinvalid c = new checkinvalid();
        System.out.println(c.check("a''f"));
    }
}
