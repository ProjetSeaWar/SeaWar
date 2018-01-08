package fr.lesprogbretons.seawar.model;

import java.util.*;

public class Jeu {
    public void start (Grille g){
        boolean jeuFini=false,tourFini=false;
        int joueur=1;
        int mode;    //1:selection 2:deplacement

        Iterator<Boat> it1 = g.bateaux1.iterator();
        Iterator<Boat> it2 = g.bateaux2.iterator();

        while(!jeuFini){
            if(joueur==1){
                while(tourFini){


                    while (it1.hasNext()) {
                        Boat b = it1.next();

                    }

                }
            }

        }
    }
    public static void main(){

    }
}
