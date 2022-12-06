/*
    Projet Java - LU2IN002 - 2022
    PasDeRessource.java
    
    PINHO FERNANDES Enzo - 21107465
    DURBIN Deniz Ali - 21111116
*/

public class PasDeRessourceException extends Exception {
  /*
   * Cette classe permet de créer une exception si une case ne possède pas une ressource.
   * En effet, nous voulions faire en sorte que chaque case à soit une herbe, un oeuf ou une viande.
   */
  
  public PasDeRessourceException(){
    super("Pas de ressource ici !"); 
  }
  
  public String toString(){
    return "PasDeRessource : Il n'y a pas de ressource ici (pas voulu par le fournisseur), il ne se passe rien !"; 
  }
}
