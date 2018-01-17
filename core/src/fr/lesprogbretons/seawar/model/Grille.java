package fr.lesprogbretons.seawar.model;

import java.util.ArrayList;

public class Grille {
    protected Player joueur1 = new Player(1);

    public Player getJoueur1() {
        return joueur1;
    }

    public void setJoueur1(Player joueur1) {
        this.joueur1 = joueur1;
    }

    public Player getJoueur2() {
        return joueur2;
    }

    public void setJoueur2(Player joueur2) {
        this.joueur2 = joueur2;
    }

    protected Player joueur2 = new Player(2);
    protected ArrayList<Boat> bateaux1;
    protected ArrayList<Boat> bateaux2;
    protected Case tableau[][];
    protected int hauteur;
    protected int largeur;

    public Grille(int hauteur,int largeur){
        this.hauteur = hauteur;
        this.largeur = largeur;
        bateaux1=new ArrayList<>();
        bateaux2=new ArrayList<>();
        tableau=new Case[hauteur][largeur];

        for(int i=0;i<hauteur;i++){
            for(int j=0;j<largeur;j++){
                tableau[i][j]=new CaseEau(i,j);
            }
        }
    }

    public Case getCase(int hauteur,int largeur){
        return tableau[hauteur][largeur];
    }

    public Case getCaseNord(Case c){
        if(c.equals(null)){
            return null;
        }
        int x;
        int y;
        Case cas;
        x=c.getX();
        y=c.getY();
        if(x+1>=0 && x+1<=hauteur) {
            cas = getCase((x + 1), y);
            return cas;
        }
        return null;
    }
    public Case getCaseSud(Case c){
        if(c.equals(null)){
            return null;
        }
        int x;
        int y;
        Case cas;
        x=c.getX();
        y=c.getY();
        if(x-1>=0 && x-1<=hauteur) {
            cas = getCase((x - 1), y);
            return cas;
        }
        return null;
    }
    public Case getCaseNordEst(Case c){
        if(c.equals(null)){
            return null;
        }
        int x;
        int y;
        Case cas;
        x=c.getX();
        y=c.getY();
        if (y%2==0){
            if(y+1>=0 && y+1<=largeur) {
                cas = getCase(x, (y + 1));
                return cas;
            }
        }
        else {
            if(x+1>=0 && x+1<=hauteur && y+1>=0 && y+1<=hauteur) {
                cas = getCase((x + 1), (y + 1));
                return cas;
            }
        }
        return null;
    }
    public Case getCaseSudEst(Case c){
        if(c.equals(null)){
            return null;
        }
        int x;
        int y;
        Case cas;
        x=c.getX();
        y=c.getY();
        if (y%2==0){
            if(x-1>=0 && x-1<=0 && y+1>=0 && y+1<=largeur) {
                cas = getCase((x - 1), (y + 1));
                return cas;
            }
        }
        else {
            if(y-1>=0 && y+1<=largeur) {
                cas = getCase(x, (y + 1));
                return cas;
            }
        }
        return null;
    }
    public Case getCaseNordOuest(Case c){
        if (c.equals(null)) {
            return null;
        }
        int x;
        int y;
        Case cas;
        x=c.getX();
        y=c.getY();
        if (y%2==0){
            if(y-1>=0 && y-1<=largeur) {
                cas = getCase(x, (y - 1));
                return cas;
            }
        }
        else {
            if(x+1>=0 && x+1<=hauteur && y-1>=0 && y-1<=hauteur) {
                cas = getCase((x + 1), (y - 1));
                return cas;
            }
        }
        return null;
    }
    public Case getCaseSudOuest(Case c){
        if(c.equals(null)){
            return null;
        }
        int x;
        int y;
        Case cas;
        x=c.getX();
        y=c.getY();
        if (y%2==0){
            if(x-1>=0 && x-1<=hauteur && y-1>=0 && y-1<=largeur) {
                cas = getCase((x - 1), (y - 1));
                return cas;
            }
        }
        else {
            if(y-1>=0 && y-1<=hauteur) {
                cas = getCase(x, (y - 1));
                return cas;
            }
        }
        return null;
    }

