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

public class Cartes {
    private ArrayList <Carte> tab;
    
    //constructeur de Cartes
    Cartes(){
        tab = new ArrayList();
    }
    
    //retourne la carte a l'indice n
    public Carte getCarte(int n){
        if(n<tab.size() && n>=0)
        return this.tab.get(n);
        else
            return null;
    }
    //renvoies copie le paquet de cartes
    public Cartes copie(){
        Cartes tmp = new Cartes();
        for(int i=0;i<this.tab.size();i++){
            tmp.addCarte(new Carte(this.getCarte(i).getNombre(),this.getCarte(i).getCouleur()));
        }
        return tmp;
    }
    
    //supp carte et la renvoie
    public Carte remove(int n){
        if(n>=0 && n<this.tab.size())
            return this.tab.remove(n);
        else
            return null;
    }
    
    //retourne l'arrayList de carte
    public ArrayList <Carte> getCartes(){
        return this.tab;
    }
    
    //ajoute une cartes
    public void addCarte(Carte c){
        this.tab.add(c);
    }
    
    //retourne le nombre de cartes
    public int getTaille(){
        return this.tab.size();
    }
    
    //retourne le nombre de la carte la plus haute du paquet de cartes
    public int CarteHaute(){
        int max = 0;
        int indice = 0;
        for(int i=0;i<this.getTaille();i++){
            if(max < this.getCarte(i).getNombre()){
                max = this.getCarte(i).getNombre();
                indice = i;
            }
        }
        return max;
    }
    
    //retourne la carte Haute qui a la bonne couleur
    public int CarteHauteCouleur(){
        String c = this.CouleurNom();
        int max = 0;
        for(int i=0;i<this.getTaille();i++){
            if(max < this.getCarte(i).getNombre() && this.getCarte(i).getCouleur() == c)
                max = this.getCarte(i).getNombre();
        }
        return max;
    }
    
    //retourne s'il y a une couleur ou non
    public boolean Couleur(){
        boolean continuer =true;
        boolean couleur = false;
        int j = 0;
        int tab[] = {0,0,0,0};
        for(int i=0;i<this.tab.size();i++){
            switch(this.tab.get(i).getCouleur()){
                case "coeur":
                    tab[0]++;
                break;
                
                case "carreau":
                    tab[1]++;
                break;
                
                case "pique":
                    tab[2]++;
                break;
                
                case "trefle":
                    tab[3]++;
                break;
            }
        }
        while(j<4 && continuer){
            if(tab[j] >= 5){
                couleur = true;
                continuer = false;
            }
            j++;
        }
        return couleur;
    }
    
    //retourne le nom de la couleur s'il y a une couleur
    public String CouleurNom(){
        boolean continuer =true;
        boolean couleur = false;
        int j = 0;
        String s="";
        int indice = -1;
        int tab[] = {0,0,0,0};
        for(int i=0;i<this.tab.size();i++){
            switch(this.tab.get(i).getCouleur()){
                case "coeur":
                    tab[0]++;
                break;
                
                case "carreau":
                    tab[1]++;
                break;
                
                case "pique":
                    tab[2]++;
                break;
                
                case "trefle":
                    tab[3]++;
                break;
            }
        }
        while(j<4 && continuer){
            if(tab[j] >= 5){
                couleur = true;
                continuer = false;
                indice = j;
            }
            j++;
        }
        switch(indice){
                case 0:
                  s =  "coeur";
                break;
                
                case 1:
                  s = "carreau";
                break;
                
                case 2:
                    s = "pique";
                break;
                
                case 3:
                    s = "trefle";
                break;
                
                default:
                    s = "none";
                break;    
            }
        return s;
    }
    
    //regarde s'il y a une couleur proche pour l'ia
    public boolean ProcheCouleur3()
    {
        int cpt=0;
        boolean bool=false;
        Cartes p= this.tri();
        for (int i=0;i<p.getTaille()-1;i++)
        {
            if(p.getCarte(i).getCouleur().equals(p.getCarte(i+1).getCouleur()))
            {
                cpt++;
            }
            if(cpt==2)
            {
                bool=true;
            }
        }
        return bool;
    }

