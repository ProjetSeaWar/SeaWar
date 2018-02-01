package fr.lesprogbretons.seawar.ia;

import static fr.lesprogbretons.seawar.SeaWar.partie;

public class AbstractIA extends Thread {

    public AbstractIA(String ia) {
        super(ia);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (partie.isPlayer2()) {
                getCoup();
            }
        }
    }

    public void getCoup() {

    }
}
