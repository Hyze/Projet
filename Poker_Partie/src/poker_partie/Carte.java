/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker_partie;

/**
 *
 * @author tomskiev
 */
public class Carte {
    
    final static String Tabcouleur[] = {"coeur","carreau","pique","trefle"};
    
    private int nombre;
    private String couleur;
    
    //sert de constructeur par defaut sans besoin que les attrib soit init
    Carte(){
        ;
    }
    
    Carte(int n,String c){
        setNombre(n);
        setCouleur(c);
    }
    //renvoie le num de la carte
    public int getNombre(){
        return nombre;
    }
    //renvoie le nom de la couleur de la carte
    public String getCouleur(){
        return this.couleur; 
    }
    
    //modifie la valeur du nombre de la carte
    public void setNombre(int n){
        if(n>=1 && n<=14 )  //pour le cas du un/as 
        this.nombre = n;
    }
    //modifie la couleur de la carte
    public void setCouleur(String c){
         this.couleur = c;
    }
    
    //redéfinition de la toString pour aff carte avec attrib
    public String toString(){
        String s= new String();
        if(this.nombre<=10){
            return ""+this.nombre+" de "+this.couleur;
        }
        else{
            switch(this.nombre){
                case 11:
                    s = "valet";
                break;
                case 12:
                    s = "reine";
                break;
                case 13:
                    s = "roi";
                break;
                case 14:
                    s = "as";
            }
            return ""+s+" de "+this.couleur;
        }
    }
    
    //renvoie couleur n du tab de couleur sinon null
    public String getTabCouleur(int n){
        if(n>=0 && n<4)
            return Tabcouleur[n];
        else
            return "null";
    }
}
