/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker_partie;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*; 
import java.util.ArrayList;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author tomskiev
 */
public class Partie extends JFrame implements ActionListener {

    private final static int NB_PAQUET = 52,NB_CARTE_JOUEUR = 2;
    private final int HAUTEUR = 720;
    private final int LARGEUR = 1280;
    
    
    private boolean action;
    public int ibis,tmp;
    
    private JPanel table;            
    private JPanel p1;                   
    private JPanel p2;

    private ArrayList <Player> tab; //sert aussi pour le nombre de joueurs
    private int tour;
    //add de var pour identifier le dealer
    private int numDealer;
    //argent des mise pendant un round
    private int argent = 0;
            
    private Cartes paquet;
    private Cartes tapis;
    boolean continuer =true;
    public int resultat=-1;
    
    
    
    //constructeur de partie avec le nombre d'ia et arrayList des noms des joueurs
    Partie(int nb_Ia,ArrayList <String> Nom) throws InterruptedException{
        super();
        mise_en_page( LARGEUR, HAUTEUR ); 
        
        this.tour = 0;
        if(Nom.size() == 1 && nb_Ia == 0)
            System.exit(-1);
        else if(Nom.size() > 4){    //pas plus de 4 joueurs 
            System.exit(-1);
        }
        tab = new ArrayList();

        for(int i=0;i<Nom.size();i++){
            tab.add(new Joueur(Nom.get(i)));
        }

        if(nb_Ia > 0){
            for(int i=0;i<nb_Ia;i++){
                tab.add(new IA(tab));
            }
        }

        for(int i=0;i<tab.size();i++){
            System.out.println("Le joueur numero "+(i+1)+" s'appelle "+tab.get(i).getNom());
            if(this.tab.get(i).estIA())
                System.out.println("IA");
        }
        //alea pour dealer au début du premier tour d'une partie
        numDealer = alea(0,tab.size()-1);
        
        tab.get(numDealer).setDealer(true);

        while(continuer){
            gestionTour();
        }


    }
    
    
    //mise en forme de l'interface graphique avec les dimensions en param
    public void mise_en_page(int maxX, int maxY) 
    {   
        setTitle("Poker");
        setSize(maxX,maxY);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        this.p1 = new JPanel(new GridLayout());
  	getContentPane().add(p1,"North");
        this.p2 = new JPanel(new GridLayout());
        getContentPane().add(p2,"South");
        
        this.table = new JPanel();
        this.table.setSize(maxX,maxY);                                  
        this.table.setPreferredSize(new Dimension(maxX,maxY));
        getContentPane().add(this.table,"Center");
  	ajouteBouton("Quitter", p2);
        
        pack();
        setVisible(true);
    }
    
    
    private void ajouteBouton(String label, JPanel p) {
        JButton b = new JButton(label);
        p.add(b);
        b.addActionListener(this);
    }
    
    
    
    public void paint(Graphics g) 
    {
         g.setFont(new Font("TimeRoman",Font.PLAIN,40));
         this.p1.repaint();  
         this.p2.repaint();
         peindreDecor(g);
         ajoutCarte(g);
         
         
    }
    
    //ajoute les cartes a la fen graphiques
    public void ajoutCarte(Graphics g){
        for(int i=0;i<this.tab.size();i++){
            for(int j=0;j<this.tab.get(i).getMain().getTaille();j++){
                if(ibis == i)
                ajoutCarteJoueur(this.tab.get(i).getMain().getCarte(j),g,i,j);
                else
                ajoutCarteJoueur(new Carte(-1,"Coeur"),g,i,j);
            }
        }
        
        for(int i=0;i<this.tapis.getTaille();i++){
            ajoutCarteRiviere(this.tapis.getCarte(i),g,i);
        }
        g.setColor(Color.white);
        g.drawString("Score :", 600, 50);
        g.drawString(""+this.argent, 625, 100);
        g.setColor(Color.black);
    }
    
    //test pour joueurs humains présents
    public boolean VerifJoueurPresent(){
        boolean b = false;
        int i = 0;
        while(i < this.tab.size() && !b){
            if(this.tab.get(i).estJoueur())
                b = true;
            i++;
        }
        return b;
    }
    
