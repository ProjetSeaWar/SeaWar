package fr.lesprogbretons.seawar.model.cases;


/**
 * Classe CaseTerre : sous-classe de Case
 */
public class CaseTerre extends Case {
    public CaseTerre(int x, int y) {
        super(x, y);
    }

    public void setPhare(boolean phare) {
        this.phare = false;
    }

}
