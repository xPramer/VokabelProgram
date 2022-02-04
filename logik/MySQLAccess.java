package logik;

/**
 *
 * @author Raphael Möller
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class MySQLAccess {
    
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    String user, psw;
    
    public void getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        user = "VoVerwaltung";
        psw = "1234";
        connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=CONVERT_TO_NULL", user, psw);
    }
    
    public ResultSet readDataBase(String tabelle) throws Exception {
        try {
            getConnection();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from vokabelprogramm." + tabelle);
            
            return resultSet;
            
        } catch (Exception e) {
            throw e;
        } finally {
           
        }
    }
    
    public int getMaxID() throws Exception {
        getConnection();
        statement = connect.createStatement();
        resultSet = statement.executeQuery("select MAX(id) AS Max_Id from vokabelprogramm.vokabeln");
        
        resultSet.next();
        return resultSet.getInt("Max_Id");
    }
    
    public ArrayList<Vokabel> loadData() throws ClassNotFoundException, SQLException, Exception{
        
        getConnection();
        ArrayList<Vokabel> vokabeln = new ArrayList<>();
        
        ResultSet rs1 = readDataBase("vokabeln");
        
        while(rs1.next()){
            if(rs1.getString("kategorien").contains("Verb")){
                vokabeln.add(new Verb(rs1.getInt("id"), rs1.getInt("streak"), rs1.getString("wortD"), rs1.getString("wortJ"), rs1.getString("naiForm"), rs1.getString("masuForm"), rs1.getString("baForm"), rs1.getString("befehlsform"), rs1.getString("willensform"), rs1.getString("teForm"), rs1.getString("taForm"), rs1.getString("kategorien")));
            }
            else {
                vokabeln.add(new Vokabel(rs1.getInt("id"), rs1.getString("wortJ"), rs1.getString("wortD"), rs1.getString("kategorien"), rs1.getInt("streak")));
            }
        }
        return vokabeln;
    }
    
    public void addData(Vokabel vok) throws SQLException, ClassNotFoundException{
        
        getConnection();
        preparedStatement = connect.prepareStatement("insert into vokabelprogramm.vokabeln values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, vok.getID());
        preparedStatement.setString(2, vok.getWortD());
        preparedStatement.setString(3, vok.getWortJ());
        preparedStatement.setInt(4, vok.getStreak());
        preparedStatement.setString(5, vok.getKategorien());
        
        if(vok instanceof Verb){
            preparedStatement.setString(6, ((Verb) vok).getNaiForm());
            preparedStatement.setString(7, ((Verb) vok).getMasuForm());
            preparedStatement.setString(8, ((Verb) vok).getBaForm());
            preparedStatement.setString(9, ((Verb) vok).getBefehlsform());
            preparedStatement.setString(10, ((Verb) vok).getWillensform());
            preparedStatement.setString(11, ((Verb) vok).getTeForm());
            preparedStatement.setString(12, ((Verb) vok).getTaForm());
        } else {
            preparedStatement.setString(6, "");
            preparedStatement.setString(7, "");
            preparedStatement.setString(8, "");
            preparedStatement.setString(9, "");
            preparedStatement.setString(10, "");
            preparedStatement.setString(11, "");
            preparedStatement.setString(12, "");
        }
        preparedStatement.executeUpdate();
    }
    
    public void updateData(Vokabel voc) throws SQLException, ClassNotFoundException{
        getConnection();
        preparedStatement = connect.prepareStatement("UPDATE vokabelprogramm.vokabeln SET streak = ? WHERE id = ?;");
        preparedStatement.setInt(1, voc.getStreak());
        preparedStatement.setInt(2, voc.getID());
        preparedStatement.executeUpdate();
    }
    
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
        }
    }
}