    //ajout des textures des cartes et indicateurs de jeu
    public void ajoutCarteJoueur(Carte carte,Graphics g,int i,int j){
        int x=0;
        int y=0;
        String nb,coul;
        //pour initialiser l'image
        Image img = null;
        if(carte.getNombre() > 0){
        nb = Integer.toString(carte.getNombre());
        coul= carte.getCouleur();
        }
        else{
         nb = "dos";
         coul = "carte";
        }
        
        String chemin = "cartes/"+nb+"_"+coul+".png";
        try {
            img = ImageIO.read(new File(chemin));
         } catch (IOException e) {
            e.printStackTrace();
        }
        
        switch(i){
            case 0:
                g.drawImage(img, 150+j*80, 350, rootPane);
                if(j == 1){
                    g.setColor(Color.black);
                    g.drawString(""+this.tab.get(i).getArgent(), 25, 450);
                    g.drawString(""+this.tab.get(i).getNom(), 25, 500);
                    if(!this.tab.get(i).estEnJeu()){
                        g.setColor(Color.red);
                       g.drawString("Couché", 25, 550); 
                    }
                    else{
                        g.setColor(Color.blue);
                       g.drawString("En Jeu", 25, 550); 
                    }
                }
            break;
            case 1:
                g.drawImage(img, 350+j*80, 450, rootPane);
                if(j == 1){
                    g.setColor(Color.black);
                    g.drawString(""+this.tab.get(i).getArgent(), 375, 600);
                    g.drawString(""+this.tab.get(i).getNom(), 360, 650);
                    if(!this.tab.get(i).estEnJeu()){
                        g.setColor(Color.red);
                       g.drawString("Couché", 360, 700); 
                    }
                    else{
                       g.setColor(Color.blue);
                       g.drawString("En Jeu", 360, 700); 
                    }
                }
            break;
            case 2:
                g.drawImage(img, 650+j*80, 450, rootPane);
                if(j == 1){
                    g.setColor(Color.black);
                    g.drawString(""+this.tab.get(i).getArgent(), 675, 600);
                    g.drawString(""+this.tab.get(i).getNom(), 650, 650);
                    if(!this.tab.get(i).estEnJeu()){
                        g.setColor(Color.red);
                       g.drawString("Couché", 650, 700); 
                    }
                    else{
                       g.setColor(Color.blue);
                       g.drawString("En Jeu", 650, 700); 
                    }
                }
            break;
            case 3:
                g.drawImage(img, 900+j*80, 350, rootPane);
                if(j == 1){
                    g.setColor(Color.black);
                    g.drawString(""+this.tab.get(i).getArgent(), 1140, 450);
                    g.drawString(""+this.tab.get(i).getNom(), 1130, 500);
                    g.setColor(Color.red);
                    if(!this.tab.get(i).estEnJeu()){
                        g.setColor(Color.red);
                       g.drawString("Couché", 1130, 550); 
                    }
                    else{
                       g.setColor(Color.blue);
                       g.drawString("En Jeu", 1130, 550); 
                    }
                }
            break;
            default:
            break;
        }

    }
    
    //ajout des textures des cartes du tapis
    public void ajoutCarteRiviere(Carte carte,Graphics g,int n){    
        int x=0;
        int y=0;
        String nb = Integer.toString(carte.getNombre());
        String coul= carte.getCouleur();
        String chemin = "cartes/"+nb+"_"+coul+".png";
        switch(n){
            case 0:
                x=460;
                y=253;
            break;
            case 1:    
                x=460+71+1;
                y=253;
            break;
            case 2:
                x=460+71+71+2;
                y=253;
            break;
            case 3:
                x=460+71+71+71+3;
                y=253;
            break;
            case 4:
                x=460+71+71+71+71+4;
                y=253;
            break;
        }
       try {
            Image img = ImageIO.read(new File(chemin));
            g.drawImage(img, x, y, this);
         } catch (IOException e) {
            e.printStackTrace();
        }  
    }
    
    
    
    //ajout texture décors
    public void peindreDecor(Graphics g){
        try {
                Image img = ImageIO.read(new File("cartes/decord.jpg"));
                g.drawImage(img, 0, 0, 1280, 720, this);
               
                
         } catch (IOException e) {
                e.printStackTrace();
         }
    }
    
    

