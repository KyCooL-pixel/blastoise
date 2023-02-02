package com.blastoisefx.model;

public class Notification {
    private String machineString;
    private String dateTime;
    private boolean seen;
    public Notification(String txt, String dTString){
        machineString=txt;
        dateTime = dTString;
        seen = false;
    }

    public String getMachineString(){
        return machineString;
    }

    public String getDateTime(){
        return dateTime;
    }

    public boolean getSeen(){
        return seen;
    }

    public void setSeen(boolean seen){
        this.seen = seen;

    }
}
