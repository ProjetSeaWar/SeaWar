package fr.lesprogbretons.seawar.model.cases;


/**
 * Classe CaseEau : sous-classe de Case
 */
public class CaseEau extends Case {

    /**
     * Constructeur
     * @param x
     * @param y
     */
    public CaseEau(int x, int y) {
        super(x, y);
        phare = false;
    }

    public boolean isPhare() {
        return phare;
    }

    public void setPhare(boolean phare) {
        this.phare = phare;
    }
}
