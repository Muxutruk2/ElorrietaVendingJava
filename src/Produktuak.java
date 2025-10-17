public class Produktuak {
public static void produktuakErakutsi(String[] produktu_izenak, double[] produktu_prezioak, int[] produktu_kantitatea) {
        int PRODUKTU_KOPURUA = produktu_izenak.length; 
        
        // Datuen taula prestatu (ID, Izena, Prezioa, Kantitatea)
        String[][] produktuak_tabla = new String[PRODUKTU_KOPURUA + 1][4]; 

        // Goiburuak (IDa, Izena, Prezioa, Kantitatea)
        produktuak_tabla[0][0] = "IDa";
        produktuak_tabla[0][1] = "Izena";
        produktuak_tabla[0][2] = "Prezioa (€)";
        produktuak_tabla[0][3] = "Kant.";

        // Datuak sartu
        for (int i = 0; i < PRODUKTU_KOPURUA; i++) {
            if (produktu_kantitatea[i] > 0) {
                produktuak_tabla[i + 1][0] = String.valueOf(i + 1);
                produktuak_tabla[i + 1][1] = produktu_izenak[i];
                produktuak_tabla[i + 1][2] = String.format("%.2f", produktu_prezioak[i]); 
                produktuak_tabla[i + 1][3] = String.valueOf(produktu_kantitatea[i]);
            }
        }

        // Lortu zutabe bakoitzeko luzera maximoa
        int[] zutabeMaxLuzera = new int[4];
        for (int j = 0; j < 4; j++) {
            int max = 0;
            for (int i = 0; i < PRODUKTU_KOPURUA + 1; i++) {
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
            System.out.print("|");
            for (int j = 0; j < 4; j++) {
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
     * Mota zehatz bateko produktuak (kantitatea > 0 dutenak soilik) erakusten ditu.
     * Aukera 1-eko zati baten antzekoa izan liteke.
     */
    public static void produktuakErakutsiMotak(int motaID, int[] produktu_motak, int[] produktu_kantitatea, String[] produktu_izenak, double[] produktu_prezioak) {
        int PRODUKTU_KOPURUA = produktu_izenak.length; 
        
        // Datuen taula prestatu (ID, Izena, Prezioa, Kantitatea)
        String[][] produktuak_tabla = new String[PRODUKTU_KOPURUA + 1][4]; 

        // Goiburuak (IDa, Izena, Prezioa, Kantitatea)
        produktuak_tabla[0][0] = "IDa";
        produktuak_tabla[0][1] = "Izena";
        produktuak_tabla[0][2] = "Prezioa (€)";
        produktuak_tabla[0][3] = "Kant.";

        // Datuak sartu

        int index = 0;
        for (int i = 0; i < PRODUKTU_KOPURUA; i++) {
            if (produktu_kantitatea[i] > 0 && produktu_motak[i] == motaID) {
                produktuak_tabla[index + 1][0] = String.valueOf(i + 1);
                produktuak_tabla[index + 1][1] = produktu_izenak[i];
                produktuak_tabla[index + 1][2] = String.format("%.2f", produktu_prezioak[i]); 
                produktuak_tabla[index + 1][3] = String.valueOf(produktu_kantitatea[i]);
                index++;
            }
        }

        // Lortu zutabe bakoitzeko luzera maximoa
        int[] zutabeMaxLuzera = new int[4];
        for (int j = 0; j < 4; j++) {
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
            for (int j = 0; j < 4; j++) {
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