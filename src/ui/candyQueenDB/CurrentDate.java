/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.candyQueenDB;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author Falcon
 */
public class CurrentDate {
    private String dateObtained;
    private String timeObtained;
    
    public void setDate(){
        Date date = new Date();
        SimpleDateFormat getDate = new SimpleDateFormat("yyyy/MM/dd");
        dateObtained = getDate.format(date);
    }
    
    public void setTime(){
        Date time = new Date();
        SimpleDateFormat getTime = new SimpleDateFormat("hh:mm:ss");
        timeObtained = getTime.format(time);
    }
    
    public String getDate(){
        return dateObtained;
    }
    
    public String getTime(){
        return timeObtained;
    }
    
    
}

    