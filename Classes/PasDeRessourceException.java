/*
    Projet Java - LU2IN002 - 2022
    PasDeRessource.java
    
    PINHO FERNANDES Enzo - 21107465
    DURBIN Deniz Ali - 21111116
*/

public class PasDeRessourceException extends Exception {
  public PasDeRessourceException(){
    super("Pas de ressource ici !"); 
  }
  
  public String toString(){
    return "PasDeRessource : Il n'y a pas de ressource ici (pas voulu par le fournisseur), il ne se passe rien !"; 
  }
}
