/*
    Projet Java - LU2IN002 - 2022
    Poule.java
    
    PINHO FERNANDES Enzo - 21107465
    DURBIN Deniz Ali - 21111116
*/

import java.util.ArrayList;

public class Poule extends Agent{
    /*
     * Cette classe représente les poules, qui sont des agents.
     * Elle contient les constantes du temps de la repousse de l'herbe après avoir été mangée, ainsi que la probabilité qu'elle ponde un oeuf.
     * Elle contiendra aussi une liste de toutes les poules crées et actives de la simulation.
     */
    public static final int temps_repousse_herbe = -15;
    public static final double p_pondre = 0.20;
    
    // Statistiques.
    private static int nbPouleTotal = 0;
    private static int nbPouleMorts = 0;
    private static int nbOeufsPondus = 0;

    private static ArrayList<Poule> listPoule = new ArrayList<Poule>();

    private int mois;

    public Poule(int x, int y, Terrain terrain){
        super(x, y, (int)(Math.random()*5 + 10), terrain);
        this.mois = (int)(Math.random()*6);
        nbPouleTotal++;

        listPoule.add(this);
    }



    /*
     * Fonction qui permet de faire pondre une poule.
     * Si la poule à l'âge requis (6 mois), et que le hasard le veut bien, alors elle pondra un oeuf.
     * L'oeuf sera rajouté à la liste de ressources et placé sur le terrain.
     * S'il est déjà dans le terrain, alors on augmente la quantité de 1.
     * Si la position est occupée, on suppose que la poule fait le ménage pour son enfant.
     */
    public void pondre(ArrayList<Ressource> listRes){
        if (mois >= 6 && Math.random() < p_pondre){
            if (terrain.caseEstVide(x, y)){
                Ressource oeuf = new Ressource("Oeufs", 1);
                terrain.setCase(x, y, oeuf);
                listRes.add(oeuf);
                nbOeufsPondus++;
                System.out.println("Un nouvel oeuf a été pondu en (" + x + "," + y + " !");
                return;
            }

            Ressource res = terrain.getCase(x,y);

            if (res.type.equals("Oeufs"))
                res.setQuantite(res.getQuantite() + 1);
            else{
                terrain.videCase(x, y);
                listRes.remove(res);
                Ressource oeuf = new Ressource("Oeufs", 1);
                terrain.setCase(x, y, oeuf);
                listRes.add(oeuf);
            }

            System.out.println("Un nouvel oeuf a été pondu en (" + x + "," + y + ") !");
            nbOeufsPondus++;
        }

        mois++;
    }



    /*
     * Fonction qui permet à la poule de manger l'herbe.
     * On rajoute la quantité d'herbe à ses points d'énergie.
     * L'herbe prendre <temps_repousse_herbe> itérations avant d'être mangeable de nouveau.
     */
    public void manger(){
        if (!terrain.caseEstVide(x, y)){
            Ressource res = terrain.getCase(x, y);
            if (res.type.equals("  __  ") && res.getQuantite() > 0){
                this.energie += res.getQuantite() / 3;
                res.setQuantite(temps_repousse_herbe);
            }
        }
    }

    
    public boolean checkAlive(){
        if (energie <= 0){
            listPoule.remove(this);
            listAgent.remove(this);
            nbPouleMorts++;
            System.out.println("Une poule est morte d'épuisement en (" + x + "," + y + ") !");
            return false;
        }

        return true;
    }



    /*
     * Fonctions utilitaires pour les statistiques, l'ajout dans la liste des Poules...
     */
    public static void pouleTueCpt() { nbPouleMorts++; }
    public static void oeufEclos() { nbPouleTotal++; }

    public static int getNbPouleTotal() { return nbPouleTotal; }
    public static int getNbPouleMorts() { return nbPouleMorts; }
    public static int getNbPouleVivant() { return nbPouleTotal - nbPouleMorts; }
    public static int getNbOeufsPondus() { return nbOeufsPondus; }

    public static ArrayList<Poule> getListPoules() { return listPoule; }

    public static void addInListPoule(Poule p) { listPoule.add(p); }
    public static void removeInListPoule(Poule p) { listPoule.remove(p); }




    /*
     * Fonction qui permet de récupérer les statistiques de fin de simulation des poules.
     */
    public static String stat(){
        String message = "Nombre de poules au total : " + nbPouleTotal;
        message += "\nNombre de poules mortes : " + nbPouleMorts;
        message += "\nNombre d'oeufs pondus : " + nbOeufsPondus;

        return message;
    }
}
