/*
    Projet Java - LU2IN002 - 2022
    TestSimulation.java

    PINHO FERNANDES Enzo - 21107465
    DURBIN Deniz Ali - 21111116
*/

import java.lang.Thread;

public class TestSimulation {
    public static void main(String[] args){
        Simulation simulation = new Simulation(10, 5);


        int nbIteration = 0;
        while(nbIteration < 60){
            simulation.rafraichir();
            System.out.println("Nombre de poules : " + Poule.getNbPouleVivant());
            System.out.println("Nombre de renards : " + Renard.getNbRenardVivant());
            System.out.println("Nombre de rats : " + Rat.getNbRatVivant());
            System.out.println("\n" + Simulation.separateur + "\n");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            nbIteration++;
        }

        System.out.print("\033\143");
        System.out.println(Simulation.stat());
        System.out.println("\nFin de simulation !");
    }
}