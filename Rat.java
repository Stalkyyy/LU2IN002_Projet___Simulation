/*
    Projet Java - LU2IN002 - 2022
    Rat.java
    
    PINHO FERNANDES Enzo - 21107465
    DURBIN Deniz Ali - 21111116
*/

import java.util.ArrayList;

public class Rat extends Predateur implements PredRat{
    /*
     * C'est la classe des Rats, un prédateur.
     * Le renard se balade dans le terrain, en quête d'oeufs afin de les manger.
     * Il peut attaquer un oeuf proche de lui, mais se fera attaquer par les poulets autour de l'oeuf s'il y en a !
     * Il peut également se reproduire si le hasard le veut bien.
     */


    // Statistiques.
    private static int nbRatTotal = 0;
    private static int nbRatMorts = 0;
    private static int nbRatReproduction = 0;
    private static int nbOeufMange = 0;

    public Rat(int x, int y, Terrain terrain){
        super(x, y, 100, terrain);
        nbRatTotal++;
    }


    /*
     * Constructeur par copie, représentant la naissance d'un enfant renard. 
     */
    public Rat(Rat parent){
        super(parent.x, parent.y, parent.energie/2 + 1, parent.terrain);
        nbRatTotal++;
    }



    public double probaAttackSuccess(Poule p){
        return (double)energie / ((double)p.energie + 1.0);
    }



    /*
     * Fonction correspondant à une attaque envers un oeuf.
     * Ici, s'il y a un oeuf sur la case du terrain correspondant à sa position, alors il y a tentative d'attaque.
     * S'il y a une poule autour de lui (distance euclidienne inférieure à 3), alors il y a une lutte.
     * S'il gagne la lutte, une autre poule l'attaque s'il y en a. S'il est toujours vivant, alors il mange l'oeuf et rajoute à ses points d'énergie la quantité d'oeufs.
     * S'il perd la lutte, il meurt.
     */
    public void attack(ArrayList<Ressource> listRes){
        if (!terrain.caseEstVide(x, y)){
            Ressource res = terrain.getCase(x, y);
            if (res.type.equals("Oeufs")){
                for(Poule poule : Poule.getListPoules()){
                    if (this.distance(poule.x, poule.y) < 6){
                        if (Math.random() < probaAttackSuccess(poule)){
                            listPredateur.remove(this);
                            listAgent.remove(this);
                            nbRatMorts++;
                            System.out.println("Echec de l'attaque du rat, il est mort !");
                            return;
                        }
                    }
                }

                this.energie += res.getQuantite();
                nbOeufMange += res.getQuantite();

                listRes.remove(res);
                terrain.videCase(x, y);

                Ressource herbe = new Ressource("  __  ", 3);
                terrain.setCase(x, y, herbe);
                listRes.add(herbe);

                System.out.println("Un rat a mangé un ou plusieurs oeufs !");
            }
        }
    }


    /*
     * Fonction de reproduction du renard.
     * Si le hasard le veut bien, le renard fera un enfant qui a la même position que lui sur le terrain MAIS avec la moitié de son énergie.
     */
    public void reproduce(){
        if (Math.random() < p_reproduce){
            Rat enfant = new Rat(this);
            listAgent.add(enfant);
            System.out.println("Un rat vient de naitre !");
        }
    }



    /*
     * Fonctions utilitaires pour les statistiques, le cas d'arrêt de la simulation...
     */
    public static int getNbRatTotal() { return nbRatTotal; }
    public static int getNbRatMorts() { return nbRatMorts; }
    public static int getNbRatVivant() { return nbRatTotal - nbRatMorts; }
    public static int getNbRatReproduction() { return nbRatReproduction; }

    public boolean checkAlive(){
        if (energie <= 0){
            listPredateur.remove(this);
            listAgent.remove(this);
            nbRatMorts++;
            System.out.println("Un rat est mort d'épuisement !");
            return false;
        }

        return true;
    }



    /*
     * Fonction qui permet de récupérer les statistiques de fin de simulation des renards.
     */
    public static String stat(){
        String message = "Nombre de rats au total : " + nbRatTotal;
        message += "\nNombre de rats morts : " + nbRatMorts;
        message += "\nNombre d'oeufs mangés : " + nbOeufMange;
        return message;
    }
}
