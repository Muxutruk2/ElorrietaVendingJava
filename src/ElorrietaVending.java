import java.util.Arrays;
import java.util.Scanner;

public class ElorrietaVending {
    static int PRODUKTU_KOPURUA = 40;
    static int MOTA_KOPURUA = 4;
    static int[] BILLETEAK = { 1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000 };

    public static void main(String[] args) throws Exception {
        // Scanner sortu
        Scanner sc = new Scanner(System.in);

        // Moten izenak gorde {0: "edariak", 1: "snack", ...}
        String[] mota_izenak = new String[MOTA_KOPURUA];

        // Produktuen izenak {0: "FritzKola", 1: "Aqua Pura", ...}
        String[] produktu_izenak = new String[PRODUKTU_KOPURUA];

        // Produktuen prezioak {0: 1.50, 1: 1.00, ... }
        double[] produktu_prezioak = new double[PRODUKTU_KOPURUA];

        // Produtuen mota {0: 0, 1: 0, ...} // Biak edariak
        int[] produktu_motak = new int[PRODUKTU_KOPURUA];

        // Zenbat ale dauden makinan {0: 3, 1: 2}
        // 0: produktua existitzen da baina makina honek ez ditu alerik
        // -1: produktua ez da existitzen
        int[] produktu_kantitatea = new int[PRODUKTU_KOPURUA];
        Arrays.fill(produktu_kantitatea, -1); // -1 ekin bete

        // Administratzarien logina
        String[] erabiltzaileak = { "benat", "bilal", "enay" };
        String[] pasahitzak = { "benat1234", "bilal07", "enayyy" };

        // Hasieratu arrayak
        init_produktuak(mota_izenak, produktu_izenak, produktu_prezioak, produktu_motak, produktu_kantitatea);

        // {
        // 0: {0, 2}, // Bi fritzkola
        // 1: {-1, -1}, // Leku librea
        // ...
        // }
        int[][] orga = new int[20][2]; // 20 item, non 2 zenbaki gordetzen diren: ida eta kopurua

        // Bete orga -1 zenbakiekin (leku librea)
        for (int i = 0; i < orga.length; i++) {
            Arrays.fill(orga[i], -1);
        }

        // Aplikazioaren hasiera
        main_loop: while (true) {
            // Menu nagusia
            int organ_kopurua = Orga.organProduktuak(orga);
            System.out.println(heading("Ongi etorri", 5, "-"));
            System.out.println("Organ " + organ_kopurua + " item daude");
            System.out.println("1 Produktu motak");
            System.out.println("2 Produktuak");
            System.out.println("3 Produktuak kendu");
            System.out.println("4 Laburpena");
            System.out.println("5 Erosketa");
            System.out.println("6 Admin panela");

            // lortuInt zenbaki oso bat erakusten dio erabiltzaileari, min eta max artean
            // (biak barne)
            int aukera = lortuInt(sc, "Aukeratu", 1, 6);

            switch (aukera) {
                case 1:
                    System.out.println("Produktu Motak: ");
                    for (int i = 0; i < mota_izenak.length; i++) {
                        System.out.println((i + 1) + " - " + mota_izenak[i]);
                    }

                    int aukera_mota = lortuInt(sc, "Aukeratu produktu mota", 1, mota_izenak.length) - 1;

                    // Lortu zenbat produktu dauden erabiltzaileak aukeratu duen motakoak
                    int motako_produktu_kop = Produktuak.produktuKopuruaMota(produktu_kantitatea, produktu_motak,
                            aukera_mota);

                    if (motako_produktu_kop == 0) {
                        // Aukeratutako mota ez du produkturik
                        System.err.println("Emandako motan ez daude aukeratu ahal diren produkturik!");
                        System.err.println("Saiatu beste mota batekin");
                        itxaronEnter(sc);
                        continue main_loop;
                    }

                    while (true) {
                        Produktuak.produktuakErakutsiMotak(
                                mota_izenak, aukera_mota, produktu_motak, produktu_kantitatea,
                                produktu_izenak, produktu_prezioak);

                        int aukera_produktua = lortuInt(sc, "Aukeratu produktua", 1, PRODUKTU_KOPURUA) - 1;

                        if (produktu_kantitatea[aukera_produktua] == 0) {
                            // Aukeratu duen produktua
                            System.err.println("Aukeratu duzun produktua ez da existitzen");
                            continue; // Berriro erakutsi produktuak
                        }

                        if (produktu_motak[aukera_produktua] != aukera_mota) {
                            System.err.println("Aukeratu duzun produktua ez da aukeratu duzun motaren parte");
                            continue; // Berriro erakutsi produktuak
                        }

                        // Produktua zuzena bada, organ sartu
                        Orga.sartuProduktuaOrgan(orga, sc, produktu_kantitatea, aukera_produktua);

                        // Galdetu zer egin nahi duen orain
                        System.out.println();
                        System.out.println("1 Mota berdineko beste produktu bat gehitu");
                        System.out.println("2 Itzuli menu nagusira");

                        int aukera_menua = lortuInt(sc, "Aukeratu", 1, 2);

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
                        int produktuak_kopurua = Produktuak.produktuKopurua(produktu_kantitatea);

                        if (produktuak_kopurua == 0) {
                            System.err.println("Ez dago produkturik makinan!");
                            System.err.println("Deitu arreta zerbitzura, barkatu eragozpenak");
                            continue main_loop;
                        }

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

                    System.out.println(heading("Guztira " + prezioa, 5, "-"));

                    double emandakoa = 0.0;

                    while (emandakoa < prezioa) {
                        System.out.println(round(prezioa - emandakoa, 2) + " Falta zaizu");
                        emandakoa += round(lortuDouble(sc, "Sartu dirua makinan", 0.01, 999.0), 2);
                    }

                    double bueltak = emandakoa - prezioa;

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

                    System.out.println(heading("Eskerrik asko Clankergan konfidatzearren! :D", 10, "-"));

                    try {
                        Thread.sleep(5000);
                        cls();
                    } catch (Exception e) {
                        // Ezer
                    }

                    break;
                case 6:
                    if (sc.hasNextLine()) {
                        sc.nextLine();
                    }

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

                    int stock_gabe_kop = Produktuak.produktuKopuruaStockGabe(produktu_kantitatea);

                    if (stock_gabe_kop > 0) {
                        System.err.println(heading("ADI! " + stock_gabe_kop + " produktu daude stock gabe!", 10, "-"));
                    }

                    System.out.println("Ongi etorri " + erabiltzaileak[erabiltzaile_indizea] + "!");

                    System.out.println(heading("ADMIN PANELA", 10, "*"));

                    System.out.println("1 Produktu berria");
                    System.out.println("2 Produktua aldatu");
                    System.out.println("3 Produktua ezabatu");
                    System.out.println("4 Restock");
                    System.out.println("5 Irten");

                    int aukera_admin = lortuInt(sc, "Aukeratu", 1, 4);

                    if (sc.hasNextLine()) {
                        sc.nextLine();
                    }

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

                            double produktu_prezioa = lortuDouble(sc, "Idatzi produktuaren prezioa", 0.01, 99.9);

                            int produktu_berria_kantitatea = lortuInt(sc, "Idatzi zenbat produktu dauden", 1,
                                    10);

                            produktu_izenak[leku_librea] = produktu_izena;
                            produktu_motak[leku_librea] = produktu_mota;
                            produktu_kantitatea[leku_librea] = produktu_berria_kantitatea;
                            produktu_prezioak[leku_librea] = produktu_prezioa;

                            System.out.println("Produktua arrakastaz gehituta");

                            itxaronEnter(sc);

                            break;
                        case 2:
                            while (true) {
                                Produktuak.produktuakErakutsi(mota_izenak, produktu_motak, produktu_kantitatea,
                                        produktu_izenak, produktu_prezioak);

                                int aldatzeko_produktu_id = lortuInt(sc, "Aukeratu aldatzeko produktua", 1,
                                        PRODUKTU_KOPURUA) - 1;

                                if (produktu_kantitatea[aldatzeko_produktu_id] <= 0) {
                                    System.err.println("Aukeratu duzun produktua ez da existitzen");
                                    continue;
                                }

                                System.out.println("Izena: " + produktu_izenak[aldatzeko_produktu_id]);
                                System.out.println("Prezioa: " + produktu_prezioak[aldatzeko_produktu_id]);
                                System.out.println("Mota: " + mota_izenak[produktu_motak[aldatzeko_produktu_id]] + " ("
                                        + produktu_motak[aldatzeko_produktu_id] + ")");
                                System.out.println("Kantitatea" + produktu_kantitatea[aldatzeko_produktu_id]);

                                String aldatzeko_izena = "";
                                while (aldatzeko_izena.isEmpty()) {
                                    System.out.print("Idatzi izen berria: ");
                                    aldatzeko_izena = sc.nextLine();
                                }

                                double aldatzeko_prezioa = lortuDouble(sc, "Idatzi prezio berria", 0.01, 99.9);

                                System.out.println("Produktu Motak: ");
                                for (int i = 0; i < mota_izenak.length; i++) {
                                    System.out.println((i + 1) + " - " + mota_izenak[i]);
                                }

                                int aldatzeko_mota = lortuInt(sc, "Idatzi mota berria", 1, mota_izenak.length) - 1;

                                int aldatzeko_kantitatea = lortuInt(sc, "Idatzi kantitate berria", 0, 10);

                                produktu_izenak[aldatzeko_produktu_id] = aldatzeko_izena;
                                produktu_prezioak[aldatzeko_produktu_id] = aldatzeko_prezioa;
                                produktu_motak[aldatzeko_produktu_id] = aldatzeko_mota;
                                produktu_kantitatea[aldatzeko_produktu_id] = aldatzeko_kantitatea;

                                System.out.println("Produktua arrakastaz aldatuta");

                                break;
                            }

                            break;
                        case 3:
                            while (true) {
                                Produktuak.produktuakErakutsi(mota_izenak, produktu_motak, produktu_kantitatea,
                                        produktu_izenak, produktu_prezioak);

                                int ezabatzeko_produktua = lortuInt(sc, "Idatzi ezabazeko produktua", 1,
                                        PRODUKTU_KOPURUA)
                                        - 1;

                                if (produktu_kantitatea[ezabatzeko_produktua] <= 0) {
                                    System.err.println("Produktu hori ez da existitzen");
                                    continue;
                                }

                                produktu_kantitatea[ezabatzeko_produktua] = 0;

                                break;
                            }
                            break;

                        case 4:
                            int restock_prod = -1;
                            while (true) {
                                stock_gabe_kop = Produktuak.produktuKopuruaStockGabe(produktu_kantitatea);

                                if (stock_gabe_kop <= 0) {
                                    System.err.println("Ez daude produkturik restock egiteko");
                                    itxaronEnter(sc);
                                    break;
                                }

                                Produktuak.produktuakErakutsiAgortuak(mota_izenak, produktu_motak, produktu_kantitatea,
                                        produktu_izenak, produktu_prezioak);

                                restock_prod = lortuInt(sc, "Aukeratu restock egiteko produktua", 0, PRODUKTU_KOPURUA)
                                        - 1;

                                if (produktu_kantitatea[restock_prod] != 0) {
                                    System.err.println("Produktu hori ez du restock egin behar");
                                } else {
                                    int restock_kant = lortuInt(sc, "Zenbat produktu sartuko dira?", 1, 10);

                                    produktu_kantitatea[restock_prod] = restock_kant;
                                    System.out.println("Produktua arrakastaz gehituta");

                                    itxaronEnter(sc);

                                    System.out.println("1 Beste produktu bati restock egin");
                                    System.out.println("2 Itzuli admin menura");

                                    int aukera_restock = lortuInt(sc, "Aukeratu", 1, 2);

                                    if (aukera_restock == 2) {
                                        break;
                                    }
                                }
                            }

                            break;

                        default:
                            continue main_loop;
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
        if (dirua >= 100) {
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
     * Terminala garbitu
     */
    static void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
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

        // Produktu motak
        mota_izenak[0] = "Edariak";
        mota_izenak[1] = "Snack-ak";
        mota_izenak[2] = "Osasungarriak";
        mota_izenak[3] = "Teknologia";

        // Edariak
        produktu_izenak[0] = "FritzKola";
        produktu_prezioak[0] = 1.50;
        produktu_motak[0] = 0;
        produktu_kantitatea[0] = 3;

        produktu_izenak[1] = "Aqua Pura 50cl";
        produktu_prezioak[1] = 1.00;
        produktu_motak[1] = 0;
        produktu_kantitatea[1] = 2;

        produktu_izenak[2] = "Kafe hotza";
        produktu_prezioak[2] = 0.80;
        produktu_motak[2] = 0;
        produktu_kantitatea[2] = 4;

        produktu_izenak[3] = "Edari Isotonikoa";
        produktu_prezioak[3] = 1.60;
        produktu_motak[3] = 0;
        produktu_kantitatea[3] = 2;

        produktu_izenak[4] = "Te hotz errefreskantea";
        produktu_prezioak[4] = 1.60;
        produktu_motak[4] = 0;
        produktu_kantitatea[4] = 5;

        produktu_izenak[5] = "Root Beer Garagardoa";
        produktu_prezioak[5] = 1.20;
        produktu_motak[5] = 0;
        produktu_kantitatea[5] = 3;

        // Snack-ak
        produktu_izenak[6] = "Patata Frijituak";
        produktu_prezioak[6] = 1.20;
        produktu_motak[6] = 1;
        produktu_kantitatea[6] = 4;

        produktu_izenak[7] = "Gaileta gaziak";
        produktu_prezioak[7] = 0.80;
        produktu_motak[7] = 1;
        produktu_kantitatea[7] = 3;

        produktu_izenak[8] = "Choco Wafer Txokolatina";
        produktu_prezioak[8] = 1.20;
        produktu_motak[8] = 1;
        produktu_kantitatea[8] = 5;

        produktu_izenak[9] = "Negritos ";
        produktu_prezioak[9] = 0.80;
        produktu_motak[9] = 1;
        produktu_kantitatea[9] = 2;

        produktu_izenak[10] = "Txokolatezko gailetak";
        produktu_prezioak[10] = 1.20;
        produktu_motak[10] = 1;
        produktu_kantitatea[10] = 3;

        produktu_izenak[11] = "Patata kurruskagarriak";
        produktu_prezioak[11] = 1.80;
        produktu_motak[11] = 1;
        produktu_kantitatea[11] = 4;

        // Osasungarriak
        produktu_izenak[12] = "Laranja Zukua 20cl";
        produktu_prezioak[12] = 1.30;
        produktu_motak[12] = 2;
        produktu_kantitatea[12] = 3;

        produktu_izenak[13] = "Sagarra";
        produktu_prezioak[13] = 0.60;
        produktu_motak[13] = 2;
        produktu_kantitatea[13] = 2;

        produktu_izenak[14] = "Ezpeltazko barritak";
        produktu_prezioak[14] = 0.80;
        produktu_motak[14] = 2;
        produktu_kantitatea[14] = 4;

        produktu_izenak[15] = "Brokoli freskoa";
        produktu_prezioak[15] = 1.00;
        produktu_motak[15] = 2;
        produktu_kantitatea[15] = 2;

        produktu_izenak[16] = "Fruitu lehorrak";
        produktu_prezioak[16] = 0.60;
        produktu_motak[16] = 2;
        produktu_kantitatea[16] = 3;

        produktu_izenak[17] = "Pepinoa";
        produktu_prezioak[17] = 1.00;
        produktu_motak[17] = 2;
        produktu_kantitatea[17] = 2;

        // Teknologia
        produktu_izenak[18] = "Aurikular Inalambrikoak";
        produktu_prezioak[18] = 15.00;
        produktu_motak[18] = 3;
        produktu_kantitatea[18] = 2;

        produktu_izenak[19] = "Bateria Eramangarria";
        produktu_prezioak[19] = 10.00;
        produktu_motak[19] = 3;
        produktu_kantitatea[19] = 3;

        produktu_izenak[20] = "Kargadorea";
        produktu_prezioak[20] = 12.50;
        produktu_motak[20] = 3;
        produktu_kantitatea[20] = 4;

        produktu_izenak[21] = "Aurikularrak (Mini-Jack)";
        produktu_prezioak[21] = 8.00;
        produktu_motak[21] = 3;
        produktu_kantitatea[21] = 2;

        produktu_izenak[22] = "Aurikularrak (USB-C)";
        produktu_prezioak[22] = 8.00;
        produktu_motak[22] = 3;
        produktu_kantitatea[22] = 3;

        produktu_izenak[23] = "USB-C Kablea";
        produktu_prezioak[23] = 5.00;
        produktu_motak[23] = 3;
        produktu_kantitatea[23] = 2;
    }
}
