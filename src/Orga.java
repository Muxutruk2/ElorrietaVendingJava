import java.util.Scanner;

public class Orga {
    /**
     * Erosketa-orgaren lehen leku librearen ID-a buletatzen du. -1 bueltatzen du,
     * ez badago
     *
     * @param orga Erabiltzailearen orga
     * @return Lehen leku librearen indizea
     */
    public static int organLehenLekuLibrea(int[][] orga) {
        for (int i = 0; i < orga.length; i++) {
            if (orga[i][1] < 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Produktua organ bilatzen du
     * 
     * @param orga        Erabiltzaileak duen orga
     * @param produktu_id Produktuaren id-a
     * @return -1 ez bada existizen, eta bestela orgaren indizea
     */
    public static int organProduktua(int[][] orga, int produktu_id) {
        for (int i = 0; i < orga.length; i++) {
            if (orga[i][0] == produktu_id) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Orgaren elementu guztien prezioak eta kantiateak erabiliz, subtotala
     * kalkulatu
     * 
     * @param orga               Erabiltzaileak duen orga
     * @param produktuak_prezioa Produktuen prezioa
     * @return Subtotala
     */
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
     * Organ dauden produktu desberdinak bueltatzen ditu.
     * 
     * @param orga Erabiltzailearen orga
     * @return Produktu desberdinen kantitatea
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
     * Produktu bat sartu edo eguneratu orga batean.
     * 
     * Funtzio honek honako prozesua egiten du:
     * 1. Aukeratutako produktua existitzen dela egiaztatzen du produktu_kantitatea
     * array-an.
     * 2. Produktua dagoen ala ez organ egiaztatzen du Orga.organProduktua
     * metodoaren bidez.
     * 3. Produktuaren gehieneko kantitatea kalkulatzen du.
     * 4. Kantitatea erabiltzailearen sarreraren arabera ezartzen du:
     * - Gehieneko kantitatea 1 bada, automatikoki ezartzen da 1 edo 0, produktua
     * dagoen ala ez kontuan hartuta.
     * - Ale bat baino gehiago bada, erabiltzaileari galdetzen zaio kantitatea
     * sartzeko.
     * 5. Kantitatea 0 bada:
     * - Produktua orgatik kendu egiten da (baldin badago).
     * 6. Kantitatea > 0 bada:
     * - Produktua jada organ badago, kantitatea eguneratzen da.
     * - Produktua organ ez badago, lehen leku librean gehitzen da.
     * 
     * Erroreak:
     * - Aukeratutako produktua ez badago existitzen, mezua pantailaratzen da eta
     * funtzioa amaitzen da.
     * - Produktua organ sartzeko lekurik ez badago, errore mezua pantailaratzen da.
     * - Produktuaren kantitatea 0 edo gutxiago bada, mezua pantailaratzen da.
     * 
     * @param orga                Bi dimentsioko array-a, orgako produktuak
     *                            gordetzen dituena.
     * @param sc                  Scanner objektua, erabiltzailearen sarrera
     *                            jasotzeko.
     * @param produktu_kantitatea Array bat, non produktu bakoitzaren stock-a
     *                            gordetzen den.
     * @param aukera_produktua_0  Gehitu edo eguneratu nahi den produktuaren IDa
     *                            (indexa produktu_kantitatea array-an).
     */
    public static void sartuProduktuaOrgan(int[][] orga, Scanner sc, int[] produktu_kantitatea,
            int aukera_produktua_0) {

        // Produktua existitzen dela egiaztatu
        if (produktu_kantitatea[aukera_produktua_0] == 0) {
            System.err.println("Aukeratu duzun produktua ez da existitzen.");
            return;
        }

        int produktua_orga_id = Orga.organProduktua(orga, aukera_produktua_0);

        int maxKantitatea = produktu_kantitatea[aukera_produktua_0];
        int kantitatea = 0;

        if (maxKantitatea <= 0) {
            System.err.println("Produktuaren kantitatea 0 edo gitxiago da!");
            return;
        }

        else if (maxKantitatea == 1) {
            // Produktuak ale bakarra badu,
            if (produktua_orga_id == -1) {
                // Produktua ez dago organ
                kantitatea = 1;
            } else {
                // Produktua jada organ dago
                kantitatea = 0;
            }
        } else {
            // Ale bat baino gehiago
            kantitatea = ElorrietaVending.lortuInt(
                    sc,
                    "Sartu produktuaren kantitatea",
                    0,
                    maxKantitatea);
        }

        if (kantitatea == 0) {
            // 0 bada, kendu produktua orgatik, baldin bazegoen
            if (produktua_orga_id != -1) {
                orga[produktua_orga_id] = new int[2];
                orga[produktua_orga_id][0] = -1;
                orga[produktua_orga_id][1] = -1;
                System.out.println("Produktua orgatik kendu da.");
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
