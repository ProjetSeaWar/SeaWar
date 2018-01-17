package fr.lesprogbretons.seawar.model;

import java.util.ArrayList;

public class Grille {
    protected ArrayList<Boat> bateaux1;
    protected ArrayList<Boat> bateaux2;
    protected Case tableau[][];
    protected int hauteur;
    protected int largeur;

    public Grille(int hauteur, int largeur) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        bateaux1 = new ArrayList<>();
        bateaux2 = new ArrayList<>();
        tableau = new Case[hauteur][largeur];

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                tableau[i][j] = new CaseEau(i, j);
            }
        }
    }

    public Case getCase(int hauteur, int largeur) {
        return tableau[hauteur][largeur];
    }

    public Case getCaseHaut(Case c) {
        int x;
        int y;
        Case cas;
        x = c.getX();
        y = c.getY();
        cas = getCase((x + 1), y);
        return cas;
    }

    public Case getCaseBas(Case c) {
        int x;
        int y;
        Case cas;
        x = c.getX();
        y = c.getY();
        cas = getCase((x - 1), y);
        return cas;
    }

    public Case getCaseDroiteh(Case c) {
        int x;
        int y;
        Case cas;
        x = c.getX();
        y = c.getY();
        if (y % 2 == 0) {
            cas = getCase(x, (y + 1));
        } else {
            cas = getCase((x + 1), (y + 1));
        }
        return cas;
    }

    public Case getCaseDroiteb(Case c) {
        int x;
        int y;
        Case cas;
        x = c.getX();
        y = c.getY();
        if (y % 2 == 0) {
            cas = getCase((x - 1), (y + 1));
        } else {
            cas = getCase(x, (y + 1));
        }
        return cas;
    }

    public Case getCaseGaucheh(Case c) {
        int x;
        int y;
        Case cas;
        x = c.getX();
        y = c.getY();
        if (y % 2 == 0) {
            cas = getCase(x, (y - 1));
        } else {
            cas = getCase((x + 1), (y - 1));
        }
        return cas;
    }

    public Case getCaseGaucheb(Case c) {
        int x;
        int y;
        Case cas;
        x = c.getX();
        y = c.getY();
        if (y % 2 == 0) {
            cas = getCase((x - 1), (y - 1));
        } else {
            cas = getCase(x, (y - 1));
        }
        return cas;
    }

    public ArrayList<Case> getCasesDisponible(Case c, int range) {
        ArrayList<Case> tab = new ArrayList<>();

        if (!(tab.contains(c)) && !(c instanceof CaseTerre)) {
            tab.add(c);
        }

        if (range != 0 && !(c instanceof CaseTerre)) {
            if (c.getX() > 0) {
                tab.addAll(getCasesDisponible(getCaseBas(c), (range - 1)));
            }
            if (c.getY() < largeur - 1) {
                if ((c.getY() % 2 == 0 && c.getX() > 0) || c.getY() % 2 == 1) {
                    tab.addAll(getCasesDisponible(getCaseDroiteb(c), (range - 1)));
                }
            }
            if (c.getY() < largeur - 1) {
                if ((c.getY() % 2 == 1 && c.getX() < hauteur - 1) || c.getY() % 2 == 0) {
                    tab.addAll(getCasesDisponible(getCaseDroiteh(c), (range - 1)));
                }
            }
            if (c.getY() > 0) {
                if ((c.getY() % 2 == 0 && c.getX() > 0) || c.getY() % 2 == 1) {
                    tab.addAll(getCasesDisponible(getCaseGaucheb(c), (range - 1)));
                }
            }
            if (c.getY() > 0) {
                if ((c.getY() % 2 == 1 && c.getX() < hauteur - 1) || c.getY() % 2 == 0) {
                    tab.addAll(getCasesDisponible(getCaseGaucheh(c), (range - 1)));
                }
            }
            if (c.getX() < hauteur - 1) {
                tab.addAll(getCasesDisponible(getCaseHaut(c), (range - 1)));
            }
        }
        return tab;
    }

    public ArrayList<Case> getCasesDisponibles(Case c, int range) {
        ArrayList<Case> tab = new ArrayList<>(getCasesDisponible(c, range));
        tab.remove(0);
        return tab;
    }

    public boolean casePossedeBateaux(Case c) {
        for (Boat aBateaux1 : bateaux1) {
            if (aBateaux1.getPosition().equals(c)) {
                return true;
            }
        }

        for (Boat aBateaux2 : bateaux2) {
            if (aBateaux2.getPosition().equals(c)) {
                return true;
            }
        }

        return false;
    }

    public boolean casePossedeBateau(Case c, Player joueur) {
        if (joueur.getNumber() == 1) {
            for (Boat aBateaux1 : bateaux1) {
                if (aBateaux1.getPosition().equals(c)) {
                    return true;
                }
            }
        } else {
            for (Boat aBateaux2 : bateaux2) {
                if (aBateaux2.getPosition().equals(c)) {
                    return true;
                }
            }
        }

        return false;
    }

    public Boat bateauSurCase(Case c) {
        for (Boat aBateaux1 : bateaux1) {
            if (aBateaux1.getPosition().equals(c)) {
                return aBateaux1;
            }
        }

        for (Boat aBateaux2 : bateaux2) {
            if (aBateaux2.getPosition().equals(c)) {
                return aBateaux2;
            }
        }

        return null;
    }

    /* TODO : toString de la grille
    @Override
    public String toString() {
        return "Grille{" +
                "tableau=" +
                '}';
    }*/

    public int distanceCase(Case c1, Case c2) {
        return 0;
    }

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

    public void getCasesPortees(Case position, Boat bateauSelectionne, ArrayList<Case> casesPorteeTir) {
    }

    public void prendPhare(Case c, Player joueur) {
        if (c.getPossedePhare() == null) {
            c.setPossedePhare(joueur);
            joueur.setPharesPossedes(joueur.getPharesPossedes() + 1);
        } else if (!(c.getPossedePhare().equals(joueur))) {
            c.getPossedePhare().setPharesPossedes(c.getPossedePhare().getPharesPossedes() - 1);
            c.setPossedePhare(joueur);
            joueur.setPharesPossedes(joueur.getPharesPossedes() + 1);
        }
    }
}
