import java.util.Arrays;
import java.util.Scanner;

public class ElorrietaVending {

    static int PRODUKTU_KOPURUA = 40;
    static int MOTA_KOPURUA = 4;
    static int[] BILLETEAK = { 1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000 };

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        String[] mota_izenak = new String[MOTA_KOPURUA];

        String[] produktu_izenak = new String[PRODUKTU_KOPURUA];
        double[] produktu_prezioak = new double[PRODUKTU_KOPURUA];
        int[] produktu_motak = new int[PRODUKTU_KOPURUA];
        int[] produktu_kantitatea = new int[PRODUKTU_KOPURUA];

        String[] erabiltzaileak = { "benat", "bilal", "enay" };
        String[] pasahitzak = { "benat1234", "bilal07", "enayyy" };

        init_produktuak(mota_izenak, produktu_izenak, produktu_prezioak, produktu_motak, produktu_kantitatea);

        int[][] orga = new int[20][2]; // 20 item, non 2 zenbaki gordetzen diren: ida eta kopurua

        // Bete orga -1
        for (int i = 0; i < orga.length; i++) {
            Arrays.fill(orga[i], -1);
        }

        main_loop: while (true) {
            int organ_kopurua = Orga.organProduktuak(orga);
            System.out.println(heading("Ongi etorri", 5, "-"));
            System.out.println("Organ " + organ_kopurua + " item daude");
            System.out.println("1 Produktu motak");
            System.out.println("2 Produktuak");
            System.out.println("3 Produktuak kendu");
            System.out.println("4 Laburpena");
            System.out.println("5 Erosketa");
            System.out.println("6 Admin panela");

            int aukera = lortuInt(sc, "Aukeratu", 1, 6);

            switch (aukera) {
                case 1:
                    System.out.println("Produktu Motak: ");
                    for (int i = 0; i < mota_izenak.length; i++) {
                        System.out.println((i + 1) + " - " + mota_izenak[i]);
                    }

                    int aukera_mota = lortuInt(sc, "Aukeratu produktu mota", 1, mota_izenak.length) - 1;

                    while (true) {
                        Produktuak.produktuakErakutsiMotak(
                                mota_izenak, aukera_mota, produktu_motak, produktu_kantitatea,
                                produktu_izenak, produktu_prezioak);

                        int aukera_produktua = lortuInt(sc, "Aukeratu produktua", 1, PRODUKTU_KOPURUA) - 1;

                        if (produktu_kantitatea[aukera_produktua] == 0) {
                            System.err.println("Aukeratu duzun produktua ez da existitzen");
                            continue;
                        }

                        if (produktu_motak[aukera_produktua] != aukera_mota) {
                            System.err.println("Aukeratu duzun produktua ez da aukeratu duzun motaren parte");
                            continue;
                        }

                        // Produktua zuzena bada, organ sartu
                        Orga.sartuProduktuaOrgan(orga, sc, produktu_kantitatea, aukera_produktua);

                        // Galdetu zer egin nahi duen orain
                        System.out.println();
                        System.out.println("1 Mota berdineko beste produktu bat gehitu");
                        System.out.println("2 Itzuli menu nagusira");

                        int aukera_menua = lortuInt(sc, "Aukeratu", 1, 2);

                        itxaronEnter(sc);

                        if (aukera_menua == 1) {
                            // bueltatu while-ra (jarraitu mota bereko beste produktu bat aukeratzen)
                            continue;
                        } else if (aukera_menua == 2) {
                            // zuzenean menu nagusira bueltatu
                            continue main_loop;
                        }
                    }
                case 2:
                    while (true) {
                        Produktuak.produktuakErakutsi(mota_izenak, produktu_motak, produktu_kantitatea, produktu_izenak,
                                produktu_prezioak);

                        int aukera_produktua_1 = lortuInt(sc, "Aukeratu produktua", 1, PRODUKTU_KOPURUA) - 1;

                        if (produktu_kantitatea[aukera_produktua_1] == 0) {
                            System.err.println("Aukeratu duzun produktua ez da existitzen");
                            continue;
                        }

                        Orga.sartuProduktuaOrgan(orga, sc, produktu_kantitatea, aukera_produktua_1);

                        System.out.println("1 Beste produktu bat gehitu");
                        System.out.println("2 Itzuli menu nagusira");

                        int aukera_menua_0 = lortuInt(sc, "Aukeratu", 1, 2);

                        if (aukera_menua_0 == 1) {
                            itxaronEnter(sc);
                            // bueltatu while-ra (jarraitu beste produktu bat aukeratzen)
                            continue;
                        } else if (aukera_menua_0 == 2) {
                            itxaronEnter(sc);
                            // zuzenean menu nagusira bueltatu
                            continue main_loop;
                        }
                    }
                case 3:
                    System.out.println("Produktua kendu");

                    if (Orga.organProduktuak(orga) == 0) {
                        System.err.println("Ez dago produkturik organ kentzeko");
                        itxaronEnter(sc);
                        break;
                    }

                    for (int i = 0; i < orga.length; i++) {
                        if (orga[i][1] > 0) {
                            System.out.println((i + 1) + " " + produktu_izenak[i]);
                        }
                    }
                    int aukera_ezabatu = 0;

                    while (true) {
                        aukera_ezabatu = lortuInt(sc, "Aukeratu ezabatzeko produktua", 1, orga.length) - 1;
                        if (orga[aukera_ezabatu][1] > 0) {
                            break;
                        } else {
                            System.err.println("Produktua ez dago organ");
                        }
                    }

                    orga[aukera_ezabatu] = new int[2];

                    itxaronEnter(sc);

                    break;
                case 4:
                    double subtotal = round(Orga.orgaSubtotal(orga, produktu_prezioak), 2);

                    Laburpena.laburpena(orga, produktu_izenak, produktu_kantitatea, produktu_prezioak);

                    System.out.println(heading(("Subtotala: " + subtotal), 3, "-"));
                    System.out.println(heading(("Totala: " + round(subtotal * 1.21, 2)), 3, "-"));

                    itxaronEnter(sc);

                    break;
                case 5:
                    double prezioa = round(Orga.orgaSubtotal(orga, produktu_prezioak) * 1.21, 2);

                    double sarrera = round(lortuDouble(sc, "Sartu dirua makinan", prezioa, 999.0), 2);

                    double bueltak = sarrera - prezioa;

                    if (bueltak < 0) {
                        System.err.println("Errorea: Ez da diru nahiko sartu");
                        return;
                    }

                    int[] dirua = diruaTxanponetara(bueltak);

                    for (int i = 0; i < dirua.length; i++) {
                        if (dirua[i] != 0) {
                            System.out.println(diruaErakutsi(i) + " x " + dirua[i]);
                        }
                    }

                    Produktuak.produktuakErosiOrgatik(orga, produktu_kantitatea);

                    for (int i = 0; i < orga.length; i++) {
                        orga[i] = new int[] { -1, -1 }; // Orga hutsitu
                    }

                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        // Ezer
                    }

                    break;
                case 6:
                    String erabiltzailea = "";
                    while (erabiltzailea.isEmpty()) {
                        System.out.print("Idatzi erabiltzailea: ");
                        erabiltzailea = sc.nextLine();
                    }

                    String pasahitza = "";
                    while (pasahitza.isEmpty()) {
                        System.out.print("Idatzi pasahitza: ");
                        pasahitza = sc.nextLine();
                    }

                    int erabiltzaile_indizea = linearSearch(erabiltzaileak, erabiltzailea);

                    if (erabiltzaile_indizea == -1) {
                        // Erabiltzailea ez da existitzen
                        System.err.println("Pasahitz edo erabiltzaile okerra");
                        continue;
                    }

                    if (!pasahitzak[erabiltzaile_indizea].equals(pasahitza)) {
                        // Pasahitz okerra
                        System.err.println("Pasahitz edo erabiltzaile okerra");
                        continue;
                    }

                    System.out.println("Ongi etorri " + erabiltzaileak[erabiltzaile_indizea] + "!");

                    System.out.println(heading("ADMIN PANELA", 10, "*"));

                    System.out.println("1 Produktu berria");
                    System.out.println("2 Produktua aldatu");
                    System.out.println("3 Produktua ezabatu");
                    System.out.println("4 Irten");

                    int aukera_admin = lortuInt(sc, "Aukeratu", 1, 4);

                    switch (aukera_admin) {
                        case 1:
                            int leku_librea = Produktuak.produktuakLekuLibrea(produktu_kantitatea);

                            if (leku_librea == -1) {
                                System.err.println("Ez dago lekurik produktu berriak sarzeko");
                                break;
                            }

                            System.out.println("Idatzi produktuaren izen berria");
                            String produktu_izena = sc.nextLine();

                            for (int i = 0; i < MOTA_KOPURUA; i++) {
                                System.out.println((i + 1) + " - " + mota_izenak[i]);
                            }
                            System.out.println("Idatzi produktuaren mota");

                            int produktu_mota = lortuInt(sc, "Idatzi mota", 1, MOTA_KOPURUA) - 1;

                            double produktu_prezioa = lortuDouble(sc, "Idatzi produktuaren prezioa", 0.01, 50.0);

                            int produktu_berria_kantitatea = lortuInt(sc, "Idatzi zenbat produktu dauden", aukera_admin,
                                    produktu_mota);

                            produktu_izenak[leku_librea] = produktu_izena;
                            produktu_motak[leku_librea] = produktu_mota;
                            produktu_kantitatea[leku_librea] = produktu_berria_kantitatea;
                            produktu_prezioak[leku_librea] = produktu_prezioa;

                            System.out.println("Produktua arrakastaz gehituta");

                            itxaronEnter(sc);

                            break;
                        case 2:

                            break;
                        case 3:

                            break;
                        case 4:
                            return;

                        default:
                            break;
                    }

                    break;
                default:
                    System.err.println("Errorea: Aukera 1 baino txikiago edo 6 baino handiagoa da");
                    return;
            }
        }
    }

    /**
     * Dirua formatu zuzenean erakutsi
     * 
     * @param billeteak_index BILLETEAK arrayaen indizea (0: 1cent, 1: 2cent...)
     * @return Textuan txanpon/billete horren errepresentazioa
     */
    private static String diruaErakutsi(int billeteak_index) {
        int dirua = BILLETEAK[billeteak_index];
        if (dirua > 100) {
            return (dirua / 100) + "€";
        } else {
            return dirua + "cent";
        }
    }

    /**
     * Diru kopuru bat txanpon eta billeteetan banatu.
     * 
     * @param dirua diru kopurua (eurotan, adib. 23.75)
     * @return txanponen arraya, ezkerretik eskumara:
     *         1c, 2c, 5c, 10c, 20c, 50c, 1€, 2€, 5€, 10€, 20€, 50€, 100€
     */
    private static int[] diruaTxanponetara(double dirua) {
        int[] emaitza = new int[BILLETEAK.length];

        // Dirua zentimotan bihurtu eta biribildu
        int diruaZentimo = (int) Math.round(dirua * 100);

        // Banatu dirua balio handienetik txikienera
        for (int i = BILLETEAK.length - 1; i >= 0; i--) {
            emaitza[i] = diruaZentimo / BILLETEAK[i];
            diruaZentimo %= BILLETEAK[i];
        }

        return emaitza;
    }

    /**
     * "..." erakutsi erabiltzaileari eta harek enter sakatu arte itxaron
     * 
     * @param sc Scanner instantzia
     */
    private static void itxaronEnter(Scanner sc) {
        String input;
        do {
            System.out.println("...");
            input = sc.nextLine();
        } while (!input.isEmpty());
        sc.nextLine();

    }

    /**
     * Textua titulo modura bueltatu "--- Adibidea ---"
     * 
     * @param input      erabiliko den textua
     * @param kopurua    zenbak karaktere sartuko zaion ezker eta eskuinean
     * @param karakterea zer karakter (multzo) sartuko zaion
     * @return Textua
     */
    static String heading(String input, int kopurua, String karakterea) {
        return karakterea.repeat(kopurua) + " " + input + " " + karakterea.repeat(kopurua);
    }

    /**
     * Erabiltzaileari galdetu zenbaki bat min eta max artean (biak barne)
     * 
     * @param sc     Scanner instantzia
     * @param prompt Zer galdetuko zaion
     * @param min    Zenbaki minimoa
     * @param max    Zenbaki maximoa
     * @return Zenbaki osoa, min eta max artean (biak barne)
     */
    static int lortuInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + " (" + min + " - " + max + "): ");

            // Erabiltzaileak zenbaki bat sartu duen egiaztatu
            if (sc.hasNextInt()) {
                int zenb = sc.nextInt();

                // Zenbakia tarte egokian dagoen egiaztatu
                if (zenb >= min && zenb <= max) {
                    sc.nextLine(); // garbitu sarrera
                    return zenb; // zuzena bada, bueltatu
                } else {
                    System.out.println("Mesedez, " + min + " eta " + max + " arteko zenbaki bat sartu.");
                }
            } else {
                System.out.println("Zenbaki oso bat sartu behar duzu.");
                sc.next(); // sarrera okerra garbitu
            }
        }
    }

    /**
     * Erabiltzaileari galdetu zenbaki (dobule) bat min eta max artean (biak barne)
     * 
     * @param sc     Scanner instantzia
     * @param prompt Zer galdetuko zaion
     * @param min    Zenbaki minimoa
     * @param max    Zenbaki maximoa
     * @return Zenbakia, min eta max artean (biak barne)
     */
    static double lortuDouble(Scanner sc, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt + " (" + min + " - " + max + "): ");

            // Erabiltzaileak zenbaki bat sartu duen egiaztatu
            if (sc.hasNextDouble()) {
                double zenb = sc.nextDouble();

                // Zenbakia tarte egokian dagoen egiaztatu
                if (zenb >= min && zenb <= max) {
                    return zenb; // zuzena bada, bueltatu
                } else {
                    System.out.println("Mesedez, " + min + " eta " + max + " arteko zenbaki bat sartu.");
                }
            } else {
                System.out.println("Zenbaki bat (adib. 3.5) sartu behar duzu.");
                sc.next(); // sarrera okerra garbitu
            }
        }
    }

    /**
     * Double zenbaki bat borobildu
     * 
     * @param input   Zenbaki double-a
     * @param decimal Zenbat leku dezimalera
     * @return Zenbaki borobildua
     */
    static double round(double input, int decimal) {
        // input: 2.235
        double power = Math.pow(10, decimal); // decimal: 2 power: 100
        return Math.round(input * power) / power; // round(223.5) / 100 = 2.23
    }

    /**
     * String array batean, String bat bilatu eta indizea bueltatu
     * 
     * -1 bueltatzen du ez dagoenean
     * 
     * @param arr    Bilatzeko array-a
     * @param target Bilatu nahi den textua
     * @return Non aurkitzen den (indizea); -1 Ez bada topatu
     */
    public static int linearSearch(String[] arr, String target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(target)) {
                return i; // Aurkitu da
            }
        }
        return -1; // Ez da aurkitu
    }

    /**
     * Produktuen array guztiak mutatu hasieratzeko
     * 
     * @param mota_izenak
     * @param produktu_izenak
     * @param produktu_prezioak
     * @param produktu_motak
     * @param produktu_kantitatea
     * 
     */
    static void init_produktuak(String[] mota_izenak, String[] produktu_izenak, double[] produktu_prezioak,
            int[] produktu_motak, int[] produktu_kantitatea) {
        mota_izenak[0] = "edariak";
        mota_izenak[1] = "snack";
        mota_izenak[2] = "osasuntsuak";
        mota_izenak[3] = "teknologia";

        produktu_izenak[0] = "FritzCola Lata";
        produktu_prezioak[0] = 1.50;
        produktu_motak[0] = 0;
        produktu_kantitatea[0] = 2;

        produktu_izenak[1] = "Altzola Ura 50cl";
        produktu_prezioak[1] = 1.00;
        produktu_motak[1] = 0;
        produktu_kantitatea[1] = 1;

        produktu_izenak[2] = "Patata Frijituak";
        produktu_prezioak[2] = 1.20;
        produktu_motak[2] = 1;
        produktu_kantitatea[2] = 3;

        produktu_izenak[3] = "Fruta";
        produktu_prezioak[3] = 2.00;
        produktu_motak[3] = 2;
        produktu_kantitatea[3] = 1;

        produktu_izenak[4] = "Laranja Zukua 20cl";
        produktu_prezioak[4] = 1.30;
        produktu_motak[4] = 2;
        produktu_kantitatea[4] = 2;
    }
}
