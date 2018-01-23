package fr.lesprogbretons.seawar.model.cases;


import java.io.Serializable;

/**
 * Classe CaseTerre : sous-classe de Case
 */
public class CaseTerre extends Case implements Serializable{

    public CaseTerre(int x, int y) {
        super(x, y);
        phare = false;
    }

    @Override
    public String infoCase() {
        return "    Land";
    }

    @Override
    public String toString() {
        return "Land";
    }
}
