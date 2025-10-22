public class Laburpena {
    /**
     * 
     * @param orga
     * @param produktu_izenak
     * @param produktu_kantitatea
     * @param produktu_prezioak
     */
    public static void laburpena(int[][] orga, String[] produktu_izenak, int[] produktu_kantitatea,
            double[] produktu_prezioak) {

        int ORGA_PRODUKTUAK = orga.length;

        String[][] laburpena_taula = new String[ORGA_PRODUKTUAK + 1][5];

        // Goiburuak (IDa, Izena, Prezioa, Kantitatea)
        laburpena_taula[0][0] = "IDa";
        laburpena_taula[0][1] = "Izena";
        laburpena_taula[0][2] = "Kant.";
        laburpena_taula[0][3] = "Prezioa";
        laburpena_taula[0][4] = "Guztira";

        int index = 0;
        for (int i = 0; i < ORGA_PRODUKTUAK; i++) {
            if (orga[i][1] > 0) {
                laburpena_taula[index + 1][0] = String.valueOf(orga[i][0] + 1);
                laburpena_taula[index + 1][1] = String.valueOf(produktu_izenak[orga[i][0]]);
                laburpena_taula[index + 1][2] = String.valueOf(orga[i][1]);
                laburpena_taula[index + 1][3] = String.format("%.2f", produktu_prezioak[orga[i][0]]);
                laburpena_taula[index + 1][4] = String.format("%.2f",
                        produktu_prezioak[orga[i][0]] * (double) orga[i][1]);

                index++;

            }
        }

        // Lortu zutabe bakoitzeko luzera maximoa
        int[] zutabeMaxLuzera = new int[5];
        for (int j = 0; j < 5; j++) {
            int max = 0;
            for (int i = 0; i < ORGA_PRODUKTUAK + 1; i++) {
                if (laburpena_taula[i][j] == null) {
                    break;
                }
                if (laburpena_taula[i][j].length() > max) {
                    max = laburpena_taula[i][j].length();
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
        for (int i = 0; i < ORGA_PRODUKTUAK + 1; i++) {
            if (laburpena_taula[i][0] == null) {
                System.out.println(marra);
                break;
            }
            System.out.print("|");
            for (int j = 0; j < 5; j++) {
                String gelaxka = laburpena_taula[i][j];
                // Gelaxka justifikatu
                System.out.printf(" %-" + zutabeMaxLuzera[j] + "s |", gelaxka);
            }
            System.out.println();

            // Goiburuaren azpian eta bukaeran marra jarri
            if (i == 0 || i == ORGA_PRODUKTUAK) {
                System.out.println(marra);
            }
        }
    }
}