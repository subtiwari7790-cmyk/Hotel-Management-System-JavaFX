package com.subrat;

public class Customer {
    private int roomNo;
    private String name;
    private int days;

    public Customer(int roomNo, String name, int days){
        this.roomNo = roomNo;
        this.name = name;
        this.days = days;
    }

    public int getRoomNo(){ return roomNo; }
    public String getName(){ return name; }
    public int getDays(){ return days; }

    @Override
    public String toString(){
        return "Customer: " + name + " | Room: " + roomNo + " | Days: " + days;
    }
}