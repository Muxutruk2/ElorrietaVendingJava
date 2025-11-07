public class Produktuak {
    /**
     * Erakutsi produktuak kantitatea > 0 dutenak
     */
    public static void produktuakErakutsi(String[] mota_izenak, int[] produktu_motak,
            int[] produktu_kantitatea, String[] produktu_izenak, double[] produktu_prezioak) {

        erakutsiTaula(mota_izenak, produktu_motak, produktu_kantitatea, produktu_izenak,
                produktu_prezioak, 1, -1);
    }

    /**
     * Erakutsi mota zehatz bateko produktuak kantitatea > 0 dutenak
     */
    public static void produktuakErakutsiMotak(String[] mota_izenak, int motaID,
            int[] produktu_motak, int[] produktu_kantitatea, String[] produktu_izenak,
            double[] produktu_prezioak) {

        erakutsiTaula(mota_izenak, produktu_motak, produktu_kantitatea, produktu_izenak,
                produktu_prezioak, 2, motaID);
    }

    /**
     * Erakutsi produktuak kantitatea == 0 (agortuta) dituztenak
     */
    public static void produktuakErakutsiAgortuak(String[] mota_izenak, int[] produktu_motak,
            int[] produktu_kantitatea, String[] produktu_izenak, double[] produktu_prezioak) {

        erakutsiTaula(mota_izenak, produktu_motak, produktu_kantitatea, produktu_izenak,
                produktu_prezioak, 3, -1);
    }

    /**
     * Taula moduan erakusteko logika hemen dago.
     * 
     * Modua, 1: Produktu guztiak, 0 baino handiagoa
     * 2: Mota zehatz bateko produktuak, 0 baino handiagoa
     * 3: Admin, Stock bako produktu guztiak
     * 
     * @param mota_izenak
     * @param produktu_motak
     * @param produktu_kantitatea
     * @param produktu_izenak
     * @param produktu_prezioak
     * @param modua               1 = >0 , 2 = motaID eta >0, 3 = ==0
     * @param motaID              motaID balioa modua==2 bada
     */
    private static void erakutsiTaula(
            String[] mota_izenak, int[] produktu_motak,
            int[] produktu_kantitatea, String[] produktu_izenak, double[] produktu_prezioak,
            int modua, int motaID) {

        int PRODUKTU_KOPURUA = produktu_izenak.length;
        String[][] taula = new String[PRODUKTU_KOPURUA + 1][5];

        taula[0][0] = "IDa";
        taula[0][1] = "Izena";
        taula[0][2] = "Prezioa";
        taula[0][3] = "Kant.";
        taula[0][4] = "Mota";

        int index = 0;
        for (int i = 0; i < PRODUKTU_KOPURUA; i++) {
            boolean sartu = false;

            if (modua == 1 && produktu_kantitatea[i] > 0) {
                sartu = true;
            } else if (modua == 2 && produktu_kantitatea[i] > 0 && produktu_motak[i] == motaID) {
                sartu = true;
            } else if (modua == 3 && produktu_kantitatea[i] == 0) {
                sartu = true;
            }

            if (sartu) {
                taula[index + 1][0] = String.valueOf(i + 1);
                taula[index + 1][1] = produktu_izenak[i];
                taula[index + 1][2] = String.format("%.2f", produktu_prezioak[i]);
                taula[index + 1][3] = String.valueOf(produktu_kantitatea[i]);
                taula[index + 1][4] = mota_izenak[produktu_motak[i]];
                index++;
            }
        }

        // Zutabe bakoitzeko max luzera
        int[] max = new int[5];
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i <= index; i++) {
                if (taula[i][j] != null && taula[i][j].length() > max[j]) {
                    max[j] = taula[i][j].length();
                }
            }
        }

        // Marra
        int zabalera = 1;
        for (int l : max)
            zabalera += l + 3;
        String marra = "-".repeat(zabalera);

        System.out.println(marra);

        // Inprimatu taula
        for (int i = 0; i <= index; i++) {
            System.out.print("|");
            for (int j = 0; j < 5; j++) {
                System.out.printf(" %-" + max[j] + "s |", taula[i][j]);
            }
            System.out.println();
            if (i == 0 || i == index)
                System.out.println(marra);
        }
    }

    /**
     * Produktuen array batean lehenengo leku librea bilatzen du.
     * Leku librea produktua kantitatea -1 duen indexa da.
     *
     * @param produktu_kantitatea Produktu bakoitzaren stock-a
     * @return Lehengo leku librea duen indexa, edo -1 bueltatzen du lekua aurkitu
     *         ez bada
     */
    public static int produktuakLekuLibrea(int[] produktu_kantitatea) {
        for (int i = 0; i < produktu_kantitatea.length; i++) {
            if (produktu_kantitatea[i] < 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Zenbat produktu existitzen diren makinan
     * 
     * @param produktu_kantitatea Produktuen kantitatearen arraya
     * @return Produktu kopurua
     */
    public static int produktuKopurua(int[] produktu_kantitatea) {
        int count = 0;
        for (int i = 0; i < produktu_kantitatea.length; i++) {
            if (produktu_kantitatea[i] > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * Zenbat produktu existitzen diren makinan stock gabe
     * 
     * @param produktu_kantitatea Produktuen kantitatearen arraya
     * @return Stock gabeko produktu kopurua
     */
    public static int produktuKopuruaStockGabe(int[] produktu_kantitatea) {
        int count = 0;
        for (int i = 0; i < produktu_kantitatea.length; i++) {
            if (produktu_kantitatea[i] == 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * Zenbat produktu existitzen diren makinan, produktu zehatz batekin
     * 
     * @param produktu_kantitatea Produktuen kantitatearen arraya
     * @param produktu_motak      Produktuen moten arraya
     * @param mota                Zer mota
     * @return Produktu kopurua
     */
    public static int produktuKopuruaMota(int[] produktu_kantitatea, int[] produktu_motak, int mota) {
        int count = 0;
        for (int i = 0; i < produktu_kantitatea.length; i++) {
            if (produktu_kantitatea[i] > 0 && produktu_motak[i] == mota) {
                count++;
            }
        }
        return count;
    }

    /**
     * Orgaren arabera, produktu_kantitatetik kendu erosi diren produktuak
     * 
     * Orga bi dimentsioko array bat da, non:
     * - orga[x][0] = produktuaren IDa
     * - orga[x][1] = produktuaren kantitatea organ
     * 
     * @param orga                Erabiltzailearen orga
     * @param produktu_kantitatea Produktuen kantitateen arraya
     */
    public static void produktuakErosiOrgatik(int[][] orga, int[] produktu_kantitatea) {
        for (int i = 0; i < orga.length; i++) {
            int orga_prod_id = orga[i][0];
            int orga_prod_kopurua = orga[i][1];

            if (orga_prod_kopurua <= 0) {
                continue;
            }

            if (orga_prod_kopurua > produktu_kantitatea[orga_prod_id]) {
                System.err.println("Organ dagoen kantitatea produktuaren lekuan dagoen kantitatea baino handiagoa da");
                System.err.println("Orga id: " + i);
                System.err.println("Produktu id: " + orga_prod_id);
                System.err.println("Orga Produktu kop.: " + orga_prod_kopurua);
                System.err.println("Produktu kop.: " + produktu_kantitatea[orga_prod_id]);
                continue;
            }

            produktu_kantitatea[orga_prod_id] -= orga_prod_kopurua;
        }
    }
}