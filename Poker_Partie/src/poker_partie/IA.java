/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker_partie;

import java.util.ArrayList;

/**
 *
 * @author tomskiev
 */
public class IA extends Player {
    final static String tabnom[] = {"johnny","arthur","paul","joe"};
    private double prob = 0;
    
    final static int GROSSERELANCE = 70;
    final static int PETITERELANCE = 40; 
    
    //constructeur
    IA(ArrayList <Player> nom){
        super();
        String tmp;
        tmp = Choosenom();
        //pour des nom differents des joueurs présent et on pourra verif plus tard les doublons entre IA
        for(int i=0;i<nom.size();i++){
            if(nom.get(i).getNom().equals(tmp)){
                this.nom = tmp+alea(4,100);
            }
        }
        if(this.nom == null)
            this.nom = tmp;
        
        System.out.println("L'ia s'appelle "+this.nom);
        
    }
    
    //proposition lors des paris de l'ia
    public int proposition(int n, Cartes tapis,Partie p){
        boolean b = true;
        calculeProba(tapis);
        b = choix(n);
        if(b){
            if(this.mise > this.argent)
                this.mise = this.argent;
            return this.mise;
        }
        else{
            return -1;
        }
    }

    //calcule des chances de gagner pour l'ia
    public void calculeProba(Cartes tapis){

        Cartes CarteAComparer= new Cartes();
        for(int i = 0;i<this.getTailleMain(); i++) {
            CarteAComparer.addCarte(this.getCarteMain(i));
        }
        for (int i=0;i<tapis.getTaille();i++)
        {
            CarteAComparer.addCarte(tapis.getCarte(i));
        }



        if(CarteAComparer.getTaille() >5) { // on commence par verifier les proche pour avoir un prob de base
            if (CarteAComparer.ProchePaire()) {
                prob += 20;
            }
            if (CarteAComparer.ProcheDoublePaire()) {
                prob += 10;
            }
            if (CarteAComparer.ProcheBrelan()) {
                prob += 15;
            }
            if (CarteAComparer.ProcheSuite()) {
                prob += 20;
            }
            if (CarteAComparer.ProcheCouleur3()) {
                prob += 15;
            }
            if (CarteAComparer.ProcheCouleur4()) {
                prob += 25;
            }
            if (CarteAComparer.ProcheFull()) {
                prob += 7;

            }
            if (CarteAComparer.ProcheCarre()) {
                prob += 5;
            }
            if (CarteAComparer.ProcheQuinteFlush()) {
                prob += 2;
            }
            if (CarteAComparer.ProcheQuinteFlushRoyale()) {
                prob += 1;
            }

            // prob prend la valeur la plus basse au debut et verifie si on a mieux pour prendre une proba plus haute.
            if (CarteAComparer.Paire()) {
                if (CarteAComparer.PaireChiffre() < 10 && CarteAComparer.PaireChiffre() >= 6) {
                    prob = 70;
                }
                if (CarteAComparer.PaireChiffre() >= 10) {
                    prob = 80;
                } else {
                    prob = 60;
                }
            }
            if (CarteAComparer.Paire()) {
                prob = 70;
            }
            if (CarteAComparer.DoublePaire()) {
                prob = 75;
            }
            if (CarteAComparer.Brelan()) {
                prob = 80;
            }

            if (CarteAComparer.Couleur()) {
                prob = 80;
            }
            if (CarteAComparer.Suite()) {
                prob = 90;
            }
            if (CarteAComparer.Full()) {
                prob = 95;
            }
            if (CarteAComparer.Carré()) {
                prob = 100;
            }
            if (CarteAComparer.QuinteFlush()) {
                prob = 100;
            }
            if (CarteAComparer.QuinteFlushRoyal()) {
                prob = 100;
            }

        }
        if(CarteAComparer.getTaille()==2){  // au premier tour lorsque l'on a que 2 cartes en main , donc proba plus haut impossible
            prob =90;
        }
        if(CarteAComparer.getTaille()==5 && prob<50)
        {
            prob=70;
        }
        System.out.println("Calcul des chances de gagner avec actualisation des chances par combinaison");
    }
    
    //choix de mise et de relance 
    public boolean choix( int miseMax) {
        boolean Choix = false;
        double Decision = this.prob;
        double alea = alea(0, 100);
        double mid = prob/2;
       if(this.mise<=this.argent)
       {
        
        if (Decision == alea) {
            int unSurDeux = alea(0, 1);
            if (unSurDeux == 1) {
                mise = relance();           // l'ia Relance
                Choix= true;
            }
        } else
            {
            if (mid <= alea && alea<Decision &&Decision <= 100) { // l'ia suit la mise en cours
                mise= miseMax;
                Choix=true;
            }
                if(alea<mid && Decision<=100) // IA relance
                {
                    mise = relance();
                    Choix = true ;
                }


            }
       }
       else
       {
           Choix=false;
       }
        

        return Choix;

    }

   //type de relance 
   public int relance ()
    {
        int relance =0;
        if(this.argent>0)
        {
        int alea=alea(1,3);
        if(prob>85)
        {
            if(alea==1 || alea==2)
            {
                relance = GROSSERELANCE ;
          
            }
            else
            {
                relance= PETITERELANCE;
            }
        }
        else
        {
            if(alea==1 || alea==2)
            {
                relance = PETITERELANCE ;
          
            }
            else
            {
                relance= GROSSERELANCE;
            }
        }
        
        
       }
        
        return relance ; 
    }

    //taille main
    public int getTailleMain(){
        return this.main.getTaille();
    }
    
    //valeur d'une carte a indice i de la main
    public Carte getCarteMain(int i){
        if(i>=0 && i<2)
        return main.getCarte(i);
        else
        return null;
    }

    //choix du nom de l'ia
    public String Choosenom(){
        return tabnom[alea(0,3)];
    }
    
    public boolean estJoueur(){
        return false;
    }
    
    public boolean estIA(){
        return true;
    }
}
