/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication19;

/**
 *
 * @author Yehia
 */
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Yehia
 */
public class Server  
{ 
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException  
    { 
        ServerSocket ss = new ServerSocket(5050);   
         while (true)  
        { 
            Socket s = null; 
        try 
            { 
                // socket object to receive incoming client requests 
                s = ss.accept(); 
                System.out.println("A new client is connected : " + s);  
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
                String msg =dis.readUTF();
                System.out.println("Assigning new thread for this client"); 
                Thread t = new ClientHandler(s, dis, dos,msg); 
                t.start(); 
                  
            } 
            catch (Exception e){ 
                s.close(); 
                e.printStackTrace(); 
            } 
        } 
    } 
}
class ClientHandler extends Thread  
{ 
    String msg;
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s; 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos ,String msg)  
    { 
        this.s = s; 
        this.dis = dis; 
        this.dos = dos;
        this.msg=msg;
    } 
  
    @Override
    public void run()  
    { 
        try{
        Class.forName("com.mysql.jdbc.Driver");  
        Connection con=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Cafetria","root","yehia123");  
         Statement statement =con.createStatement();
    ResultSet resultSet;
    try{
        if(msg.equals("log in")){
        String Username ;
        String password ;       
                Username = dis.readUTF(); 
                password= dis.readUTF();
                String role=dis.readUTF();
            try{
                String query = ("SELECT password  FROM `"+role+"` where username= ?");
     PreparedStatement preparedStmt = con.prepareStatement(query);
     preparedStmt.setString(1, Username);
     ResultSet results = preparedStmt.executeQuery();  
                System.out.println(results);
     if(results.next()){
       String x = results.getString("password");
     if (password.equals(x))
     {
      dos.write(1);   
     }
     else dos.write(2);
     }
     else dos.write(2);
     
            }catch(SQLException sql){
                System.out.println(sql);
            }
            finally{
             con.close();
   
            }
        }
        else if(msg.equals("register"))
        {     
              String Name=dis.readUTF();
              String Username= dis.readUTF(); 
              String Email=dis.readUTF();
              String password= dis.readUTF();
              String role = dis.readUTF();
        String query = " insert into `"+role+"`(username,email,password,Name)"
        + " values (?, ?, ?, ?)";
      PreparedStatement preparedStmt = con.prepareStatement(query);
      preparedStmt.setString (1, Username);
      preparedStmt.setString (2, Email);
      preparedStmt.setString (3, password);
      preparedStmt.setString (4, Name);
       preparedStmt.execute();
       System.out.println("Reg successful");
       con.close();
        }     
        else if(msg.equals("update info"))
        {      
            String Username= dis.readUTF();
              String Name=dis.readUTF(); 
              String Email=dis.readUTF();
              String role= dis.readUTF();
              String query = "update `"+role+"` set Name = ?  where username = ? ;";
              String query2 = "update `"+role+"` set Email = ?  where username = ?";
              PreparedStatement preparedStmt = con.prepareStatement(query);
              PreparedStatement preparedStmt2 = con.prepareStatement(query2);
              preparedStmt.setString (1,Name);
      preparedStmt.setString(2, Username);
     preparedStmt.executeUpdate();  
      preparedStmt2.setString (1,Email);
      preparedStmt2.setString(2, Username);
     preparedStmt2.executeUpdate();  
     System.out.println("Updated");
     con.close();
            } 
         else if(msg.equals("add sm"))
        {     
              String Name=dis.readUTF();
              String Username= dis.readUTF(); 
              String Email=dis.readUTF();
              String password= dis.readUTF();
        String query = " insert into salesman (username,email,password,Name)"
        + " values (?, ?, ?, ?)";
      PreparedStatement preparedStmt = con.prepareStatement(query);
      preparedStmt.setString (1, Username);
      preparedStmt.setString (2, Email);
      preparedStmt.setString (3, password);
      preparedStmt.setString (4, Name);
       preparedStmt.execute();
       System.out.println("adding successful");
       con.close();
            } 
            else if(msg.equals("delete sm"))
        {
              String username=dis.readUTF();
              String query = "delete from salesman where username= ?";
      PreparedStatement preparedStmt = con.prepareStatement(query);
      preparedStmt.setString(1,username);
      preparedStmt.execute();
      con.close();
            }
            else if(msg.equals("delete m"))
        {      
              String itemcode=dis.readUTF();
              String query = "delete from menu where itemcode= ?";
      PreparedStatement preparedStmt = con.prepareStatement(query);
      preparedStmt.setString(1,itemcode);
      preparedStmt.execute();
      con.close();
            }
        else if(msg.equals("update men"))
        {     
            String itemcode=dis.readUTF();
            String price=dis.readUTF();
             String query = "update menu set price =? where itemcode= ?";
      PreparedStatement preparedStmt = con.prepareStatement(query);
      preparedStmt.setString(1,price);
      preparedStmt.setString(2,itemcode);
      preparedStmt.execute();
      con.close();
            } 
        else if(msg.equals("make order"))
        {     
           int tprice = 0;
            String x = "";
           String itemcode=dis.readUTF();
           String query = "select sum(price) as total from menu where itemcode in("+itemcode+")";
      PreparedStatement preparedStmt = con.prepareStatement(query);
     ResultSet results = preparedStmt.executeQuery();
     String query2 = "select item from menu where itemcode in("+itemcode+")";
      PreparedStatement preparedStmt2 = con.prepareStatement(query2);
     ResultSet results2 = preparedStmt2.executeQuery();
      if(results.next()){
              tprice=results.getInt("total");  
      }
      while(results2.next()){
          x+=results2.getString("item")+"\n";  
      }
      dos.write(tprice); 
      dos.writeUTF(x);
      int y = (int) ((Math.random()*((999-1)+1))+1);
      String user=dis.readUTF();   
      
        String query3=" insert into orders(status, orderno, order_items, cost, username)" +
        "values (?,?,?,?,?)";

      PreparedStatement preparedStmt3 = con.prepareStatement(query3);
      preparedStmt3.setString(2,Integer.toString(y));
      preparedStmt3.setString(3,x.replaceAll("[\n]", " "));
      preparedStmt3.setString(4,Integer.toString(tprice));
      preparedStmt3.setString(5,user);
      preparedStmt3.setString(1,"to be deliverd");
      preparedStmt3.execute(); 
       con.close();
        }
        else if(msg.equals("Feedback"))
        {     
            String user=dis.readUTF();
            String feed=dis.readUTF();
            String query = "update customer set feedback =? where username= ?";
      PreparedStatement preparedStmt = con.prepareStatement(query);
      preparedStmt.setString(1,feed);
      preparedStmt.setString(2,user);
      preparedStmt.execute();
      con.close();
        }
         else if(msg.equals("pay by cash"))
        {
              String username=dis.readUTF();
              String query = "Update orders set paying=? where username= ?";
      PreparedStatement preparedStmt = con.prepareStatement(query);
      preparedStmt.setString(1,"Cash");
      preparedStmt.setString(2,username);
      preparedStmt.execute();
      con.close();
            }
        
         else if(msg.equals("pay by balance"))
        {
              String username=dis.readUTF();
              String query = "Update orders set paying=? where username= ?";
      PreparedStatement preparedStmt = con.prepareStatement(query);
      preparedStmt.setString(1,"balance");
      preparedStmt.setString(2,username);
      preparedStmt.execute();
      con.close();
            }
         else if(msg.equals("print invoice"))
        {
      String query = "Select * From orders";
      PreparedStatement preparedStmt = con.prepareStatement(query);
      ResultSet r =  preparedStmt.executeQuery();
    BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/yehia/Desktop/Order.txt"));
     String file="-------------------------------------------";  
    while(r.next())
      {
          writer.write(file);
          writer.newLine();
    String x1 = "Order Status: "+r.getString("status"); 
    String x2 = "order no. "+r.getString("orderno"); 
    String x3 = "The ordered items: "+r.getString("order_items");
    String x4 = "The order cost: "+r.getString("cost");
    String x5 = "Ordered by: "+r.getString("username");
    String x6 ="Paying through: "+r.getString("paying");
    writer.write(x1);
    writer.newLine();
    writer.write(x2);
    writer.newLine();
    writer.write(x3);
    writer.newLine();
    writer.write(x4);
    writer.newLine();
    writer.write(x5);
    writer.newLine();
    writer.write(x6);
    writer.newLine();
      }
       writer.close();
      con.close();
        }
         
        }catch (SQLException ex) { 
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
}catch (IOException e) { 
                e.printStackTrace(); 
            }    


               try
        { 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    }   catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

