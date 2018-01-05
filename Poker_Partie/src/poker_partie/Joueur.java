/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker_partie;

import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author tomskiev
 */
public class Joueur extends Player {
    
    
    Joueur(String n){
        super();
        this.nom = n;
    }
    
    //proposition de pari par leu joueur
    public int proposition(int n,Cartes tapis,Partie p){
        int nb=0;
        boolean continuer = true;
        String s;
        System.out.println("Tour de "+this.nom);
        while(continuer){
         nb = Integer.parseInt(JOptionPane.showInputDialog(null, "Les paris sont a "+n+"\nQues proposez vous ?\nAide : -1 pour se coucher", this.getNom(), JOptionPane.QUESTION_MESSAGE));
            if((nb >= n && nb <= this.argent) || nb == -1){ //-1 pour se coucher
                continuer = false;
            }
            else{
                JOptionPane.showMessageDialog(null,"La somme doit etre superieur ou egal a "+n+" et ne doit pas dépasser votre argent.","Paris",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        this.mise = nb;
        return nb;
        
    }
    
    public boolean estJoueur(){
        return true;
    }
    public boolean estIA(){
        return false;
    }
}
