/*
    Projet Java - LU2IN002 - 2022
    Renard.java
    
    PINHO FERNANDES Enzo - 21107465
    DURBIN Deniz Ali - 21111116
*/

import java.util.ArrayList;

public class Renard extends Predateur implements PredRenard{
    /*
     * C'est la classe des Renards, un prédateur.
     * Le renard se balade dans le terrain, en quête de poules afin de les manger.
     * Il peut attaquer un poulet proche de lui, ainsi que manger un poulet mort d'épuissement (Ressource viande sur le terrain)
     * Il peut également se reproduire si le hasard le veut bien.
     */


    // Statistiques.
    private static int nbRenardTotal = 0;
    private static int nbRenardMorts = 0;
    private static int nbPouleMange = 0;
    private static int nbRenardReproduction = 0;

    public Renard(int x, int y, Terrain terrain){
        super(x, y, 100, terrain);
        nbRenardTotal++;
    }


    /*
     * Constructeur par copie, représentant la naissance d'un enfant renard. 
     */
    public Renard(Renard parent){
        super(parent.x, parent.y, parent.energie/2 + 1, parent.terrain);
        nbRenardTotal++;
    }


    public double probaAttackSuccess(Poule p){
        return (double)energie / (double)p.energie;
    }



    /*
     * Fonction correspondant à une attaque envers une poule.
     * Ici, si la distance euclidienne entre lui et la poule est inférieure ou égale à 1, alors il y a tentative d'attaque.
     * Si la probabilité de succès lui est favorable, alors on rajoute les points d'énergie de la poule aux siens.
     * Il ne peut attaquer qu'une poule à la fois !
     */
    public void attack(){
        for(Poule poule : Poule.getListPoules()){
            if (this.distance(poule.x, poule.y) <= 1){
                if (Math.random() < probaAttackSuccess(poule)){
                    this.energie += poule.energie;
                    listAgent.remove(poule);
                    Poule.removeInListPoule(poule);
                    Poule.pouleTueCpt();
                    nbPouleMange++;

                    System.out.println("Un renard a mangé une poule !");
                    return;
                }
            }
        }
    }


    /*
     * Fonction qui lui permet de manger la ressource Viande.
     * Si la case du terrain correspondant à sa position est munie d'une ou plusieurs viandes de poulet mort, alors il récupère 5 points d'énergie par viande.
     * L'herbe repousse sur le terrain avec 3 quantités.
     */
    public void manger(ArrayList<Ressource> listRes) throws PasDeRessourceException{
        if (terrain.caseEstVide(x, y)) { throw new PasDeRessourceException(); }
        
        Ressource res = terrain.getCase(x, y);
        if (res.type.equals("Viande")){
            this.energie += res.getQuantite()*5;
            listRes.remove(res);
            terrain.videCase(x, y);

            Ressource herbe = new Ressource("  __  ", 3);
            terrain.setCase(x, y, herbe);
            listRes.add(herbe);

            System.out.println("Un renard a mangé de la viande !");
        }
    }



    /*
     * Fonction de reproduction du renard.
     * Si le hasard le veut bien, le renard fera un enfant qui a la même position que lui sur le terrain MAIS avec la moitié de son énergie.
     */
    public void reproduce(){
        if (Math.random() < p_reproduce){
            Renard enfant = new Renard(this);
            listAgent.add(enfant);
            nbRenardReproduction++;
            System.out.println("Un renard vient de naitre !");
        }
    }


    /*
     * Fonctions utilitaires pour les statistiques, le cas d'arrêt de la simulation...
     */
    public static int getNbRenardTotal() { return nbRenardTotal; }
    public static int getNbRenardMorts() { return nbRenardMorts; }
    public static int getNbRenardVivant() { return nbRenardTotal - nbRenardMorts; }
    public static int getNbRenardReproduction() { return nbRenardReproduction; }
    public static int getNbPouleMange() { return nbPouleMange; }

    public boolean checkAlive(){
        if (energie <= 0){
            listPredateur.remove(this);
            listAgent.remove(this);
            nbRenardMorts++;
            System.out.println("Un renard est mort d'épuisement !");
            return false;
        }

        return true;
    }



    /*
     * Fonction qui permet de récupérer les statistiques de fin de simulation des renards.
     */
    public static String stat(){
        String message = "Nombre de renards au total : " + nbRenardTotal;
        message += "\nNombre de renards morts : " + nbRenardMorts;
        message += "\nNombre de poulet mangés : " + nbPouleMange;
        return message;
    }
}

