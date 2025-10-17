import java.util.Scanner;

public class Orga {
    /**
     * Organ lehen leku librea. EZ BALDIN BADAGO "-1" BUELTATZEN DU
     * 
     * @param orga
     * @return
     */
    public static int organLehenLekuLibrea(int[][] orga) {
        for (int i = 0; i < orga.length; i++) {
            if (orga[i][1] == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Produktua existitzen bada, orgaren indizea, EZ BALDIN BADAGO "-1" BUELTATZEN
     * DU
     * 
     * @param orga
     * @return
     */
    public static int organProduktua(int[][] orga, int produktu_id) {
        for (int i = 0; i < orga.length; i++) {
            if (orga[i][0] == produktu_id) {
                return i;
            }
        }
        return -1;
    }

    public static double orgaSubtotal(int[][] orga, double[] produktuak_prezioa) {
        double subtotal = 0.0;
        for (int i = 0; i < orga.length; i++) {
            if (orga[i][1] > 0) {
                subtotal += produktuak_prezioa[i] * orga[i][1];
            }
        }
        return subtotal;
    }

    /**
     * Organ dauden produktu desberdin desberdinak bueltatzen ditu
     * 
     * @param orga
     * @return
     */
    public static int organProduktuak(int[][] orga) {
        int count = 0;
        for (int i = 0; i < orga.length; i++) {
            if (orga[i][1] > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * Organ produktu zehatz baten lehenego
     * 
     * @param orga
     * @return
     */
    public static int organLehenProduktua(int[][] orga) {
        for (int i = 0; i < orga.length; i++) {
            if (orga[i][1] == 0) {
                return i;
            }
        }
        return -1;
    }

    public static void sartuProduktuaOrgan(int[][] orga, Scanner sc, int[] produktu_kantitatea,
            int aukera_produktua_0) {
            
        System.out.println("Organ, " + aukera_produktua_0 + " benetako IDa duen produktua sartuko da");

        // Produktua existitzen dela egiaztatu
        if (produktu_kantitatea[aukera_produktua_0] == 0) {
            System.err.println("Aukeratu duzun produktua ez da existitzen.");
            return;
        }

        int produktua_orga_id = Orga.organProduktua(orga, aukera_produktua_0);

        System.out.println("Organ, " + );

        int maxKantitatea = produktu_kantitatea[aukera_produktua_0];

        // Eskatu erabiltzaileari kantitatea beti
        int kantitatea = ElorrietaVending.lortuInt(
                sc,
                "Sartu produktuaren kantitatea",
                0,
                maxKantitatea);

        if (kantitatea == 0) {
            // 0 bada, kendu produktua orgatik, baldin bazegoen
            if (produktua_orga_id != -1) {
                orga[produktua_orga_id] = new int[2];
                System.out.println("Produktua orgatik kendu da.");
            } else {
                System.out.println("Ez da ezer gehitu, kantitatea 0 delako.");
            }
            return;
        }

        if (produktua_orga_id != -1) {
            // Produktua jada organ badago → eguneratu kantitatea
            orga[produktua_orga_id][1] = kantitatea;
            System.out.println("Produktuaren kantitatea eguneratu da (" + kantitatea + " unitate).");
        } else {
            // Produktua ez dago organ → gehitu lehen leku librean
            int orga_id = Orga.organLehenLekuLibrea(orga);
            if (orga_id == -1) {
                System.err.println("Ezin da produktua organ sartu: Orga beteta dago.");
                return;
            }
            orga[orga_id][0] = aukera_produktua_0;
            orga[orga_id][1] = kantitatea;
            System.out.println("Produktua organ gehitu da (" + kantitatea + " unitate).");
        }
    }
}
