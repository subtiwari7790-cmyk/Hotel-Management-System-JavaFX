package com.subrat;

import java.util.*;
import java.io.*;

public class BookingManager {

    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Customer> customers = new ArrayList<>();

    public BookingManager(){
        load(); 
    }

    public void addRoom(Room r){
        rooms.add(r);
        save();
    }

    
    public boolean bookRoom(int roomNo, String name, int days){
        for(Room r: rooms){
            if(r.getRoomNo()==roomNo && r.isAvailable()){
                r.setAvailable(false);
                customers.add(new Customer(roomNo,name,days));
                save();
                return true;
            }
        }
        return false;
    }

    public void checkout(int roomNo){
        for(Room r: rooms){
            if(r.getRoomNo()==roomNo){
                r.setAvailable(true);
            }
        }
        customers.removeIf(c->c.getRoomNo()==roomNo);
        save();
    }

    public double calculateBill(int roomNo,int days){
        for(Room r: rooms){
            if(r.getRoomNo()==roomNo){
                return r.getPrice()*days;
            }
        }
        return 0;
    }

    public Room searchRoom(int roomNo){
        for(Room r: rooms){
            if(r.getRoomNo()==roomNo) return r;
        }
        return null;
    }

    public int totalRooms(){ return rooms.size(); }

    public int occupiedRooms(){
        int c=0;
        for(Room r: rooms){
            if(!r.isAvailable()) c++;
        }
        return c;
    }

    public ArrayList<Room> getRooms(){ return rooms; }

    
    public String generateBill(int roomNo){
        for(Customer c: customers){
            if(c.getRoomNo()==roomNo){
                Room r = searchRoom(roomNo);

                double total = r.getPrice() * c.getDays();

                return "----- HOTEL BILL -----\n" +
                       "Customer: " + c.getName() + "\n" +
                       "Room No: " + roomNo + "\n" +
                       "Type: " + r.getType() + "\n" +
                       "Days: " + c.getDays() + "\n" +
                       "Price/Day: ₹" + r.getPrice() + "\n" +
                       "Total: ₹" + total;
            }
        }
        return "No booking found";
    }


    void save(){
        try{
            FileWriter fw=new FileWriter("data.txt");

            for(Room r: rooms){
                fw.write("R,"+r.getRoomNo()+","+r.getType()+","+r.getPrice()+","+r.isAvailable()+"\n");
            }

            for(Customer c: customers){
                fw.write("C,"+c.getRoomNo()+","+c.getName()+","+c.getDays()+"\n");
            }

            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    
    void load(){
        try{
            File f=new File("data.txt");
            if(!f.exists()) return;

            Scanner sc=new Scanner(f);

            while(sc.hasNextLine()){
                String line=sc.nextLine();
                String[] p=line.split(",");

                if(p[0].equals("R")){
                    Room r=new Room(
                        Integer.parseInt(p[1]),
                        p[2],
                        Double.parseDouble(p[3])
                    );
                    r.setAvailable(Boolean.parseBoolean(p[4]));
                    rooms.add(r);
                }
                else if(p[0].equals("C")){
                    customers.add(new Customer(
                        Integer.parseInt(p[1]),
                        p[2],
                        Integer.parseInt(p[3])
                    ));
                }
            }

            sc.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}