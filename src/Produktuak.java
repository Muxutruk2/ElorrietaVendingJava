public class Produktuak {
    /**
     * Produktu guztiak erakusten ditu taula formatuan.
     * Soilik kantitatea > 0 duten produktuak agertzen dira.
     * Taula honetan honako zutabeak daude:
     * - IDa: Produktuaren identifikatzailea (index + 1)
     * - Izena: Produktuaren izena
     * - Prezioa: Produktuaren prezioa (bi hamartarrekin formateatua)
     * - Kant.: Produktuaren kantitatea stockean
     * - Mota: Produktuaren motaren izena
     * 
     * Taula modu bisual batean inprimatzen da, soilik '|' eta '-' karaktereak
     * erabilita.
     *
     * @param mota_izenak         Produktu mota bakoitzaren izenak
     * @param produktu_motak      Produktu bakoitzaren motaren IDa
     * @param produktu_kantitatea Produktu bakoitzaren stock-a (kantitatea)
     * @param produktu_izenak     Produktu bakoitzaren izenak
     * @param produktu_prezioak   Produktu bakoitzaren prezioak
     */
    public static void produktuakErakutsi(String[] mota_izenak, int[] produktu_motak, int[] produktu_kantitatea,
            String[] produktu_izenak, double[] produktu_prezioak) {
        int PRODUKTU_KOPURUA = produktu_izenak.length;

        // Datuen taula prestatu (ID, Izena, Prezioa, Kantitatea, Mota)
        String[][] produktuak_tabla = new String[PRODUKTU_KOPURUA + 1][5];

        // Goiburuak (IDa, Izena, Prezioa, Kantitatea)
        produktuak_tabla[0][0] = "IDa";
        produktuak_tabla[0][1] = "Izena";
        produktuak_tabla[0][2] = "Prezioa";
        produktuak_tabla[0][3] = "Kant.";
        produktuak_tabla[0][4] = "Mota";

        // Datuak sartu

        int index = 0;
        for (int i = 0; i < PRODUKTU_KOPURUA; i++) {
            if (produktu_kantitatea[i] > 0) {
                produktuak_tabla[index + 1][0] = String.valueOf(i + 1);
                produktuak_tabla[index + 1][1] = produktu_izenak[i];
                produktuak_tabla[index + 1][2] = String.format("%.2f", produktu_prezioak[i]);
                produktuak_tabla[index + 1][3] = String.valueOf(produktu_kantitatea[i]);
                produktuak_tabla[index + 1][4] = String.valueOf(mota_izenak[produktu_motak[i]]);
                index++;
            }
        }

        // Lortu zutabe bakoitzeko luzera maximoa
        int[] zutabeMaxLuzera = new int[5];
        for (int j = 0; j < 5; j++) {
            int max = 0;
            for (int i = 0; i < PRODUKTU_KOPURUA + 1; i++) {
                if (produktuak_tabla[i][j] == null) {
                    break;
                }
                if (produktuak_tabla[i][j].length() > max) {
                    max = produktuak_tabla[i][j].length();
                }
            }
            zutabeMaxLuzera[j] = max;
        }

        // Marra (separadorea) kalkulatu
        int taulaZabalera = 0;
        for (int luzera : zutabeMaxLuzera) {
            taulaZabalera += luzera + 3; // " | ".lenght == 3
        }
        taulaZabalera += 1; // azken "|"
        String marra = "-".repeat(taulaZabalera);

        System.out.println(marra);

        // Erakutsi taula modura, soilik | eta - erabiliz
        for (int i = 0; i < PRODUKTU_KOPURUA + 1; i++) {
            if (produktuak_tabla[i][0] == null) {
                System.out.println(marra);
                break;
            }
            System.out.print("|");
            for (int j = 0; j < 5; j++) {
                String gelaxka = produktuak_tabla[i][j];
                // Gelaxka justifikatu
                System.out.printf(" %-" + zutabeMaxLuzera[j] + "s |", gelaxka);
            }
            System.out.println();

            // Goiburuaren azpian eta bukaeran marra jarri
            if (i == 0 || i == PRODUKTU_KOPURUA) {
                System.out.println(marra);
            }
        }
    }

    /**
     * Produktuen array batean lehenengo leku librea bilatzen du.
     * Leku librea produktua kantitatea 0 duen indexa da.
     *
     * @param produktu_kantitatea Produktu bakoitzaren stock-a
     * @return Lehengo leku librea duen indexa, edo -1 bueltatzen du lekua aurkitu
     *         ez bada
     */
    public static int produktuakLekuLibrea(int[] produktu_kantitatea) {
        for (int i = 0; i < produktu_kantitatea.length; i++) {
            if (produktu_kantitatea[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Mota zehatz bateko produktuak erakusten ditu taula moduan.
     * Soilik kantitatea > 0 duten produktuak agertzen dira.
     * Taula honetan honako zutabeak daude:
     * - IDa: Produktuaren identifikatzailea (index + 1)
     * - Izena: Produktuaren izena
     * - Prezioa: Produktuaren prezioa (bi hamartarrekin formateatua)
     * - Kant.: Produktuaren kantitatea stockean
     * - Mota: Produktuaren motaren izena
     * 
     * @param mota_izenak         Produktu mota bakoitzaren izenak
     * @param motaID              Erakutsi nahi den motaren IDa
     * @param produktu_motak      Produktu bakoitzaren motaren IDa
     * @param produktu_kantitatea Produktu bakoitzaren stock-a (kantitatea)
     * @param produktu_izenak     Produktu bakoitzaren izenak
     * @param produktu_prezioak   Produktu bakoitzaren prezioak
     */
    public static void produktuakErakutsiMotak(String[] mota_izenak, int motaID, int[] produktu_motak,
            int[] produktu_kantitatea,
            String[] produktu_izenak, double[] produktu_prezioak) {
        int PRODUKTU_KOPURUA = produktu_izenak.length;

        // Datuen taula prestatu (ID, Izena, Prezioa, Kantitatea)
        String[][] produktuak_tabla = new String[PRODUKTU_KOPURUA + 1][5];

        // Goiburuak (IDa, Izena, Prezioa, Kantitatea)
        produktuak_tabla[0][0] = "IDa";
        produktuak_tabla[0][1] = "Izena";
        produktuak_tabla[0][2] = "Prezioa";
        produktuak_tabla[0][3] = "Kant.";
        produktuak_tabla[0][4] = "Mota";

        // Datuak sartu

        int index = 0;
        for (int i = 0; i < PRODUKTU_KOPURUA; i++) {
            if (produktu_kantitatea[i] > 0 && produktu_motak[i] == motaID) {
                produktuak_tabla[index + 1][0] = String.valueOf(i + 1);
                produktuak_tabla[index + 1][1] = produktu_izenak[i];
                produktuak_tabla[index + 1][2] = String.format("%.2f", produktu_prezioak[i]);
                produktuak_tabla[index + 1][3] = String.valueOf(produktu_kantitatea[i]);
                produktuak_tabla[index + 1][4] = String.valueOf(mota_izenak[produktu_motak[i]]);
                index++;
            }
        }

        // Lortu zutabe bakoitzeko luzera maximoa
        int[] zutabeMaxLuzera = new int[5];
        for (int j = 0; j < 5; j++) {
            int max = 0;
            for (int i = 0; i < PRODUKTU_KOPURUA + 1; i++) {
                if (produktuak_tabla[i][j] == null) {
                    break;
                }
                if (produktuak_tabla[i][j].length() > max) {
                    max = produktuak_tabla[i][j].length();
                }
            }
            zutabeMaxLuzera[j] = max;
        }

        // Marra (separadorea) kalkulatu
        int taulaZabalera = 0;
        for (int luzera : zutabeMaxLuzera) {
            taulaZabalera += luzera + 3; // " | ".lenght == 3
        }
        taulaZabalera += 1; // azken "|"
        String marra = "-".repeat(taulaZabalera);

        System.out.println(marra);

        // Erakutsi taula modura, soilik | eta - erabiliz
        for (int i = 0; i < PRODUKTU_KOPURUA + 1; i++) {
            if (produktuak_tabla[i][0] == null) {
                System.out.println(marra);
                break;
            }
            System.out.print("|");
            for (int j = 0; j < 5; j++) {
                String gelaxka = produktuak_tabla[i][j];
                // Gelaxka justifikatu
                System.out.printf(" %-" + zutabeMaxLuzera[j] + "s |", gelaxka);
            }
            System.out.println();

            // Goiburuaren azpian eta bukaeran marra jarri
            if (i == 0 || i == PRODUKTU_KOPURUA) {
                System.out.println(marra);
            }
        }
    }
}