    //gestion des tours charque round d'une partie
    private void gestionTour() throws InterruptedException{
        int i = 0;
        switch(this.tour){
            //distribution des deux cartes et trois carte sur le tapis face retournées
            case -1:
                if(!VerifJoueurPresent()){
                    String [] reponse = {"oui","non"};
                    int rep = JOptionPane.showOptionDialog(null, "Voulez vous continuer la partie ?","Arreter le joueur",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,reponse,reponse[0]);
                    if(rep == 1){
                        continuer = false;
                        this.dispose();
                    }
                }
                this.tab.get(numDealer).setDealer(false);
                ReinitJoueurs();
                i=0;
                while( i < this.tab.size()){
                    if(this.tab.get(i).getArgent() <= 0){
                        System.out.println("Le joueur "+this.tab.get(i).getNom()+" est éliminé.");
                        this.tab.remove(i);
                    }
                    else
                    i++;
                }
                if(numDealer+1 <= this.tab.size()-1){
                    numDealer++;
                }
                else{
                    numDealer = 0;
                    }
                if(this.tab.size() > 0)
                this.tab.get(numDealer).setDealer(true);
                
                
                break;
                
                case 0:
                if(this.tab.size() >= 2){
                this.argent = 0;
                initPaquet();
                tapis = new Cartes();
                debout();
                distribution();
                //gestion de tri en fonction des petite et grosse blindes
                triJoueursDealer();
                gestionTapis();
                paris();
                }
                else
                continuer = false;
                break;

            case 1 : case 2:
                if(nbrJoueur() < 2){
                    while(i<this.tab.size() && !this.tab.get(i).estEnJeu()){
                        i++;
                    }
                    JOptionPane.showMessageDialog(null,"Pas assez de joueur.\nVainqueur "+this.tab.get(i).getNom(),"Tour Finis",JOptionPane.INFORMATION_MESSAGE);
                    partageArgent();
                    System.out.println("Pas assez de joueurs");
                    tour = -2;
                }
                else{
                    gestionTapis();
                    paris();
                }
                break;

            case 3:
                if(nbrJoueur() < 2){
                    while(i<this.tab.size() && !this.tab.get(i).estEnJeu()){
                        i++;
                    }
                    JOptionPane.showMessageDialog(null,"Pas assez de joueur.\nVainqueur "+this.tab.get(i).getNom(),"Tour Finis",JOptionPane.INFORMATION_MESSAGE);
                    partageArgent();
                    tour = -2;
                }
                else{
                gestionTapis();
                paris();
                comparaison();
                this.tour = -2;
                }
                break;
                
        }
        if(this.tour != 3)
            this.tour++;
        
    }
    
    //remet les joueurs en jeu
    public void debout(){
        for(int i=0;i<this.tab.size();i++){
            this.tab.get(i).seReleve();
        }
    }
    
    //reinit les main des joueurs
    public void ReinitJoueurs(){
        for(int i=0;i<this.tab.size();i++){
            this.tab.get(i).main = new Cartes();
        }
    }
    
    //gestion dealer
    public void triJoueursDealer(){
        //Dealer initié au début de chaque partie
        Player p1,p2;
        System.out.println("Dealer num est de "+numDealer+" et tab est de "+tab.size());
        switch(numDealer){
            case 0:
                p1 = this.tab.remove(this.tab.size()-1);
                //car arrayList
                p2 = this.tab.remove(this.tab.size()-1);
            break;
            case 1:
                p1 = this.tab.remove(0);
                p2 = this.tab.remove(this.tab.size()-1);
            break;
            default:
                p1 = this.tab.remove(numDealer-1);
                p2 = this.tab.remove(numDealer-2);
            break;
        }
        this.tab.add(0, p1);
        this.tab.add(1, p2);
        
    }
    
    //partage argent
    public void partageArgent(){
        for(int i=0;i<this.tab.size();i++){
            if(this.tab.get(i).estEnJeu()){
                this.tab.get(i).setArgent(this.argent/nbrJoueur());
            }
        }
    }
    
    //met les cartes dans le paquet j pour la couleur et i pour le nombre de 11 a 13 les tetes
    private void initPaquet(){
        Carte tmp = new Carte();
        paquet = new Cartes();
        for(int j=0;j<4;j++){
            for(int i=0;i<13;i++){
                paquet.addCarte(new Carte((i+1),tmp.getTabCouleur(j)));
            }
        }
    }
    
