package com.subrat;

public class Room {
    private int roomNo;
    private String type;
    private double price;
    private boolean available = true;

    public Room(int roomNo, String type, double price){
        this.roomNo = roomNo;
        this.type = type;
        this.price = price;
    }

    public int getRoomNo(){ return roomNo; }
    public String getType(){ return type; }
    public double getPrice(){ return price; }
    public boolean isAvailable(){ return available; }

    public void setAvailable(boolean status){
        this.available = status;
    }

    @Override
    public String toString(){
        return "Room " + roomNo +
                " | " + type +
                " | ₹" + price +
                " | " + (available ? "Available" : "Occupied");
    }
}