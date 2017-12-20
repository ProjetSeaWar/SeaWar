package fr.lesprogbretons.seawar.model;

import java.util.ArrayList;

public class Controller {

    public void moveBoat(Grille map,Boat boat, Case destination){

        if(destination instanceof CaseEau) {
            ArrayList<Case> casesDispo = new ArrayList<>();

            map.getCasesDisponibles(boat.getPosition(), boat.getMoveAvalaible(), casesDispo);

            if (casesDispo.contains(destination)) {
                boat.moveBoat(destination);
            } else {
                // TODO : indiquer au joueur qu'il veut aller trop loin
            }
        }

        else {
            // TODO : indiquer au joueur qu'il s'agit d'une case terre
        }
    }

    public static void main(String[] args){
        Grille map = new Grille();

        Boat amiral = new Amiral();


    }
}
