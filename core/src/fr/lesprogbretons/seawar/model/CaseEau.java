package fr.lesprogbretons.seawar.model;

public class CaseEau extends Case {


    public CaseEau(int x, int y) {
        super(x, y);
        phare=false;
    }

    public boolean isPhare() {
        return phare;
    }

    public void setPhare(boolean phare) {
        this.phare = phare;
    }
}
