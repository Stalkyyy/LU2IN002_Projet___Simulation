/*
    Projet Java - LU2IN002 - 2022
    Simulation.java

    PINHO FERNANDES Enzo - 21107465
    DURBIN Deniz Ali - 21111116
*/

import java.util.ArrayList;

public class Simulation {
    // Initialisation des variables globales
    public static final String couleur = "\u001B[34m";
    public static final String couleurReset = "\u001B[0m";
    public static final String separateur = couleur + "============================================================================" + couleurReset;
    public static final double p_eclosion = 0.10;

    private Terrain terrain;
    private ArrayList<Agent> listAgent;
    private ArrayList<Predateur> listPredateur;
    private ArrayList<Ressource> listRes;


    // Construction de la simulation
    public Simulation(int m, int n){
        terrain = new Terrain(10,20);
        listAgent = Agent.getListAgent();
        listPredateur = Predateur.getListPredateur();
        listRes = new ArrayList<Ressource>();

        // On rajoute, au hasard, m oeufs sur le terrain.
        for(int k = 0; k < m; k++){
            int x = (int)(Math.random() * terrain.nbLignes);
            int y = (int)(Math.random() * terrain.nbColonnes);

            if (terrain.caseEstVide(x, y)){
                Ressource oeuf = new Ressource("Oeufs", 1);
                listRes.add(oeuf);
                terrain.setCase(x, y, oeuf);
            } 
            else{
                Ressource oeuf = terrain.getCase(x, y);
                oeuf.setQuantite(oeuf.getQuantite() + 1);
            }
        }


        // Sur le reste du terrain, on lui rajoute une parcelle d'herbe, initialisé à 5 quantités.
        for(int i = 0; i < terrain.nbLignes; i++){
            for(int j = 0; j < terrain.nbColonnes; j++){
                if (terrain.caseEstVide(i,j)){
                    Ressource herbe = new Ressource("  __  ", 5);
                    listRes.add(herbe);
                    terrain.setCase(i, j, herbe);
                }
            }
        }


        // On crée 5*n poules, 2*n renards et 4*n rats avec des positions aléatoires.
        for(int k = 0; k < n; k++){
            int x = (int)(Math.random() * terrain.nbLignes);
            int y = (int)(Math.random() * terrain.nbColonnes);

            for(int cpt = 0; cpt < 5; cpt++)
                new Poule(x, y, terrain);

            x = (int)(Math.random() * terrain.nbLignes);
            y = (int)(Math.random() * terrain.nbColonnes);

            for(int cpt = 0; cpt < 2; cpt++)
                new Renard(x, y, terrain);

            x = (int)(Math.random() * terrain.nbLignes);
            y = (int)(Math.random() * terrain.nbColonnes);

            for(int cpt = 0; cpt < 4; cpt++)
                new Rat(x, y, terrain);
        }
    }


