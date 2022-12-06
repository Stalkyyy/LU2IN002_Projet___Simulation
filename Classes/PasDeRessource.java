/*
    Projet Java - LU2IN002 - 2022
    PasDeRessource.java
    
    PINHO FERNANDES Enzo - 21107465
    DURBIN Deniz Ali - 21111116
*/

public class PasDeRessource extends Exception {
  /*
   * Cette classe permet de renvoyer une exception s'il n'y a pas de ressource dans une position donnée du Terrain.
   * En théorie, toutes les cases du tableau de Ressources devrait avoir une ressource, s'il n'y en a pas, on renvoit cette exception.
   */
    
  public PasDeRessource(){
    super("Pas de ressource ici !"); 
  }
  
  public String toString(){
    return "PasDeRessource : Il n'y a pas de ressource ici (pas voulu par le fournisseur), il ne se passe rien !"; 
  }
}
