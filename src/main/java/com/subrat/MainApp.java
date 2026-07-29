package com.subrat;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {

    BookingManager manager = new BookingManager();

    public void start(Stage stage){

        TabPane tabs=new TabPane();

        tabs.getTabs().add(addRoomTab());
        tabs.getTabs().add(bookingTab());
        tabs.getTabs().add(viewTab());
        tabs.getTabs().add(searchTab());

        stage.setScene(new Scene(tabs,900,600));
        stage.setTitle("SUBRAT HOTEL PRO");
        stage.show();
    }

    
    Tab addRoomTab(){
        TextField r=new TextField();
        TextField t=new TextField();
        TextField p=new TextField();

        Button add=new Button("Add Room");
        Label msg=new Label();

        add.setOnAction(e->{
            try{
                manager.addRoom(new Room(
                        Integer.parseInt(r.getText()),
                        t.getText(),
                        Double.parseDouble(p.getText())
                ));
                msg.setText("Room Added");
            }catch(Exception ex){
                msg.setText("Invalid Input");
            }
        });

        VBox box=new VBox(10,
                new Label("Room Number"),r,
                new Label("Type"),t,
                new Label("Price"),p,
                add,msg);

        box.setStyle("-fx-padding:20; -fx-background-color: #f0f8ff;");

        return new Tab("Add Room",box);
    }

    
    Tab bookingTab(){
        TextField r=new TextField();
        TextField name=new TextField();
        TextField days=new TextField();

        Button book=new Button("Book Room");
        Button billBtn=new Button("Generate Bill");
        Button checkout=new Button("Checkout");

        Label result=new Label();

        
        book.setOnAction(e->{
            try{
                boolean ok=manager.bookRoom(
                        Integer.parseInt(r.getText()),
                        name.getText(),
                        Integer.parseInt(days.getText())
                );

                result.setText(ok ? "Booking Successful" : "Booking Failed");
            }catch(Exception ex){
                result.setText("Invalid Input");
            }
        });

    
        billBtn.setOnAction(e->{
            try{
                String bill=manager.generateBill(Integer.parseInt(r.getText()));
                new Alert(Alert.AlertType.INFORMATION,bill).show();
            }catch(Exception ex){
                result.setText("Enter valid room number");
            }
        });

        
        checkout.setOnAction(e->{
            try{
                manager.checkout(Integer.parseInt(r.getText()));
                result.setText("Checked Out");
            }catch(Exception ex){
                result.setText("Invalid Input");
            }
        });

        VBox box=new VBox(10,
                new Label("Room Number"),r,
                new Label("Customer Name"),name,
                new Label("Days"),days,
                book,billBtn,checkout,result);

        box.setStyle(
                "-fx-background-color: linear-gradient(to right, #74ebd5, #ACB6E5);" +
                "-fx-padding:20;"
        );

        return new Tab("Booking",box);
    }

    
    Tab viewTab(){

        TableView<Room> table=new TableView<>();

        TableColumn<Room,Integer> c1=new TableColumn<>("Room");
        c1.setCellValueFactory(x->
                new javafx.beans.property.SimpleIntegerProperty(x.getValue().getRoomNo()).asObject());

        TableColumn<Room,String> c2=new TableColumn<>("Type");
        c2.setCellValueFactory(x->
                new javafx.beans.property.SimpleStringProperty(x.getValue().getType()));

        TableColumn<Room,Double> c3=new TableColumn<>("Price");
        c3.setCellValueFactory(x->
                new javafx.beans.property.SimpleDoubleProperty(x.getValue().getPrice()).asObject());

        TableColumn<Room,String> c4=new TableColumn<>("Status");
        c4.setCellValueFactory(x->
                new javafx.beans.property.SimpleStringProperty(
                        x.getValue().isAvailable() ? "Available" : "Occupied"
                ));

        table.getColumns().addAll(c1,c2,c3,c4);

        Button load=new Button("Load Rooms");
        Button available=new Button("Available");

        load.setOnAction(e->table.getItems().setAll(manager.getRooms()));

        available.setOnAction(e->{
            table.getItems().clear();
            for(Room r: manager.getRooms()){
                if(r.isAvailable()) table.getItems().add(r);
            }
        });

        Label stats=new Label();

        Button statBtn=new Button("Stats");
        statBtn.setOnAction(e->{
            stats.setText("Total: "+manager.totalRooms()+
                    "  Occupied: "+manager.occupiedRooms());
        });

        VBox box=new VBox(10,load,available,statBtn,stats,table);
        box.setStyle("-fx-padding:15;");

        return new Tab("View",box);
    }

    
    Tab searchTab(){

        TextField r=new TextField();
        Label res=new Label();

        Button s=new Button("Search");

        s.setOnAction(e->{
            try{
                Room rm=manager.searchRoom(Integer.parseInt(r.getText()));
                if(rm!=null) res.setText("Found: "+rm.getType());
                else res.setText("Not Found");
            }catch(Exception ex){
                res.setText("Invalid Input");
            }
        });

        VBox box=new VBox(10,r,s,res);
        box.setStyle("-fx-padding:20;");

        return new Tab("Search",box);
    }

    public static void main(String[] args){
        launch(args);
    }
}