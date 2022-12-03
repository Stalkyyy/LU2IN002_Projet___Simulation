/*
    Projet Java - LU2IN002 - 2022
    Agent.java
    
    PINHO FERNANDES Enzo - 21107465
    DURBIN Deniz Ali - 21111116
*/

import java.util.ArrayList;

public abstract class Agent {
    /*
     * Cette classe abstraite représente les agents en général.
     * Elle possédera la liste de tous les agents crées et actifs de la simulation, ainsi que la probabilité de changement de direction.
     * Chaque agent aura une position x et y, une direction, une énergie et le terrain associé à eux.
     */


    public static final double p_ch_dir = 0.5;
    public static ArrayList<Agent> listAgent = new ArrayList<Agent>();

    protected int x;
    protected int y;
    protected int[] dir;
    protected int energie;
    protected Terrain terrain;

    public Agent(int x, int y, int energie, Terrain terrain){
        this.x = x;
        this.y = y;

        this.dir = new int[2];
        dir[0] = (int)(Math.random() * 3 - 1);
        dir[1] = (int)(Math.random() * 3 - 1);

        this.energie = energie;
        this.terrain = terrain;

        listAgent.add(this);
    }



    /*
     * Permet de calculer la distance euclidienne entre le point (x,y) et l'agent.
     */
    public double distance(int x, int y){
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
    }


    /*
     * Fonction de changement de direction d'un agent.
     * Si le hasard l'accepte, alors la nouvelle direction [dx,dy] de l'agent sera choise aléatoirement entre -1 et 1 compris.
     */
    public void changeDirection(){
        if (Math.random() < p_ch_dir){
            dir[0] = (int)(Math.random() * 3 - 1);
            dir[1] = (int)(Math.random() * 3 - 1);
        }
    }



    /*
     * Fonction permettant de se déplacer.
     * Chaque agent perd un point d'énergie par déplacement.
     * Si un autre agent occupe la position convoitée, il restera à sa place.
     */
    public void seDeplacer(int newx, int newy){
        this.energie--;
        int posx = (newx + terrain.nbLignes) % (terrain.nbLignes);
        int posy = (newy + terrain.nbColonnes) % (terrain.nbColonnes);

        for (Agent agent : listAgent)
            if (agent.x == posx && agent.y == posy)
                return;

        this.x = posx; this.y = posy;
    }


    public static ArrayList<Agent> getListAgent() { return listAgent; }

    
    /*
     * Fonction qui permet de supprimer un agent si son énergie tombe à 0.
     */
    public abstract boolean checkAlive();

    public int getX() { return x; }
    public int getY() { return y; }
    public int getDx() { return dir[0]; }
    public int getDy() { return dir[1]; }
    public int getEnergie() { return energie; }
}