    public void getCasesDisponible(Case c, int range, ArrayList<Case> tab){
        if(!(tab.contains(c)) &&!(c  instanceof CaseTerre)  &&!(casePossedeBateaux(c))) {
            tab.add(c);
        }

        if (range != 0 && !(c  instanceof CaseTerre) &&!(casePossedeBateaux(c))) {
            if(c.getX()>0) {
                getCasesDisponible(getCaseSud(c), (range - 1), tab);
            }
            if(c.getY()<largeur-1) {
                if((c.getY()%2==0 && c.getX()>0)|| c.getY()%2==1) {
                    getCasesDisponible(getCaseSudEst(c), (range - 1), tab);
                }
            }
            if(c.getY()<largeur-1) {
                if ((c.getY() % 2 == 1 && c.getX() < hauteur-1)|| c.getY()%2==0) {
                    getCasesDisponible(getCaseNordEst(c), (range - 1), tab);
                }
            }
            if(c.getY()>0) {
                if ((c.getY() % 2 == 0 && c.getX() > 0)|| c.getY()%2==1) {
                    getCasesDisponible(getCaseSudOuest(c), (range - 1), tab);
                }
            }
            if(c.getY()>0) {
                if ((c.getY() % 2 == 1 && c.getX() <hauteur-1) || c.getY()%2==0 ) {
                    getCasesDisponible(getCaseNordOuest(c), (range - 1), tab);
                }
            }
            if(c.getX()<hauteur-1) {
                getCasesDisponible(getCaseNord(c), (range - 1), tab);
            }
        }
    }

    public void getCasesDisponibles(Case c, int range, ArrayList<Case> tab){
        getCasesDisponible(c,range,tab);
        tab.remove(0);
    }

    public boolean casePossedeBateaux(Case c){
        for(int i =0;i<bateaux1.size();i++){
            if(bateaux1.get(i).getPosition().equals(c)){
                return true;
            }
        }

        for(int i =0;i<bateaux2.size();i++){
            if(bateaux2.get(i).getPosition().equals(c)){
                return true;
            }
        }

        return false;
    }

    public boolean casePossedeBateau(Case c, Player joueur){
        if(joueur.getNumber()==1){
            for(int i =0;i<bateaux1.size();i++){
                if(bateaux1.get(i).getPosition().equals(c)){
                    return true;
                }
            }
        }

        else{
            for(int i =0;i<bateaux2.size();i++){
                if(bateaux2.get(i).getPosition().equals(c)){
                    return true;
                }
            }
        }

        return false;
    }

    public Boat bateauSurCase(Case c){
        for(int i = 0 ; i<bateaux1.size();i++){
            if(bateaux1.get(i).getPosition().equals(c)){
                return bateaux1.get(i);
            }
        }

        for(int i =0;i<bateaux2.size();i++){
            if(bateaux2.get(i).getPosition().equals(c)){
                return bateaux2.get(i);
            }
        }

        return null;
    }



    public int distanceCase(Case c1, Case c2){
        return 0;
    }

    /* TODO : toString de la grille
    @Override
    public String toString() {
        return "Grille{" +
                "tableau=" +
                '}';
    }*/

    public ArrayList<Boat> getBateaux1() {
        return bateaux1;
    }

    public void setBateaux1(ArrayList<Boat> bateaux1) {
        this.bateaux1 = bateaux1;
    }

    public ArrayList<Boat> getBateaux2() {
        return bateaux2;
    }

    public void setBateaux2(ArrayList<Boat> bateaux2) {
        this.bateaux2 = bateaux2;
    }