    //ici les test des scores, si socre identique on appronfondira
    private void comparaison(){
        ArrayList <Carte> c = new ArrayList();
        int cmpt = 0;
        //int indiceTmp;
        ArrayList <Player> vainqueur = new ArrayList();
        HashMap <Integer,Carte> haute = new HashMap();
        HashMap <Integer,Integer> combinaison = new HashMap();
        int min,indice;
        indice = 0;
        for(int i=0;i<this.tab.size();i++){
            if(this.tab.get(i).estEnJeu()){
                combinaison.put(i,this.tab.get(i).meilleurCombinaison(c,this.tapis));  //on utilisera le c comme pointeur pour le retour de la carte la plus haute sera utile pour score similaire
                System.out.println(this.tab.get(i).getNom()+" a "+combinaison.get(i));
                haute.put(i,new Carte(c.get(0).getNombre(),c.get(0).getCouleur()));
                c.remove(0);
                indice = i;
            }
        }
        min = combinaison.get(indice);
        
        //resultat pour le plus petit score donc la score le plus important
        
        for(int i=0;i<this.tab.size();i++){
            if(this.tab.get(i).estEnJeu()){
                if(min > combinaison.get(i)){
                    min = combinaison.get(i);
                    indice = i;
                }
            }
        }
        
        //verif si plusieurs joueurs ont le même score
        
        for(int i=0;i<this.tab.size();i++){
            if(this.tab.get(i).estEnJeu()){
                if(combinaison.get(i) == min){
                    cmpt++;
                }
            }
        }
        
        //comparaison des cartes les plus hautes
        int max = 0;
        if(cmpt > 1){
            //verif de la carte la plus haute
            for(int i=0;i<tab.size();i++){
                //fonctionne car element a gauche du if prio dans test
                if(tab.get(i).estEnJeu() && combinaison.get(i) == min){
                  if(max<haute.get(i).getNombre()){
                      max = haute.get(i).getNombre();
                      indice = i;
                  }   
                }
            }
            cmpt=0;
            //verif si plusieurs carte hautes
            for(int i=0;i<tab.size();i++){
                if(tab.get(i).estEnJeu() && (combinaison.get(i) == min) && (haute.get(i).getNombre() == max)){
                   cmpt++;  
                }
            }
            if(cmpt > 1){
                System.out.println("Il y a plusieurs vainqueurs !!!!!!");
                JOptionPane.showMessageDialog(null,"Il y a plusieurs vainqueurs !!!!!!","Félicitations",JOptionPane.INFORMATION_MESSAGE);
                partageArgent();
                for(int i=0;i<tab.size();i++){
                    if(tab.get(i).estEnJeu() && combinaison.get(i) == min && haute.get(i).getNombre() == max){
                        JOptionPane.showMessageDialog(null,"Un des vainqueurs est "+this.tab.get(i).getNom()+" avec un/une "+typeVictoire(combinaison.get(indice))+" avec en carte haute "+haute.get(i).getNombre(),"Félicitations",JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("Un des vainqueurs est "+this.tab.get(i).getNom()+" avec un nombre numero "+typeVictoire(combinaison.get(indice))+" avec en carte haute "+haute.get(i).getNombre());
                    }
                }
            }
            else{
                System.out.println("Le vainqueur est "+this.tab.get(indice).getNom()+" avec un nombre numero "+combinaison.get(indice));
                JOptionPane.showMessageDialog(null,"Le vainqueur est "+this.tab.get(indice).getNom()+" avec un/une "+typeVictoire(combinaison.get(indice)),"Félicitations",JOptionPane.INFORMATION_MESSAGE);
                this.tab.get(indice).setArgent(this.argent);
                resultat = combinaison.get(indice);
            }
        }
        else{
        System.out.println("Le vainqueur est "+this.tab.get(indice).getNom()+" avec un nombre numero "+combinaison.get(indice));
        JOptionPane.showMessageDialog(null,"Le vainqueur est "+this.tab.get(indice).getNom()+" avec un/une "+typeVictoire(combinaison.get(indice)),"Félicitations",JOptionPane.INFORMATION_MESSAGE);
        this.tab.get(indice).setArgent(this.argent);
        resultat = combinaison.get(indice);
        }
        
        
    }

    //traduit les n victoires en string
    public String typeVictoire(int n){
        String s = new String();
        switch(n){
            case 0:
                s = "Quinte Flush Royale";
            break;
            case 1:
                s = "Quinte Flush";
            break;
            case 2:
                s = "Carré";
            break;
            case 3:
                s = "Full";
            break;
            case 4:
                s = "Couleur";
            break;
            case 5:
                s = "Suite";
            break;
            case 6:
                s = "Brelan";
            break;
            case 7:
                s = "Double Paires";
            break;
            case 8:
                s = "Paire";
            break;
            case 9:
                s = "Carte Haute";
            break;
        }
        return s;
    }
    
    
    //nombre joueurs en jeu
    private int nbrJoueur(){
        int cmpt = 0;
        for(int i=0;i<this.tab.size();i++){
            if(this.tab.get(i).estEnJeu())
                cmpt++;
        }
        return cmpt;
    }


    //gestion de distribution des cartes du tapis
    private void gestionTapis(){
        switch(this.tour){
            case 0:
                //face retournée avec skin retournée
                break;

            case 1:
                for(int i=0;i<3;i++){
                    this.tapis.addCarte(this.paquet.remove(alea(0,paquet.getTaille()-1)));
                }
                break;

            case 2: case 3:
                this.tapis.addCarte(this.paquet.remove(alea(0,paquet.getTaille()-1)));
                break;
        }
        System.out.println("On a sur le tapis :");
        for(int i=0;i<this.tapis.getTaille();i++){
            System.out.println(this.tapis.getCarte(i));
        }
    }
    
    //distribution des cartes pour joueurs
    private void distribution(){
        Carte c;
        for(int i=0;i<this.tab.size();i++){
            for(int j=0;j<NB_CARTE_JOUEUR;j++){
                c = this.paquet.remove(alea(0,this.paquet.getTaille()-1));
                tab.get(i).RecevoirCarte(c, j);
            }
        }
    }

    //gestion des paris
    private void paris() throws InterruptedException{
        int tmp = 0;
        int valeur = 0; //au dealer plutot mais on verra apres            //on va comp les deux valeur la tmp et la valeur servant de témoin entre chaque tour de proposition
        int valeurTmp = valeur;
        int ronde  = 0;
        Cartes tapis = this.tapis;
        boolean continuer = true;
        do{
            if(valeur == 0){    //un tour de table obligatoire au debut
                if(this.tab.get(0).estEnJeu() && this.tab.get(0).getArgent() > 0){
                    argent+= Player.PETITEBLINDE;
                    valeurTmp = Player.PETITEBLINDE;
                    this.tab.get(0).setMise(Player.PETITEBLINDE);
                }
                if(this.tab.get(1).estEnJeu() && this.tab.get(0).getArgent() > 0){
                    argent+=Player.GROSSEBLINDE;
                    valeurTmp = Player.GROSSEBLINDE;
                    this.tab.get(1).setMise(Player.GROSSEBLINDE);
                }
                for(ibis=2;ibis<this.tab.size();ibis++){
                    if(this.tab.get(ibis).estEnJeu()){
                            repaint();
                            if(this.tab.get(ibis).getArgent() > 0 && this.tab.get(ibis).getMise()<= this.tab.get(ibis).getArgent())
                            tmp = this.tab.get(ibis).proposition(valeurTmp,tapis,this);
                            else{
                            tmp = -1;
                            }
                        //se couche
                        if(tmp == -1){
                            this.tab.get(ibis).seCouche();
                        }
                        //suit
                        else if(tmp == valeurTmp){

                        }
                        //augmente
                        else if(tmp > valeurTmp){
                            valeurTmp = tmp;
                        }
                    }
                }
            }

            else{   //on regarde uniquement si les joueur on la mise qui est ajustée avec la mise actuelle
                for(ibis=0;ibis<this.tab.size();ibis++){
                    if(this.tab.get(ibis).estEnJeu() && this.tab.get(ibis).getMise() < valeurTmp){
                        repaint();
                        if(this.tab.get(ibis).getArgent() > 0 && this.tab.get(ibis).getMise()<= this.tab.get(ibis).getArgent())
                        tmp = this.tab.get(ibis).proposition(valeurTmp,tapis,this);
                        else{
                            tmp = -1;
                        }
                        //se couche
                        if(tmp == -1){
                            this.tab.get(ibis).seCouche();
                        }
                        //suit si tmp ==     valeurTmp

                        //augmente
                        else if(tmp > valeurTmp){
                            valeurTmp = tmp;
                        }
                    }
                
                }
                
            }

            if(valeur == valeurTmp)
                continuer = false;
            else{
                valeur = valeurTmp;
            }
            
        }while(continuer);
        
            for(int i=0;i<this.tab.size();i++){
                    if(this.tab.get(i).estEnJeu() && this.tab.get(i).getArgent() > 0){
                        argent+=valeurTmp;
                        this.tab.get(i).setArgent(-valeurTmp);
                        this.tab.get(i).setMise(0);
                    }
                    else{
                        argent+=this.tab.get(i).getMise();
                        this.tab.get(i).setMise(0);
                    }
                }
                if(!this.tab.get(0).estEnJeu() && this.tab.get(0).getArgent() > 0){
                    this.tab.get(0).setArgent(-Player.PETITEBLINDE);
                    this.argent+=Player.PETITEBLINDE;
                }
                if(!this.tab.get(1).estEnJeu() && this.tab.get(1).getArgent() > 0){
                    this.tab.get(1).setArgent(-Player.GROSSEBLINDE);
                    this.argent+=Player.GROSSEBLINDE;
                }
    }

    //fonction aléa
    public int alea(int min,int max){
        return (int)(Math.random()*(max-min+1)+min);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String arg = e.getActionCommand();  
        
        if (arg.equals("Quitter"))
        {
            continuer = false;
            this.dispose();
        }
 							
   	   
            
         
    repaint();
    }
}