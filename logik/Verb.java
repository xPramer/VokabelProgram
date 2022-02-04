package logik;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Raphael Möller
 */
public class Verb extends Vokabel{
    
    private String naiForm, masuForm, baForm, befehlsform, willensform, teForm, taForm;
    
    public Verb(String wortD, String wortJ, String naiForm, String masuForm, String baForm, String Befehlsform, String Willensform, String teForm, String taForm, String kat1, String kat2, String kat3) throws Exception{
        super(wortJ, wortD, kat1, kat2, kat3);
        this.naiForm = naiForm;
        this.masuForm = masuForm;
        this.baForm = baForm;
        this.befehlsform = Befehlsform;
        this.willensform = Willensform;
        this.teForm = teForm;
        this.taForm = taForm;
        this.streak = 0;
    }
    
    public Verb(int id, int streak, String wortD, String wortJ, String naiForm, String masuForm, String baForm, String Befehlsform, String Willensform, String teForm, String taForm, String kategorien){
        super(id, wortJ, wortD, kategorien, streak);
        this.naiForm = naiForm;
        this.masuForm = masuForm;
        this.baForm = baForm;
        this.befehlsform = Befehlsform;
        this.willensform = Willensform;
        this.teForm = teForm;
        this.taForm = taForm;
    }
    
    public String getNaiForm(){
        return naiForm;
    }
    public String getMasuForm(){
        return masuForm;
    }
    public String getBaForm(){
        return baForm;
    }
    public String getBefehlsform(){
        return befehlsform;
    }
    public String getWillensform(){
        return willensform;
    }
    public String getTeForm(){
        return teForm;
    }
    public String getTaForm(){
        return taForm;
    }
}
