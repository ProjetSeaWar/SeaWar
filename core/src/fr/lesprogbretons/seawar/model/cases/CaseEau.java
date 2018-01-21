package fr.lesprogbretons.seawar.model.cases;


import java.io.Serializable;

/**
 * Classe CaseEau : sous-classe de Case
 */
public class CaseEau extends Case implements Serializable{

    /**
     * Constructeur
     * @param x
     * @param y
     */
    public CaseEau(int x, int y) {
        super(x, y);
        phare = false;
    }
}
