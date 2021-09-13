/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.*; 
import javafx.beans.value.*; 
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.*; 
import javafx.scene.text.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import sun.plugin.javascript.navig4.Layer;

/**
 *
 * @author Yehia
 */
public class JavaFXApplication1 extends Application{
    Scene scene,scene2,scene3,scene4,scene5,scene6,scene7,scene8,scene9,scene10,scene11,scene12;
    static String code="";
    static int tprice;
    static String item;
    @Override
    public void start(Stage primaryStage) {
       //scene
       
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(11.5,12.5,13.5,14.5));
        pane.setHgap(5.5);
        pane.setVgap(5.5);
        pane.add(new Label("Username:"),0,0);
        TextField tx1=new TextField();
        pane.add(tx1,1,0);
        pane.add(new Label("Password:"),0,1);
        PasswordField p1=new PasswordField();
        pane.add(p1,1,1);
        Button btn1 = new Button("Log in");
        Button btn2 = new Button("register");
        pane.add(btn1, 1, 3);
        pane.add(btn2, 1, 3);
        String st0[] = { "manager", "salesman", "customer"}; 
        ChoiceBox c2 = new ChoiceBox(FXCollections.observableArrayList(st0));
        pane.add(c2, 0, 5);
      Label l2 = new Label("nothing selected"); 
        c2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {  
            @Override
            public void changed(ObservableValue ov, Number value, Number new_value) 
            { 
                l2.setText(st0[new_value.intValue()] + " selected"); 
            } 
        }); 
        GridPane.setHalignment(btn1, HPos.RIGHT);
        GridPane.setHalignment(btn2, HPos.CENTER);
        btn1.setOnAction((ActionEvent event) -> {
            try {
                Socket socket = new Socket("localhost", 5050);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("log in");
           dos.writeUTF(tx1.getText());
           dos.writeUTF(p1.getText());
           dos.writeUTF((String) c2.getValue());
           int x =dis.read();
           if(x==1)
           {
               if(c2.getValue().equals("customer")){
                   primaryStage.setScene(scene4);
                   primaryStage.setTitle("Customer account");}
               else if(c2.getValue().equals("manager")){
                   primaryStage.setScene(scene6);
                   primaryStage.setTitle("Manager account");}
               else{primaryStage.setScene(scene7);
               primaryStage.setTitle("Salesman account");
               }
           } else {
            JOptionPane.showMessageDialog(null,"wrong user name or password");
           }
           dos.flush(); 
           dos.close();
            } catch(IOException e){ 
            e.printStackTrace(); 
        } 
        });
         btn2.setOnAction((ActionEvent event) -> {
        primaryStage.setScene(scene2); 
        primaryStage.setTitle("Register");
        });
        //scene2
        GridPane pane2 = new GridPane();
        pane2.setPadding(new Insets(11.5,12.5,13.5,14.5));
        pane2.setHgap(5.5);
        pane2.setVgap(5.5);
        TextField tx2=new TextField();
        TextField tx3=new TextField();
        TextField tx4=new TextField();
        PasswordField p2= new PasswordField();
        pane2.add(new Label("Name:"),0,0);
        pane2.add(tx2,1,0);
        pane2.add(new Label("User Name:"),0,1);
        pane2.add(tx3,1,1);
        pane2.add(new Label("Email:"),0,2);
        pane2.add(tx4,1,2);
        pane2.add(new Label("Password:"),0,3);
        pane2.add(p2,1,3);
        pane2.add(new Label("retype password:"),0,4);
        pane2.add(new PasswordField(),1,4);
        Button btnreg = new Button("Register");
        pane2.add(btnreg, 1, 8);
        Button btnbck1 = new Button("Back");
        pane2.add(btnbck1, 0, 8);
        GridPane.setHalignment(btnreg, HPos.RIGHT);
        GridPane.setHalignment(btnbck1, HPos.LEFT);
        Button b = new Button("show"); 
        Label l1 = new Label("nothing selected"); 
        pane2.add(l1,1,5);     
        String st[] = { "Manager", "Sales Man", "Customer"}; 
        ChoiceBox c = new ChoiceBox(FXCollections.observableArrayList(st));
        pane2.add(c, 0, 5);
         c.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {  
            @Override
            public void changed(ObservableValue ov, Number value, Number new_value) 
            { 
                l1.setText(st[new_value.intValue()] + " selected"); 
            } 
        }); 
        scene = new Scene(pane);
        btnbck1.setOnAction((ActionEvent event) -> {
        primaryStage.setScene(scene); 
        primaryStage.setTitle("Log in");
        });
        btnreg.setOnAction((ActionEvent event) -> {
        try {
            Socket socket = new Socket("localhost", 5050);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("register");
           dos.writeUTF(tx2.getText());
           dos.writeUTF(tx3.getText());
           dos.writeUTF(tx4.getText());
           dos.writeUTF(p2.getText());
           dos.writeUTF((String)c.getValue());
           dos.flush(); 
           dos.close();
            } catch(IOException e){ 
            e.printStackTrace(); 
        } 
        });
        scene2=new Scene (pane2);
        //scene3
        GridPane pane3=new GridPane();
        pane3.setPadding(new Insets(11.5,12.5,13.5,14.5));
        pane3.setHgap(5.5);
        pane3.setVgap(5.5);
        pane3.add(new Label("Feedback:"),0,0);
        TextField t20 =new TextField();
        pane3.add(t20,1,0);
        Button btnsub = new Button("Submit");
        pane3.add(btnsub, 1, 3);
        Button btb = new Button("Back");
        GridPane.setHalignment(btnsub, HPos.RIGHT);
         btnsub.setOnAction((ActionEvent event)->{
            try {
                Socket socket = new Socket("localhost", 5050);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("Feedback");
                dos.writeUTF(tx1.getText());
                dos.writeUTF(t20.getText());
           dos.flush();
           dos.close();
            } catch (IOException ex) {
                Logger.getLogger(JavaFXApplication1.class.getName()).log(Level.SEVERE, null, ex);
            }
                 });
          btb.setOnAction((ActionEvent event) -> {
        primaryStage.setScene(scene4); 
        primaryStage.setTitle("Customer account");
        });
       
       scene3=new Scene (pane3);
       //scene4
       Button btnf = new Button("Add Feedback");
       Button btnm = new Button("Menu");
        Button btnacc = new Button("Manage account");
        Label n2 = new Label("Welcome !");
        final double MAX_FONT_SIZE = 30.0; 
        n2.setFont(new Font(MAX_FONT_SIZE)); 
        BorderPane.setAlignment(n2,Pos.BOTTOM_CENTER);
        BorderPane borderPane = new BorderPane();
        HBox paneb = new HBox();
        borderPane.setTop(n2);
        borderPane.setCenter(paneb);
        BorderPane.setAlignment(paneb,Pos.CENTER);
        paneb.getChildren().add(btnm);
        paneb.getChildren().add(btnacc);
        paneb.getChildren().add(btnf);
        paneb.setSpacing(40);
        paneb.setAlignment(Pos.CENTER);
        scene4= new Scene(borderPane, 400, 300);
        btnm.setOnAction((ActionEvent event) -> {
        primaryStage.setScene(scene5); 
        primaryStage.setTitle("Menu");
        });
        btnacc.setOnAction((ActionEvent event) -> {
        primaryStage.setScene(scene8); 
        primaryStage.setTitle("Account management");
        });
        btnf.setOnAction((ActionEvent event) -> {
        primaryStage.setScene(scene3); 
        primaryStage.setTitle("Feedback");
        });
        //scene5
        GridPane p57 = new GridPane();
        p57.setPadding(new Insets(11.5,12.5,13.5,14.5));
        p57.setHgap(35);
        p57.setVgap(8);
        Button btnb = new Button ("Back");
        Button btnch = new Button ("Checkout");
        Button btnp = new Button ("Add to cart");
        CheckBox cb1 = new CheckBox("Shrimp 15$");
        CheckBox cb2 = new CheckBox("Chicken creme 12$");
        CheckBox cb3 = new CheckBox("Tomato 10$");
        CheckBox cb4 = new CheckBox("T-steak 25$");
        CheckBox cb5 = new CheckBox("Alfredo 12$");
        CheckBox cb6 = new CheckBox("Fish and chips 16$");
        CheckBox cb7 = new CheckBox("lazagnia 10$");
        CheckBox cb8 = new CheckBox("fruit salad 20$");
        CheckBox cb9 = new CheckBox("ice cream 12$");
        CheckBox cb10 = new CheckBox("Cola 5$");
        CheckBox cb11 = new CheckBox("Coffee 5$");
        CheckBox cb12= new CheckBox("Tea 5$");
        CheckBox cb13= new CheckBox("fries 7$");
        CheckBox cb14= new CheckBox("Garlic bread 9$");
        Label sp = new Label("Soups");
        sp.setFont(new Font(30));
        p57.add(sp, 0, 0);
        p57.add(cb1, 0, 1);
        p57.add(cb2, 0, 2);
        p57.add(cb3, 0, 3);
        Label md = new Label("Main dishes");
        md.setFont(new Font(30));
        p57.add(md, 3, 0);
        p57.add(cb4, 3, 1);
        p57.add(cb5, 3, 2);
        p57.add(cb6, 3, 3);
        p57.add(cb7, 3, 4);
        Label sw = new Label("Sweets");
        sw.setFont(new Font(30));
        p57.add(sw, 0, 6);
        p57.add(cb8, 0, 7);
        p57.add(cb9, 0, 8);
        Label dr = new Label("Drinks");
        dr.setFont(new Font(30));
        p57.add(dr, 3, 6);
        p57.add(cb10, 3, 7);
        p57.add(cb11, 3, 8);
        p57.add(cb12, 3, 9);
        Label ap = new Label("Appatizers");
        ap.setFont(new Font(30));
        p57.add(ap, 0, 10);
        p57.add(cb13, 0, 11);
        p57.add(cb14, 0, 12);
        p57.add(btnp, 3, 13);
        p57.add(btnch, 2, 13);
        p57.add(btnb,1,13);
        btnp.setOnAction((ActionEvent event) -> {
            try{
            Socket socket = new Socket("localhost", 5050);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("make order");
            //String code="";
            if(cb1.isSelected())
                code+="'s2' ,";
            if(cb2.isSelected())
                code+="'s1',";
            if(cb3.isSelected())
                code+="'s3',";
            if(cb4.isSelected())
               code+="'m3',";
            if(cb5.isSelected())
                code+="'m1',";
            if(cb6.isSelected())
                code+="'m2',";
            if(cb7.isSelected())
                code+="'m4',";
            if(cb8.isSelected())
                code+="'sw1',";
            if(cb9.isSelected())
                code+="'sw2',";
            if(cb10.isSelected())
                code+="'d2',";
            if(cb11.isSelected())
                code+="'d1',";
            if(cb12.isSelected())
                code+="'d3',";
            if(cb13.isSelected())
               code+="'a1',";
            if(cb14.isSelected())
                code+="'a2',";
                dos.writeUTF(code.substring(0,(code.length())-1));
                dos.writeUTF(tx1.getText());
                tprice= dis.read();
                item=dis.readUTF();
       JOptionPane.showMessageDialog(null,"You have choosen \n"+item+"the amount to pay = "+tprice+"$");
        dos.flush();
        dos.close();
            }catch(IOException e){ 
            e.printStackTrace(); 
        }
        });
        btnch.setOnAction((ActionEvent event) -> {
         primaryStage.setScene(scene11); 
        primaryStage.setTitle("Cafetria Cart");
        });
         btnb.setOnAction((ActionEvent event) -> {
         primaryStage.setScene(scene4); 
        primaryStage.setTitle("Customer account");
        });
        System.out.println(code);
        scene5= new Scene(p57);
        //scene6
        Button btnr = new Button("Reports");
        Button btnads = new Button("Add Salesman");
        Button btnds = new Button("Delete Salesman");
        Button btnui = new Button("Update inventory");
        Button btnum = new Button("Update Menu"); 
        BorderPane.setAlignment(n2,Pos.BOTTOM_CENTER);
        BorderPane borderPane2 = new BorderPane();
        HBox paneb1 = new HBox();
        Label n3 = new Label("Welcome !");
        borderPane2.setTop(n3);
        final double MAX_FONT_SIZE1 = 30.0; 
        n3.setFont(new Font(MAX_FONT_SIZE1)); 
        borderPane2.setCenter(paneb1);
        BorderPane.setAlignment(paneb1,Pos.CENTER);
        paneb1.getChildren().add(btnr);
        paneb1.getChildren().add(btnads);
        paneb1.getChildren().add(btnds);
        paneb1.getChildren().add(btnui);
        paneb1.getChildren().add(btnum);
        paneb1.setSpacing(40);
        paneb1.setAlignment(Pos.CENTER);
        btnds.setOnAction((ActionEvent event) -> {
        primaryStage.setScene(scene9); 
        primaryStage.setTitle("Delete an account");
        });
        btnads.setOnAction((ActionEvent event) -> {
        primaryStage.setScene(scene10); 
        primaryStage.setTitle("ADD an account");
        });
        btnum.setOnAction((ActionEvent event) -> {
        primaryStage.setScene(scene12); 
        primaryStage.setTitle("Update Menu");
        });
        scene6= new Scene(borderPane2, 800, 300);
        //scene7
        Button btnco = new Button("Create Order");
        Button btnpi = new Button("Print invoice");
        Button btnac = new Button("Account mangement");
        BorderPane.setAlignment(n2,Pos.BOTTOM_CENTER);
        BorderPane borderPane3 = new BorderPane();
        HBox paneb2 = new HBox();
        Label n4 = new Label("Welcome !");
        borderPane2.setTop(n4);
        final double MAX_FONT_SIZE2 = 30.0; 
        n3.setFont(new Font(MAX_FONT_SIZE2)); 
        borderPane3.setCenter(paneb2);
        BorderPane.setAlignment(paneb2,Pos.CENTER);
        paneb2.getChildren().add(btnco);
        paneb2.getChildren().add(btnpi);
        paneb2.getChildren().add(btnac);
        paneb2.setSpacing(40);
        paneb2.setAlignment(Pos.CENTER);
        scene7= new Scene(borderPane3, 500, 300);
        btnac.setOnAction((ActionEvent event) -> {
        primaryStage.setScene(scene8); 
        primaryStage.setTitle("Account management");
        });
         btnco.setOnAction((ActionEvent event) -> {
        primaryStage.setScene(scene5); 
        primaryStage.setTitle("Menu");
         });
         btnpi.setOnAction((ActionEvent event) -> {
            try {
                Socket socket = new Socket("localhost", 5050);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("print invoice");
         }  catch (IOException ex) {
                Logger.getLogger(JavaFXApplication1.class.getName()).log(Level.SEVERE, null, ex);
            }
         });
       //scene8
        GridPane pane4 = new GridPane();
        TextField tx5=new TextField();
        TextField tx6=new TextField();
        TextField tx7=new TextField();
        pane4.setPadding(new Insets(11.5,12.5,13.5,14.5));
        pane4.setHgap(5.5);
        pane4.setVgap(5.5);
        pane4.add(new Label("Name:"),0,1);
        pane4.add(tx5,1,1);
        pane4.add(new Label(" Enter your User Name:"),0,0);
        pane4.add(tx6,1,0);
        pane4.add(new Label("Email:"),0,2);
        pane4.add(tx7,1,2);
        Button btnup = new Button("Update");
        pane4.add(btnup, 1, 7);
        Button btnbck2 = new Button("Back");
        pane4.add(btnbck2, 0, 7);
         String st6[] = { "Manager", "Sales Man", "Customer"}; 
        ChoiceBox c3 = new ChoiceBox(FXCollections.observableArrayList(st6));
        pane4.add(c3, 0, 5);
      Label l3 = new Label("nothing selected"); 
        c3.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {  
            @Override
            public void changed(ObservableValue ov, Number value, Number new_value) 
            { 
                l3.setText(st6[new_value.intValue()] + " selected"); 
            } 
        }); 
        GridPane.setHalignment(btnup, HPos.RIGHT);
        GridPane.setHalignment(btnbck2, HPos.LEFT);
      scene8=new Scene(pane4);
       btnbck2.setOnAction((ActionEvent event) ->{
            if(c3.getValue().equals("customer")){
            primaryStage.setScene(scene4);
                primaryStage.setTitle("Customer account");}
                else if(c3.getValue().equals("manager")){
            primaryStage.setScene(scene6);
                primaryStage.setTitle("Manager account");}
                else{primaryStage.setScene(scene7);
                primaryStage.setTitle("Salesman account");}
        });
        btnup.setOnAction((ActionEvent event) ->{
           try{
             Socket socket = new Socket("localhost", 5050);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("update info");
           dos.writeUTF(tx6.getText());
           dos.writeUTF(tx5.getText());
           dos.writeUTF(tx7.getText());
           dos.writeUTF((String) c3.getValue());
           dos.flush(); 
           dos.close();
            } catch(IOException e){ 
            e.printStackTrace(); 
        }  
        });
        //scene 9
        GridPane p55= new GridPane();
        Label l7 = new Label("Enter the username of the salesman you want to delete:");
        TextField t1 = new TextField();
        Button btndl = new Button("DELETE");
         p55.setPadding(new Insets(11.5,12.5,13.5,14.5));
         p55.setHgap(5.5);
         p55.setVgap(5.5);
         p55.add(l7, 0, 0);
         p55.add(t1, 1, 0);
         p55.add(btndl, 0, 2);
        scene9=new Scene(p55);
        btndl.setOnAction((ActionEvent event) ->{
           try{
             Socket socket = new Socket("localhost", 5050);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("delete sm");
           dos.writeUTF(t1.getText());
           dos.flush(); 
           dos.close();
            } catch(IOException e){ 
            e.printStackTrace(); 
        }  
        });
        //scene10
        GridPane p56 = new GridPane();
        p56.setPadding(new Insets(11.5,12.5,13.5,14.5));
        p56.setHgap(5.5);
        p56.setVgap(5.5);
        TextField t2=new TextField();
        TextField t3=new TextField();
        TextField t4=new TextField();
        PasswordField pw2= new PasswordField();
        p56.add(new Label("Name:"),0,0);
        p56.add(t2,1,0);
        p56.add(new Label("User Name:"),0,1);
        p56.add(t3,1,1);
        p56.add(new Label("Email:"),0,2);
        p56.add(t4,1,2);
        p56.add(new Label("Password:"),0,3);
        p56.add(pw2,1,3);
        p56.add(new Label("retype password:"),0,4);
        p56.add(new PasswordField(),1,4);
        Button btnadd = new Button("ADD");
        p56.add(btnadd, 1, 8);
        Button btnbck3 = new Button("Back");
        p56.add(btnbck3, 0, 8);
        GridPane.setHalignment(btnadd, HPos.RIGHT);
        GridPane.setHalignment(btnbck3, HPos.LEFT);    
        btnbck3.setOnAction((ActionEvent event) -> {
        primaryStage.setScene(scene6); 
        primaryStage.setTitle("Manager account");
        });
        btnadd.setOnAction((ActionEvent event) -> {
        try {
            Socket socket = new Socket("localhost", 5050);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("add sm");
           dos.writeUTF(t2.getText());
           dos.writeUTF(t3.getText());
           dos.writeUTF(t4.getText());
           dos.writeUTF(pw2.getText());
           dos.flush(); 
           dos.close();
            } catch(IOException e){ 
            e.printStackTrace(); 
        } 
        });
        scene10=new Scene (p56);
        //scene11
        BorderPane p70= new BorderPane();
         HBox paneb3 = new HBox();
       Button btnpay1 = new Button("pay cash");
       Button btnpay = new Button("pay by balance");
       Button bck = new Button("back");
       paneb3.getChildren().add(btnpay);
        paneb3.getChildren().add(btnpay1);
        paneb3.getChildren().add(bck);
        paneb3.setSpacing(40);
        paneb3.setAlignment(Pos.CENTER);
        p70.setCenter(paneb3);
        scene11=new Scene (p70,500 , 500);
        bck.setOnAction((ActionEvent event) -> {
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene5);
        });
        btnpay1.setOnAction((ActionEvent event) -> {
           try{
             Socket socket = new Socket("localhost", 5050);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("pay by cash");
           dos.writeUTF(tx1.getText());
           dos.flush(); 
           dos.close();
            } catch(IOException e){ 
            e.printStackTrace(); 
        }   
        JOptionPane.showMessageDialog(null, "Order sumbitted thanks for using our services");
        });
        btnpay.setOnAction((ActionEvent event) -> {
            try{
             Socket socket = new Socket("localhost", 5050);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("pay by balance");
           dos.writeUTF(tx1.getText());
           dos.flush(); 
           dos.close();
            } catch(IOException e){ 
            e.printStackTrace(); 
        }   
        JOptionPane.showMessageDialog(null, "Order sumbitted thanks for using our services");
        });
        //scene12
        GridPane p71= new GridPane();
        Label l8 = new Label("Enter the code of the menu item:");
        TextField t8 = new TextField();
        Label l89 = new Label("if you will update choose the new price:");
        TextField t89 = new TextField();
        
        Button btnd9 = new Button("DELETE");
        Button btnd8 = new Button("UPDATE");
         p71.setPadding(new Insets(11.5,12.5,13.5,14.5));
         p71.setHgap(5.5);
         p71.setVgap(5.5);
         p71.add(l8, 0, 0);
         p71.add(t8, 1, 0);
         p71.add(l89, 0, 2);
         p71.add(t89, 2, 0);
         p71.add(btnd9, 0, 3);
         p71.add(btnd8, 1, 3);
         
                 scene12=new Scene(p71);
        btnd9.setOnAction((ActionEvent event) ->{
           try{
             Socket socket = new Socket("localhost", 5050);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("delete m");
           dos.writeUTF(t8.getText());
           dos.flush(); 
           dos.close();
            } catch(IOException e){ 
            e.printStackTrace(); 
        }  
        });
        btnd8.setOnAction((ActionEvent event) ->{
           try{
             Socket socket = new Socket("localhost", 5050);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("update men");
           dos.writeUTF(t8.getText());
           dos.writeUTF(t89.getText());
           dos.flush(); 
           dos.close();
            } catch(IOException e){ 
            e.printStackTrace(); 
        }  
        });
        //stage
        primaryStage.setTitle("Log in");
        primaryStage.setScene(scene);
        primaryStage.show();
        }

    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        launch(args);
    }
}


