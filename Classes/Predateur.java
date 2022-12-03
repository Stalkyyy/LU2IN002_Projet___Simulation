/*
    Projet Java - LU2IN002 - 2022
    Predateur.java
    
    PINHO FERNANDES Enzo - 21107465
    DURBIN Deniz Ali - 21111116
*/

import java.util.ArrayList;

public abstract class Predateur extends Agent{
    /*
     * C'est la classe abstract des Prédateus (Renard et Rat).
     * Elle contiendra la liste de tous les Prédateurs crées et actifs de la simulation, ainsi que la contante de la probabilité de reproduction.
     */

    protected static ArrayList<Predateur> listPredateur = new ArrayList<Predateur>();
    public static final double p_reproduce = 0.025;

    public Predateur(int posx, int posy, int energie, Terrain terrain) { 
        super(posx, posy, energie, terrain); 
        listPredateur.add(this);
    }

    public abstract double probaAttackSuccess(Poule p);
    public abstract void reproduce();

    public static ArrayList<Predateur> getListPredateur() { return listPredateur; }
}
