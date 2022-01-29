package logik;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

/**
 *
 * @author Raphael Möller
 */
public class DataManager {
    
    private int id;
    private ArrayList<Vokabel> vokabeln = new ArrayList<>();
    private String[] propData;
    private MySQLAccess msa = new MySQLAccess();
    
    public void laodDBData() throws Exception{
        vokabeln = msa.loadData();
    }
    
    public void saveDataToDB(){
        
    }
    
    public ArrayList<Vokabel> getDataVocs(){
        return vokabeln;
    }
    public void addVokabel(Vokabel voc) throws SQLException, ClassNotFoundException{
        vokabeln.add(voc);
        msa.addData(voc);
    }
    
    public int getAnzahlVokabeln(){
        return vokabeln.size();
    }
    
    public void setID(int id){
        this.id = id;
    }
    
    /*
    
    id1 => determines what kind of Content needs to be set (f.e. Kana, Kanji, Words, ... etc)
            -> 0 = Kana (Propteries)
            -> 1 = Kanji (Database)
            -> 2 = Words (Database)
    
    id2 => for Kana / Properties Data
    
    */
    public void setContent(int id1, int id2){
        
        String temp = "";
        vokabeln.clear();
        
        switch(id1){
            case 0:
                try(InputStream input = new FileInputStream("src/logik/data.properties")) {
            
                Properties prop = new Properties();
                prop.load(input);

                switch(id2){
                    case 10:
                        temp = prop.getProperty("hiraganaKlar").toString();
                        break;
                    case 11:
                        temp = prop.getProperty("hiraganaKlar") + prop.getProperty("hiraganaKlarGebr");
                        break;
                    case 12:
                        temp = prop.getProperty("hiraganaKlar") + prop.getProperty("hiraganaStimmhaft");
                        break;
                    case 13:
                        temp = prop.getProperty("hiraganaKlar") + prop.getProperty("hiraganaKlarGebr") + prop.getProperty("hiraganaStimmhaft") + prop.getProperty("hiraganaStimmhaftGebr");
                        break;
                    case 20:
                        temp = prop.getProperty("katakanaKlar");
                        break;
                    case 21:
                        temp = prop.getProperty("katakanaKlar") + prop.getProperty("katakanaKlarGebr");
                        break;
                    case 22:
                        temp = prop.getProperty("katakanaKlar") + prop.getProperty("katakanaStimmhaft");
                        break;
                    case 23:
                        temp = prop.getProperty("katakanaKlar") + prop.getProperty("katakanaKlarGebr") + prop.getProperty("katakanaStimmhaft") + prop.getProperty("katakanaStimmhaftGebr");
                        break;
                    case 30:
                        temp = prop.getProperty("hiraganaKlar") + prop.getProperty("katakanaKlar");
                        break;
                    case 31:
                        temp = prop.getProperty("hiraganaKlar") + prop.getProperty("hiraganaKlarGebr") + prop.getProperty("katakanaKlar") + prop.getProperty("katakanaKlarGebr");
                        break;
                    case 32:
                        temp = prop.getProperty("hiraganaKlar") + prop.getProperty("hiraganaStimmhaft") + prop.getProperty("katakanaKlar") + prop.getProperty("katakanaStimmhaft");
                        break;
                    case 33:
                        temp = prop.getProperty("hiraganaKlar").toString() + prop.getProperty("hiraganaKlarGebr").toString() + prop.getProperty("hiraganaStimmhaft") + prop.getProperty("hiraganaStimmhaftGebr") + prop.getProperty("katakanaKlar") + prop.getProperty("katakanaKlarGebr") + prop.getProperty("katakanaStimmhaft") + prop.getProperty("katakanaStimmhaftGebr");
                        break;
                }
                } catch(IOException io){
                    io.printStackTrace();
                }
                propData = temp.split(",");
            break;
        }
    }
    
    /*
    id => determines what kind of Content needs to be worked with (f.e. Kana, Kanji, Words, ... etc)
            -> 01 = Kana (Propteries) -> Kana to Romaji 
            -> 02 = Kana (Propteries) -> Romaji to Kana
            -> 03 = Kana (Properties) -> 3 combined
            -> 1 = Kanji (Database)
            -> 2 = Words (Database)
    */
    public String[] getRandomContent(int id){
        
        int rand_num1, rand_num2, rand_num3;
        Random rand = new Random();
        String[] kanaContent = new String[4];
        
        switch(id){
            case 01: 
                rand_num1 = rand.nextInt(propData.length -1);
                if(rand_num1 % 2 != 0){
                    rand_num1 -= 1;
                }
                
                kanaContent[0] = propData[rand_num1];
                kanaContent[1] = propData[rand_num1 + 1];
                
                    do{
                        rand_num2 = rand.nextInt(propData.length -1);
                        if(rand_num2 % 2 == 0){rand_num2 += 1;}
                    } while(rand_num2 - 1 == rand_num1);
                   
                    kanaContent[2] = propData[rand_num2];
                    
                    do{
                        rand_num3 = rand.nextInt(propData.length -1);
                        if(rand_num3 % 2 == 0){rand_num3 += 1;}
                    } while(rand_num3 - 1 == rand_num1 || rand_num3 == rand_num2);
                   
                    kanaContent[3] = propData[rand_num3];
                    return kanaContent;
            case 02: 
                rand_num1 = rand.nextInt(propData.length -1);
                if(rand_num1 % 2 == 0){
                    rand_num1 += 1;
                }
                
                kanaContent[0] = propData[rand_num1];
                kanaContent[1] = propData[rand_num1 - 1];
                
                    do{
                        rand_num2 = rand.nextInt(propData.length -1);
                        if(rand_num2 % 2 != 0){rand_num2 -= 1;}
                    } while(rand_num2 + 1 == rand_num1);
                   
                    kanaContent[2] = propData[rand_num2];
                    
                    do{
                        rand_num3 = rand.nextInt(propData.length -1);
                        if(rand_num3 % 2 != 0){rand_num3 -= 1;}
                    } while(rand_num3 + 1 == rand_num1 || rand_num3 == rand_num2);
                   
                    kanaContent[3] = propData[rand_num3];
                    return kanaContent;
            case 03:
                String[] temp2 = new String[2];
                
                    rand_num1 = rand.nextInt(propData.length -1);
                    if(rand_num1 % 2 != 0){
                        rand_num1 -= 1;
                    }
                    temp2[0] = propData[rand_num1];
                    temp2[1] = propData[rand_num1 + 1];
                    
                for(int x = 0; x < 2; x++){
                    rand_num1 = rand.nextInt(propData.length -1);
                    if(rand_num1 % 2 != 0){
                        rand_num1 -= 1;
                    }
                    temp2[0] += propData[rand_num1];
                    temp2[1] += propData[rand_num1 + 1];
                }
                return temp2;
        }
        String[] temp = new String[0];
        return temp;
        
    }
    
   
}