    public void getCasesPortees(Boat bateauSelectionne, ArrayList<Case> casesPorteeTir) {
        if(bateauSelectionne instanceof Amiral){
            if(bateauSelectionne.getCanonSelectionne()==1){
                if(bateauSelectionne.getOrientation()==Orientation.NORD){
                    casesPorteeTir.add(this.getCaseNord(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(this.getCaseNord(getCaseNord(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(this.getCaseNord(getCaseNord(getCaseNord(bateauSelectionne.getPosition()))));
                    casesPorteeTir.add(this.getCaseNord(getCaseNord(getCaseNord(getCaseNord(bateauSelectionne.getPosition())))));
                }

                else if(bateauSelectionne.getOrientation()==Orientation.NORDEST){
                    casesPorteeTir.add(this.getCaseNordEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(this.getCaseNordEst(getCaseNordEst(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(this.getCaseNordEst(getCaseNordEst(getCaseNordEst(bateauSelectionne.getPosition()))));
                    casesPorteeTir.add(this.getCaseNordEst(getCaseNordEst(getCaseNordEst(getCaseNordEst(bateauSelectionne.getPosition())))));
                }

                else if(bateauSelectionne.getOrientation()==Orientation.SUDEST){
                    casesPorteeTir.add(this.getCaseSudEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(this.getCaseSudEst(getCaseSudEst(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(this.getCaseSudEst(getCaseSudEst(getCaseSudEst(bateauSelectionne.getPosition()))));
                    casesPorteeTir.add(this.getCaseSudEst(getCaseSudEst(getCaseSudEst(getCaseSudEst(bateauSelectionne.getPosition())))));
                }

                else if(bateauSelectionne.getOrientation()==Orientation.SUD){
                    casesPorteeTir.add(this.getCaseSud(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(this.getCaseSud(getCaseSud(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(this.getCaseSud(getCaseSud(getCaseSud(bateauSelectionne.getPosition()))));
                    casesPorteeTir.add(this.getCaseSud(getCaseSud(getCaseSud(getCaseSud(bateauSelectionne.getPosition())))));
                }

                else if(bateauSelectionne.getOrientation()==Orientation.SUDOUEST){
                    casesPorteeTir.add(this.getCaseSudOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(this.getCaseSudEst(getCaseSudEst(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(this.getCaseSudEst(getCaseSudEst(getCaseSudEst(bateauSelectionne.getPosition()))));
                    casesPorteeTir.add(this.getCaseSudEst(getCaseSudEst(getCaseSudEst(getCaseSudEst(bateauSelectionne.getPosition())))));
                }

                else if(bateauSelectionne.getOrientation()==Orientation.NORDOUEST){
                    casesPorteeTir.add(this.getCaseNordEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(this.getCaseNordEst(getCaseNordEst(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(this.getCaseNordEst(getCaseNordEst(getCaseNordEst(bateauSelectionne.getPosition()))));
                    casesPorteeTir.add(this.getCaseNordEst(getCaseNordEst(getCaseNordEst(getCaseNordEst(bateauSelectionne.getPosition())))));
                }
            }

            else {
                if(bateauSelectionne.getOrientation()==Orientation.NORD){
                    casesPorteeTir.add(this.getCaseNord(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(this.getCaseNord(getCaseNord(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(this.getCaseNordEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(this.getCaseNordOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(this.getCaseNord(getCaseNordEst(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(this.getCaseNord(getCaseNordOuest(bateauSelectionne.getPosition())));
                }

                else if(bateauSelectionne.getOrientation()==Orientation.NORDEST){
                    casesPorteeTir.add(this.getCaseNordEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(this.getCaseNordEst(getCaseNordEst(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(this.getCaseNord(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(this.getCaseNord(getCaseNordEst(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(this.getCaseSudEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(this.getCaseNordEst(getCaseSudEst(bateauSelectionne.getPosition())));
                }

                else if(bateauSelectionne.getOrientation()==Orientation.SUDEST){
                    casesPorteeTir.add(getCaseSudEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudEst(getCaseSudEst(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(getCaseSud(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSud(getCaseSudEst(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(getCaseNordEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudEst(getCaseNordEst(bateauSelectionne.getPosition())));
                }

                else if(bateauSelectionne.getOrientation()==Orientation.SUD){
                    casesPorteeTir.add(getCaseSud(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSud(getCaseSud(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(getCaseSudOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSud(getCaseSudOuest(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(getCaseSud(getCaseSudEst(bateauSelectionne.getPosition())));
                }

                else if(bateauSelectionne.getOrientation()==Orientation.SUDOUEST){
                    casesPorteeTir.add(getCaseSudOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudOuest(getCaseSudOuest(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(getCaseSud(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudOuest(getCaseSud(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(getCaseNordOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudOuest(getCaseNordOuest(bateauSelectionne.getPosition())));
                }
                else if(bateauSelectionne.getOrientation()==Orientation.NORDOUEST){
                    casesPorteeTir.add(getCaseSudOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseNordOuest(getCaseSudOuest(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(getCaseNordOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseNordOuest(getCaseNordOuest(bateauSelectionne.getPosition())));
                    casesPorteeTir.add(getCaseNord(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseNordOuest(getCaseNord(bateauSelectionne.getPosition())));
                }
            }
        }
        else if(bateauSelectionne instanceof Fregate){
            if(bateauSelectionne.getCanonSelectionne()==1){
                if(bateauSelectionne.getOrientation() == Orientation.NORD){
                    casesPorteeTir.add(getCaseNordOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseNord(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseNordEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseNord(getCaseNord(bateauSelectionne.getPosition())));
                }
                else if(bateauSelectionne.getOrientation() == Orientation.NORDEST){
                    casesPorteeTir.add(getCaseNordOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseNord(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseNordEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSud(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseNordEst(getCaseNordEst(bateauSelectionne.getPosition())));
                }
                else if(bateauSelectionne.getOrientation() == Orientation.SUDEST){
                    casesPorteeTir.add(getCaseSudOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseNord(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseNordEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSud(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudEst(getCaseSudEst(bateauSelectionne.getPosition())));
                }
                else if(bateauSelectionne.getOrientation() == Orientation.SUD){
                    casesPorteeTir.add(getCaseNordOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseNordEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSud(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSud(getCaseSud(bateauSelectionne.getPosition())));
                }
                else if(bateauSelectionne.getOrientation() == Orientation.SUDOUEST){
                    casesPorteeTir.add(getCaseNordOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseNord(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSud(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudOuest(getCaseSudOuest(bateauSelectionne.getPosition())));
                }
                else if(bateauSelectionne.getOrientation() == Orientation.NORDOUEST){
                    casesPorteeTir.add(getCaseNordOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseNord(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseNordEst(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSudOuest(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseSud(bateauSelectionne.getPosition()));
                    casesPorteeTir.add(getCaseNordOuest(getCaseNordOuest(bateauSelectionne.getPosition())));
                }
            }else {
                casesPorteeTir.add(getCaseNordOuest(bateauSelectionne.getPosition()));
                casesPorteeTir.add(getCaseNord(bateauSelectionne.getPosition()));
                casesPorteeTir.add(getCaseNordEst(bateauSelectionne.getPosition()));
                casesPorteeTir.add(getCaseSudOuest(bateauSelectionne.getPosition()));
                casesPorteeTir.add(getCaseSud(bateauSelectionne.getPosition()));
                casesPorteeTir.add(getCaseSudEst(bateauSelectionne.getPosition()));
            }
        }
    }

    public void prendPhare(Case c, Player joueur){
        if(c.getPossedePhare().equals(null)){
            c.setPossedePhare(joueur);
            joueur.setPharesPossedes(joueur.getPharesPossedes()+1);
        }

        else if(!(c.getPossedePhare().equals(joueur))){
            c.getPossedePhare().setPharesPossedes(c.getPossedePhare().getPharesPossedes()-1);
            c.setPossedePhare(joueur);
            joueur.setPharesPossedes(joueur.getPharesPossedes()+1);
        }
    }

    public static void main(String[] args) {
        Grille grille = new DefaultMap();
        //System.out.println(grille);

        Case a = grille.getCase(0, 1);


      /*System.out.println(a);
        System.out.println(grille.getCaseSud(a));
        System.out.println(grille.getCaseSudOuest(a));
        System.out.println(grille.getCaseNordOuest(a));
        System.out.println(grille.getCaseNord(a));
        System.out.println(grille.getCaseNordEst(a));
        System.out.println(grille.getCaseSudEst(a));*/

        ArrayList<Case> tab = new ArrayList<>();

        grille.getCasesDisponibles(a, 3, tab);

        for(int i=0; i<13;i++){
            System.out.println(tab.get(i));
        }

    }
}