    // Cette fonction permet de passer à la prochaine itération de la simulation.
    public void rafraichir(){

        // On efface le terminal.
        System.out.print("\033\143");

        System.out.println("\u001B[32m" + "Simulation de la vie des poules et des prédateurs (ici les renards et les rats)." + Simulation.couleurReset);
        System.out.println("\n"+ separateur +"\n");

        // On modifie aléatoirement la direction des agents, et on les fait se déplacer.
        listAgent = new ArrayList<Agent>(Agent.getListAgent());
        for(Agent a : listAgent){
            a.changeDirection();
            int newx = a.getX() + a.getDx();
            int newy = a.getY() + a.getDy();
            a.seDeplacer(newx, newy);
        }




        /*
         * Premièrement, on regarde si la poule est morte. 
         * 
         * Si elle ne l'est pas, elle mange l'herbe au sol s'il y en a, puis elle pondra un oeuf si le hasard l'accepte.
         * Sinon, un bout de viande est déposé sur le terrain à sa position. Elle permettra au renard de se nourrir.
         * 
         */
        ArrayList<Poule> listPoule = new ArrayList<Poule>(Poule.getListPoules());
        for(Poule poule : listPoule){
            if (poule.checkAlive()){
                poule.manger();
                poule.pondre(listRes);
            }

            else {
                Ressource viande = new Ressource("Viande", 1);
                listRes.add(viande);

                // Si la poule est sur un oeuf, au moment de sa chute, l'oeuf se détruit.
                if (terrain.caseEstVide(poule.getX(), poule.getY()))
                    terrain.setCase(poule.getX(), poule.getY(), viande);
                else{
                    Ressource actu = terrain.getCase(poule.getX(), poule.getY());
                    listRes.remove(actu);
                    terrain.videCase(poule.getX(), poule.getY());
                    terrain.setCase(poule.getX(), poule.getY(), viande);
                }
            }
        }

        System.out.println("\n" + separateur + "\n");




        /*
         * On regarde si les prédateurs (Renards et Rats) sont morts.
         * 
         * Si oui, les renards mangent la viande à leur pied s'il y en a, et attaque une poule à proximité s'il y en a une. 
         * Le rat attaque un oeuf à proximité s'il y en a un.
         * Enfin, il y a reproduction des prédateurs si le hasard le veut bien.
         * 
         */
        listPredateur = new ArrayList<Predateur>(Predateur.getListPredateur());
        for(Predateur predateur : listPredateur){
            if (predateur.checkAlive()){
                if (predateur instanceof PredRenard){
                    ((Renard)predateur).manger(listRes);
                    ((Renard)predateur).attack();
                }

                else 
                    ((Rat)predateur).attack(listRes);

                predateur.reproduce();
            }
        }

        System.out.println("\n"+ separateur + "\n");




        /*
         * NOTE : "  __  " CORRESPOND A L'HERBE.
         * 
         * Ici, nous parcourons chaque ressource du terrain.
         * La quantité d'herbe augmente de 1.
         * L'oeuf a une probabilité qu'elle puisse éclore. Si elle éclot, une poule est créee et la quantité d'oeuf baisse de 1 (ou disparait s'il était seul).
         * L'herbe revient sur la case de l'oeuf après éclosion.
         * 
         */
        ArrayList<Ressource> temp = new ArrayList<Ressource>(listRes);
        for(Ressource res : temp){
            if (res.type.equals("  __  "))
                res.setQuantite(res.getQuantite() + 1);
            
            else if (res.type.equals("Oeufs") && (Math.random() < p_eclosion)){
                Poule poule = new Poule(res.getX(), res.getY(), terrain);

                if (res.getQuantite() == 1){
                    int x_herbe = res.getX(), y_herbe = res.getY();
                    terrain.videCase(res.getX(), res.getY());

                    Ressource herbe = new Ressource("  __  ", 3);
                    terrain.setCase(x_herbe, y_herbe, herbe);
                    listRes.remove(res);
                    listRes.add(herbe);
                }

                else { res.setQuantite(res.getQuantite() - 1); }

                Poule.oeufEclos();
                System.out.println("Un oeuf vient d'éclore en (" + poule.getX() + "," + poule.getY() +") !");
            }
        }

        System.out.println("\n"+ separateur + "\n");


        // On affiche le terrain après toutes les modifications apportées.
        terrain.affiche(6);
    }

    

    /*
     * Cette fonction affiche les statistiques à la fin de la simulation.
     */
    public static String stat(){
        String message = separateur + "\n\n";
        message += Poule.stat();
        message += "\n\n" +separateur + "\n\n";
        message += Renard.stat();
        message += "\n\n" + separateur + "\n\n";
        message += Rat.stat();
        message += "\n\n" + separateur + "\n";
        message += "PINHO FERNANDES Enzo - 21107465\n";
        message += "DURBIN Deniz Ali - 21107465";

        return message;
    }
}
