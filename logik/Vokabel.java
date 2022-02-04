package logik;


import java.util.ArrayList;


/**
 *
 * @author Raphael Moeller
 */
public class Vokabel {
    
    private static int neachsteID;
    protected String wortJ, wortD;
    protected int streak, id;
    MySQLAccess msa = new MySQLAccess();
    protected ArrayList<String> kategorien = new ArrayList<>();
    
    
    public Vokabel(String wortJ, String wortD, String kat1, String kat2, String kat3) throws Exception{
        setNeachsteID();
        this.wortJ = wortJ;
        this.wortD = wortD;
        kategorien.add(kat1);
        kategorien.add(kat2);
        kategorien.add(kat3);
        this.streak = 0;
        this.id = getNeachsteID();
    } 
    
    public Vokabel(int id, String wortJ, String wortD, String kategorien, int streak){
        this.id = id;
        this.wortJ = wortJ;
        this.wortD = wortD;
        this.kategorien.add(kategorien);
        this.streak = streak;
    } 
    
    public void addKategorie(String kat){
        kategorien.add(kat);
    }
    public boolean hasKategorie(String kat){
        for(int x = 0; x < kategorien.size(); x++){
            if(kat == kategorien.get(x)){
                return true;
            }
        }
        return false;
    }
    public String getWortJ(){
        return wortJ;
    }
    public String getWortD(){
        return wortD;
    }
    public int getStreak(){
        return streak;
    }
    public void resetStreak(){
        streak = 0;
    }
    public void incStreak(){
        streak++;
    }
    public void setNeachsteID() throws Exception{
        Vokabel.neachsteID = msa.getMaxID() +1;
    }
    public int getNeachsteID(){
        return neachsteID;
    }
    public int getID(){
        return id;
    }
    public String getKategorien(){
        String temp = "";
        for(int x = 0; x < kategorien.size(); x++){
            temp += kategorien.get(x) + ",";
        }
        return temp;
    }
    
}