    //regarde s'il y a une couleur proche pour l'ia
    public boolean ProcheCouleur4()
    {
        int cpt=0;
        boolean bool=false;
        Cartes p= this.tri();
        for (int i=0;i<p.getTaille()-1;i++)
        {
            if(p.getCarte(i).getCouleur().equals(p.getCarte(i+1).getCouleur()))
            {
                cpt++;
            }
            if(cpt==3)
            {
                bool=true;
            }
        }
        return bool;
    }
    
    
    //regarde s'il y a une paire
    public boolean Paire(){
        boolean b = false;
        int cmpt = 0;
        Cartes p = this.tri();        
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre()){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 1){
                if((i+2)<p.getTaille()){
                    if(p.getCarte(i+2).getNombre() != p.getCarte(i).getNombre())
                        b = true;
                }
                else
                    b = true;
            }
        }
        return b;
    }
    
    //regarde s'il y a une paire qui est differente de n
    public boolean Paire(int n){
        boolean b = false;
        int cmpt = 0;
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre() && p.getCarte(i).getNombre() != n){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 1){
                if((i+2)<p.getTaille()){
                    if(p.getCarte(i+2).getNombre() != p.getCarte(i).getNombre())
                        b = true;
                }
                else
                    b = true;
            }
        }
        return b;
    }
    
    //renvoie le nombre de la pair s'il y en a une
    public int PaireChiffre(){     
        boolean b = false;
        int cmpt = 0;
        int nombre = 0; 
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre()){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 1){
                if((i+2)<p.getTaille()){
                    if(p.getCarte(i+2).getNombre() != p.getCarte(i).getNombre())
                        nombre = p.getCarte(i).getNombre();
                }
                else
                nombre = p.getCarte(i).getNombre();
            }
        } 
        return nombre;
    }
    
    //renvoie le chiffre de la paire differentes de n
    public int PaireChiffre(int n){    
        boolean b = false;
        int cmpt = 0;
        int nombre = 0; 
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre() && p.getCarte(i).getNombre() != n){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 1){
                if((i+2)<p.getTaille()){
                    if(p.getCarte(i+2).getNombre() != p.getCarte(i).getNombre())
                        nombre = p.getCarte(i).getNombre();
                }
                else
                nombre = p.getCarte(i).getNombre();
            }
        } 
        return nombre;
    }
    
    //renvoie de toute maniere true car toujours proche d'avoir une paire pour ia
    public  boolean ProchePaire()
    {
        return true;
    }
    
    //test si il y a une double paire
    public boolean DoublePaire(){   
        boolean b = false;
        int nombre = -1;
        int nombre1 = -1;
        nombre = PaireChiffre();
        if(nombre != 0){
            b = Paire(nombre);
            return b;
        }
        else
            return false;
    }
    
    //renvoie les nombre de la double pair s'il y en a
    public int[] DoublePaireTab(){      
        boolean b = false;
        int nombre = 0;
        int nombre1 = 0;
        int [] tab = {0,0};
        nombre = PaireChiffre();
        tab[0] = nombre;
        if(nombre != 0){
            tab[1] = PaireChiffre(nombre);
            return tab;
        }
        else
            return tab;
    }
    
    //renvoie la valeur de la parie la plus haute dans une double paire
    public int DoublePaireChiffre(){
        int [] res = this.DoublePaireTab();
        return res[0] > res [1] ? res[0] : res[1];
    }
    
    
    //test si proche d'avoir une double paire pour ia
    public boolean ProcheDoublePaire()
    {
        return this.Paire();
    }
    
    //test s'il y a un brelan ou non
    public boolean Brelan(){
         boolean b = false;
        int cmpt = 0;
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre()){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 2){
                b = true;
            }
        }
        return b;
    }
    
    //test s'il y a un brelan different de n
    public boolean Brelan(int n){
         boolean b = false;
        int cmpt = 0;
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre() && p.getCarte(i).getNombre() != n){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 2){
                b = true;
            }
        }
        return b;
    }

    //test pour savoir si l'ia est proche d'avoir un brelan
    public boolean ProcheBrelan()
    {
        Cartes p = this.tri();
       if(p.Paire())
       {
           return true;
       }
       else {
           return false;
       }
    }
    
    //renvoie le nombre du brelan
    public int BrelanChiffre(){
        boolean b = false;
        int cmpt = 0;
        int nombre = 0; 
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre()){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 2){
                nombre = p.getCarte(i).getNombre();
            }
        }
        
        return nombre;
    }
    
    //renvoie le nombre de la carte du brelan différent de n
    public int BrelanChiffre(int n){
        boolean b = false;
        int cmpt = 0;
        int nombre = 0; 
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre() && p.getCarte(i).getNombre() != n){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 2){
                nombre = p.getCarte(i).getNombre();
            }
        }
        
        return nombre;
    }
    
    //test s'il y a un full
    public boolean Full(){
        int n = PaireChiffre();
        if(n != 0)
            return Brelan(n);
        else
            return false;
    }
    
    //renvoie les valeurs prises par les cartes du full
    public int [] FullChiffre(){
        int n = PaireChiffre();
        int [] tab = {n,0};
        if(n != 0){
            tab[1] = BrelanChiffre(n);
            return tab;
        }
        else
            return tab;
    }
    
    //verifie si l'ia est proche d'avoir un full
    public boolean ProcheFull()
    {
        Cartes p= this.tri();
        if(p.DoublePaire() || p.Brelan())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //verifie s'il y a un carré ou non
    public boolean Carré(){
        boolean b = false;
        int cmpt = 0;
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre()){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 3){
                b = true;
            }
        }
        return b;
    }
    
    //renvoie la valeur du carré
    public int CarréChiffre(){
        boolean b = false;
        int cmpt = 0;
        int nombre = 0; 
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre()){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 3){
                nombre = p.getCarte(i).getNombre();
            }
        }
        
        return nombre;
    }

    //verifie si ia proche d'avoir un carré
    public boolean ProcheCarre()
    {
        Cartes p = this.tri();

        if(p.Brelan())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //verifie si suite ou non
    public boolean Suite(){
        Cartes p = this.copie();
        Cartes p1 = this.copie();
        p = p.tri();                                    //cmpt qui regarde si enchainement ininterrompu de chiffre
        p1 = p1.triSuiteAs();
        boolean b = false;
        int cmpt = 0;
        for(int i=p1.getTaille()-1;i>0;i--){
            if(!(p1.getCarte(i).getNombre() == (p1.getCarte(i-1).getNombre()+1))){
                if(!(p1.getCarte(i).getNombre() == (p1.getCarte(i-1).getNombre())))
                    cmpt = 0;
            }
            else{
                cmpt++;
                if(cmpt == 4)
                    b = true;
            }
        }
        if(!b){
            cmpt = 0;
            for(int i=p.getTaille()-1;i>0;i--){                 
            if(!(p.getCarte(i).getNombre() == (p.getCarte(i-1).getNombre()+1))){
                if(!(p1.getCarte(i).getNombre() == (p.getCarte(i-1).getNombre())))
                    cmpt = 0;
            }
                else{
                cmpt++;
                if(cmpt == 4)
                    b = true;
                }
            }
        }
        if(b)
            return true;
        else
            return false;
    }
    
    //verifie si suite de couleur s
    public boolean Suite(String s){
        Cartes p = this.copie();
        Cartes p1 = this.copie();
        p = p.tri();                                    //cmpt qui regarde si enchainement ininterrompu de chiffre
        p1 = p1.triSuiteAs();
        boolean b = false;
        int cmpt = 0;
        for(int i=p1.getTaille()-1;i>0;i--){                 
            if(!(p1.getCarte(i).getNombre() == (p1.getCarte(i-1).getNombre()+1))){
                if(!((p1.getCarte(i).getNombre() == (p1.getCarte(i-1).getNombre()+1)) && p1.getCarte(i).getCouleur() == s))
                    cmpt = 0;
            }
            else{
                cmpt++;
                if(cmpt == 4)
                    b = true;
            }
        }
        if(!b){
            cmpt = 0;
            for(int i=p.getTaille()-1;i>0;i--){                 
            if(!(p.getCarte(i).getNombre() == (p.getCarte(i-1).getNombre()+1))){
                if(!((p.getCarte(i).getNombre() == (p.getCarte(i-1).getNombre()+1)) && p.getCarte(i).getCouleur() == s))
                    cmpt = 0;
            }
                else{
                cmpt++;
                if(cmpt == 4)
                    b = true;
                }
            }
        }
        if(b)
            return true;
        else
            return false;
    }

    //verifie si ia proche d'avoir suite
    public boolean ProcheSuite()
    {   boolean result=false;
        Cartes p = this.tri();
        for(int i=p.getTaille()-1;i>3;i--)
        {
            if(p.getCarte(i).getNombre()==p.getCarte(i-1).getNombre()+1 && p.getCarte(i-1).getNombre()+1==p.getCarte(i-2).getNombre()+1 && p.getCarte(i-2).getNombre()+1==p.getCarte(i-3).getNombre()+1)
            {
                result=true;
            }
        }

        return result;
    }

    //renvoie carte la plus haute de la suite
    public Carte SuiteHaute(){
        Cartes p = this.copie();
        Cartes p1 = this.copie();
        p = p.tri();
        p1 = p1.triSuiteAs();
        Carte c = new Carte(0,"");
        boolean b = false;
        int cmpt = 0;
        for(int i=p1.getTaille()-1;i>0;i--){                 
            if(!((p1.getCarte(i).getNombre() == (p1.getCarte(i-1).getNombre()+1)))){
                if(!(p1.getCarte(i).getNombre() == (p1.getCarte(i-1).getNombre())))
                    cmpt = 0;
            }
            else{
                if(cmpt == 0 && !b)
                    c = p1.getCarte(i);
                cmpt++;
                if(cmpt == 4)
                    b = true;
            }
        }
        if(!b){
            cmpt = 0;
            for(int i=p.getTaille()-1;i>0;i--){                 
            if(!(p.getCarte(i).getNombre() == (p.getCarte(i-1).getNombre()+1))){
                if(!((p.getCarte(i).getNombre() == (p.getCarte(i-1).getNombre()+1))))
                    cmpt = 0;
            }
                else{
                    if(cmpt == 0 && !b)
                        c = p.getCarte(i);
                    cmpt++;
                    if(cmpt == 4)
                    b = true;
                }
            }
        }
        if(!b)
            c.setCouleur("");
        
        return c;
    }
    
    //renvoie carte haute de la suite avec la couleur s
    public Carte SuiteHaute(String s){
        //pour couleur
        Cartes p = this.copie();
        Cartes p1 = this.copie();
        p = p.tri();
        p1 = p1.triSuiteAs();
        Carte c = new Carte(0,"");
        boolean b = false;
        int cmpt = 0;
        for(int i=p1.getTaille()-1;i>0;i--){                 
            if(!((p1.getCarte(i).getNombre() == (p1.getCarte(i-1).getNombre()+1)) && p1.getCarte(i).getCouleur().equals(s) && p1.getCarte(i-1).getCouleur().equals(s))){
                if(!(p1.getCarte(i).getNombre() == p1.getCarte(i-1).getNombre()))
                    cmpt = 0;
            }
            else{
                if(cmpt == 0 && !b)
                    c = p1.getCarte(i);
                cmpt++;
                if(cmpt == 4)
                    b = true;
            }
        }
        if(!b){
            cmpt = 0;
            for(int i=p.getTaille()-1;i>0;i--){                 
            if(!((p.getCarte(i).getNombre() == (p.getCarte(i-1).getNombre()+1)) && p.getCarte(i).getCouleur().equals(s) && p.getCarte(i-1).getCouleur().equals(s))){
                if(!(p.getCarte(i).getNombre() == (p.getCarte(i-1).getNombre()+1)))
                    cmpt = 0;
            }
                else{
                    if(cmpt == 0 && !b)
                        c = p.getCarte(i);
                    cmpt++;
                    if(cmpt == 4)
                    b = true;
                }
            }
        }
        if(!b)
            c.setCouleur("");
        
        return c;
    }
    
    //verif si quinte flush
    public boolean QuinteFlush(){
        String s = CouleurNom();
        Carte c;
        if(!s.equals("none")){
            c = SuiteHaute(s);
            if(!c.getCouleur().equals("")){
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    //verifie si ia est proche d'avoir une quinte flush 
    public boolean ProcheQuinteFlush()
    {
        Cartes p=this.tri();
        if((p.ProcheSuite()||p.Suite())&&(p.ProcheCouleur4()|| p.ProcheCouleur4())){
                return true;
        }
        else
        {
            return false;
        }
    }
    
    //verifie s'il y a une quinte flush royale
    public boolean QuinteFlushRoyal(){
        String s = CouleurNom();
        Carte c;
        if(!s.equals("none")){
            c = SuiteHaute(s);
            if(!c.getCouleur().equals("") && c.getNombre() == 14){
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    //regarde s'il y a une suite de tete
    public boolean SuiteTete()
    {
        int cpt=0;
        Cartes p= this.tri();
        for(int i=10;i<14;i++)
        {
            if(p.trouver(i))
            {
                cpt++;
            }
        }
        if(cpt==4)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //cherche carte de valeur num
    public boolean trouver(int num){
        Cartes p = this.tri();
        int i=0;
        boolean trouver = false;
        while (i<p.getTaille() && !trouver)
        {
            if(p.getCarte(i).getNombre()==num)
            {
                trouver=true;
            }
            i++;
        }
        return trouver;
    }

    //verifie si ia proche d'avoir quinte flush royale
    public boolean ProcheQuinteFlushRoyale()
    {
        Cartes p= this.tri();
        if( SuiteTete() && (p.ProcheCouleur4() || p.Couleur()) )
        {
            return true ;
        }
        else
        {
            return false ;
        }
    }
    
    //fonction de tri par ordre croissant des cartes
    public Cartes tri(){
        Cartes t1  = new Cartes();
        Cartes t2 = this.copie();
        Carte c;
        int min;
        int indice = 0;
        min = t2.getCarte(0).getNombre();
        while(t1.getTaille() < this.getTaille()){
            min = t2.getCarte(0).getNombre();
            for(int i=0;i<t2.getTaille();i++){
                if(min >= t2.getCarte(i).getNombre()){ 
                    min = t2.getCarte(i).getNombre();
                    indice = i;
                }
            }
            t1.addCarte(t2.remove(indice));
        }
        return t1;
    }
    
    //fonction par ordre croissant cartes avec l'as en valeur supérieur
    public Cartes triSuiteAs(){
        Cartes t1  = new Cartes();
        Cartes t2 = this.copie();
        Carte c;
        int min;
        int indice = 0;
        //remplacement du 1 par la suite du roi soit 14
        for(int i=0;i<this.getTaille();i++){
            if(t2.getCarte(i).getNombre() == 1)
                t2.getCarte(i).setNombre(14);
        }
        
        min = t2.getCarte(0).getNombre();
        while(t1.getTaille() < this.getTaille()){
            min = t2.getCarte(0).getNombre();
            for(int i=0;i<t2.getTaille();i++){
                if(min >= t2.getCarte(i).getNombre()){
                    min = t2.getCarte(i).getNombre();
                    indice = i;
                }
            }
            t1.addCarte(t2.remove(indice));
        }
        return t1;
    }
    
}
