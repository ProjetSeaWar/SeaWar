package fr.lesprogbretons.seawar.ia;

import static fr.lesprogbretons.seawar.SeaWar.partie;

public abstract class AbstractIA extends Thread {

    public AbstractIA(String ia) {
        super(ia);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // On réinterromp pour bien pouvoir sortir de la boucle
                Thread.currentThread().interrupt();
                break;
            }

            if (partie.isPlayer2()) {
                getCoup();
            }
        }
    }

    public abstract void getCoup();
}
