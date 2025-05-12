package com.example.proverbapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProverbData {

    // Proverb Entry class to hold key, proverb, and explanation
    public static class ProverbEntry {
        private final String key;
        private final String proverb;
        private final String explanation;

        public ProverbEntry(String key, String proverb, String explanation) {
            this.key = key;
            this.proverb = proverb;
            this.explanation = explanation;
        }

        public String getKey() {
            return key;
        }

        public String getProverb() {
            return proverb;
        }

        public String getExplanation() {
            return explanation;
        }
    }

    // Method to get proverbs categorized with keys
    public static Map<String, List<ProverbEntry>> getProverbsByCategoryWithKeys() {
        Map<String, List<ProverbEntry>> categorizedProverbs = new HashMap<>();

        // Anger and Conflict
        List<ProverbEntry> angerAndConflict = new ArrayList<>();
        angerAndConflict.add(new ProverbEntry("A01", "Bagai anjing menyalak di pantat gajah.",
                "Kemarahan yang tidak membawa hasil."));
        angerAndConflict.add(new ProverbEntry("A02", "Bagai air di daun keladi.",
                "Nasihat yang tidak diendahkan."));
        angerAndConflict.add(new ProverbEntry("A03", "Seperti harimau beranak muda.",
                "Orang yang mudah tersinggung atau marah."));
        angerAndConflict.add(new ProverbEntry("A04", "Bagai api dalam sekam.",
                "Bahaya yang tidak kelihatan tetapi memudaratkan."));
        angerAndConflict.add(new ProverbEntry("A05", "Antang patah, lesung hilang",
                "Kemalangan yang bertimpa - timpa"));
        angerAndConflict.add(new ProverbEntry("A06", "Anjing galak , babi berani",
                "berani sama berani , kuat sama kuat"));
        angerAndConflict.add(new ProverbEntry("A07","Bagai rabuk dengan api,asal berdekat menyalalah ia",
                "Dikatakan kepada dua orang yang sentiasa berbantahan"));
        angerAndConflict.add(new ProverbEntry("A08", "Bagai tagar di pulau sembilan",
                "Percakapan yang terlalu tinggi lagi sombong"));
        angerAndConflict.add(new ProverbEntry("A09","Bangau minta aku leher, badak, badak minta aku daging",
                "Iri hati melihat kelebihan orang lain"));
        angerAndConflict.add(new ProverbEntry("A10","Bagai air titik ke batu",
                "Memberi nasihat dan ajaran yang sia-sia."));
        angerAndConflict.add(new ProverbEntry("A11","Putih tulang, jangan putih mata",
                "Lebih baik mati daripada menanggung malu."));
        angerAndConflict.add(new ProverbEntry("A12","Bertelingkah antan di lesung, ayam juga yang kenyang",
                "Dua orang yang berselisih tentang harta, orang lain pula yang mendapat faedah."));
        categorizedProverbs.put("Kemarahan dan Konflik", angerAndConflict);

        //Awareness and Caution
        List <ProverbEntry> awarenessAndCaution = new ArrayList<>();
        awarenessAndCaution.add(new ProverbEntry("AC01","Biar-biar naik ke mata.",
                " Jangan dilalaikan sesuatu yang akan mendatangkan bahaya kepada diri sendiri."));
        awarenessAndCaution.add(new ProverbEntry("AC02","Jangan disangka ikan lais-lais tiada menyengat.",
                "Jangan dipermudah-mudahkan orang yang lemah, ada kalanya ia dapat menyusahkan orang yang kuat."));
        awarenessAndCaution.add(new ProverbEntry("AC03","Di manakah berteras kayu mahang.",
                "Janganlah diharapkan yang mustahil."));
        awarenessAndCaution.add(new ProverbEntry("AC04","Seperti bergantung pada rambut sehelai.",
                "Sentiasa dalam keadaan yang sangat sulit dan berbahaya."));
        awarenessAndCaution.add(new ProverbEntry("AC05","Kurang taksir hilang laba.",
                "Pekerjaan yang dibuat tidak dengan berhati-hati akan mendatangkan kerugian."));
        angerAndConflict.add(new ProverbEntry("AC06","Pinggan tak retak nasi tak dingin.",
                "Cermat melakukan sestuatu pekerjaan"));
        categorizedProverbs.put("Berwaspada dan Berhati-hati",awarenessAndCaution);

        // Betrayal and Dishonesty
        List<ProverbEntry> betrayalAndDishonesty = new ArrayList<>();
        betrayalAndDishonesty.add(new ProverbEntry("B01", "Air susu dibalas air tuba.",
                "Kebaikan dibalas dengan kejahatan."));
        betrayalAndDishonesty.add(new ProverbEntry("B02", "Bagai musang berbulu ayam.",
                "Seseorang yang berpura-pura baik tetapi berniat jahat."));
        betrayalAndDishonesty.add(new ProverbEntry("B03", "Bagai musuh dalam selimut.",
                "Orang yang berpura-pura baik tetapi berniat jahat kepada kita."));
        betrayalAndDishonesty.add(new ProverbEntry("B04", "Harapkan pagar, pagar makan padi.",
                "Pengkhianatan daripada orang yang dipercayai."));
        betrayalAndDishonesty.add(new ProverbEntry("B05","Diminta tebu, diberi temberau",
                "Yang diberi berlainan atau kurang daripada yang diminta"));
        betrayalAndDishonesty.add(new ProverbEntry("B06","Bagai kucing dengan panggang",
                " Mudah menimbulkan sesuatu hal yang tak baik kalau diperdekatkan. balur = dendeng"));
        betrayalAndDishonesty.add(new ProverbEntry("B07","Seperti keli dua selubang.",
                "Seorang perempuan yang mempunyai dua orang kekasih."));
        betrayalAndDishonesty.add(new ProverbEntry("B08","Siapa mengaku berak di tengah jalan.",
                "Apabila sesuatu kejahatan yang dibuat secara sulit dektahui orang, yang membuatnya tidak akan mengaku."));
        betrayalAndDishonesty.add(new ProverbEntry("B09","Ibarat burung, mulut manis jangan dipakai.",
                "Kata yang manis-manis selalu mengandungi semu (orang yang bodoh lekas terpengaruh olehnya)."));
        betrayalAndDishonesty.add(new ProverbEntry("B10","95. Bercabang bagai lidah biawak.",
                "Tidak dapat dipercayai sebarang katanya."));
        betrayalAndDishonesty.add(new ProverbEntry("B11","Menimbun tanah yang tinggi, menggali tanah yang lekuk.",
                "Membantu orang kaya dengan jalan menagniaya orang-orang miskin."));
        categorizedProverbs.put("Pengkhianatan dan Ketidakjujuran", betrayalAndDishonesty);

        //Character and Behaviour
        List<ProverbEntry> characterAndBehavior = new ArrayList<>();
        characterAndBehavior.add(new ProverbEntry("CB01","Ekor anjing beberapa pun diurut akan dia, tiada juga betul.",
                "Orang yang bertabiat jahat itu bagaimanapun hendak diperbaiki tetap jahat juga."));
        characterAndBehavior.add(new ProverbEntry("CB02","Seperti saga di atas talam.",
                "Kelakuan yang tidak tetap."));
        characterAndBehavior.add(new ProverbEntry("CB03","Kepala sama hitam, hati berlain-lain.",
                "Manusia berlain-lainan pendapat."));
        characterAndBehavior.add(new ProverbEntry("CB04","Kita semua mati tetapi kubur masing-masing.",
                "Lain orang lain fikirannya."));
        categorizedProverbs.put("Karakter dan Perilaku", characterAndBehavior);

        // Cooperation and Unity
        List<ProverbEntry> cooperationAndUnity = new ArrayList<>();
        cooperationAndUnity.add(new ProverbEntry("CU01", "Bagai aur dengan tebing.",
                "Kerjasama yang saling membantu antara satu sama lain."));
        cooperationAndUnity.add(new ProverbEntry("CU02", "Bulat air kerana pembuluh, bulat kata kerana muafakat.",
                "Kesepakatan hanya tercapai melalui perbincangan bersama."));
        cooperationAndUnity.add(new ProverbEntry("CU03", "Bersatu teguh, bercerai roboh.",
                "Perpaduan membawa kekuatan, perpecahan membawa kehancuran."));
        cooperationAndUnity.add(new ProverbEntry("CU04","Jikalau beranak ikut kata bidan.",
                " Jika kita berkehendak sesuatu daripada orang lain, hendaklah kita turut apa yang dikatakannya."));
        categorizedProverbs.put("Kerjasama dan Perpaduan", cooperationAndUnity);

        // Consequences
        List<ProverbEntry> consequences = new ArrayList<>();
        consequences.add(new ProverbEntry("C01", "Bagai api makan sekam.",
                "Bahaya yang tidak kelihatan tetapi memudaratkan."));
        consequences.add(new ProverbEntry("C02", "Bagai telur sesangkak, pecah sebiji, pecah semua.",
                "Keharmonian yang terjejas apabila salah seorang ahli kelompok terganggu."));
        consequences.add(new ProverbEntry("C03", "Rumah siap, pahat berbunyi.",
                "Orang yang suka mengkritik setelah sesuatu selesai."));
        consequences.add(new ProverbEntry("C04", "Sudah terhantuk baru tengadah.",
                "Bertindak hanya setelah terjadi masalah."));
        consequences.add(new ProverbEntry("C05", "Asal ditugal adalah benih.",
                "Sesuatu usaha tentu ada hasilnya."));
        consequences.add(new ProverbEntry("C06"," Bagai tikus membaiki labu.",
                "Hendak membetulkan barang yang tiada diketahui, akhirnya semakin rosak."));
        consequences.add(new ProverbEntry("C07","Tanah lembah kandungan air, kayu bengkok titian kera.",
                "Tiap-tiap sesuatu sebab yang ada itu akan mendatangkan sebab yang lain."));
        consequences.add(new ProverbEntry("C08","Tahan jerat sorong kepala.",
                "Hendak mencelakakan orang, tetapi diri sendiri yang mendapat celaka."));
        consequences.add(new ProverbEntry("C09","Malu makan, perut lapar.",
                "Kalau segan berusaha, tak akan mendapat kemajuan"));
        categorizedProverbs.put("Akibat", consequences);

        //Confidence and Assurance
        List<ProverbEntry> confidenceAndAssurance = new ArrayList<>();
        confidenceAndAssurance.add(new ProverbEntry("CA01","Tak usah bimbang, gula di dalam mulut, bila hendak telan, telan.",
                "Apa yang dikhuatirkan lagi, benda itu sudah dalam milik kita."));
        categorizedProverbs.put("Keyakinan dan Kejaminan",confidenceAndAssurance);

        //Dependency and Tradition
        List <ProverbEntry> dependencyAndTradition = new ArrayList<>();
        dependencyAndTradition.add(new ProverbEntry("D01","Di mana kutu makan kalau tidak di kepala.",
                "Sudah menjadi adat, anak bergantung pada ayah dan yang miskin pada yang kaya."));
        dependencyAndTradition.add(new ProverbEntry("D02","Seperti orang mati, jikalau tiada orang mengangkat bilakan bergerak.",
                "Seseorang yang daif jika tiada orang menolongnya akan susahlah ia."));
        categorizedProverbs.put("Adat dan Kebergantungan",dependencyAndTradition);

        //Discipline and Parenting
        List <ProverbEntry> disciplineAndParenting = new ArrayList<>();
        disciplineAndParenting.add(new ProverbEntry("DP01","Seperti kerbau menanduk anak, dengan kaparan tanduk, bukan dengan hujungnya.",
                "Menghukum anak ialah untuk memperbaiki kelakuannya, bukan untuk menyeksa."));
        categorizedProverbs.put("Disiplin dan Sikap Ibu Bapa",disciplineAndParenting);

        // Foolishness and Ignorance
        List<ProverbEntry> foolishnessAndIgnorance = new ArrayList<>();
        foolishnessAndIgnorance.add(new ProverbEntry("F01", "Bagai ayam beranak itik.",
                "Anak yang tidak mengikut perangai ibu bapanya."));
        foolishnessAndIgnorance.add(new ProverbEntry("F02", "Bagai burung dalam sangkar.",
                "Hidup yang terpenjara atau terikat."));
        foolishnessAndIgnorance.add(new ProverbEntry("F03", "Bagai kera mendapat bunga.",
                "Orang yang tidak tahu menghargai sesuatu yang indah atau bernilai."));
        foolishnessAndIgnorance.add(new ProverbEntry("F04", "Bagai rusa masuk kampung.",
                "Seseorang yang kelihatan bingung di tempat baru."));
        foolishnessAndIgnorance.add(new ProverbEntry("F05","Bagai budak sapu hingus",
                "Perihal orang yang tidak tahu mengerjakan sesuatu dengan sendirinya"));
        foolishnessAndIgnorance.add(new ProverbEntry("F06","Bagai kelip-kelip terbang malam.",
                "Sesuatu rahsia yang tidak dapat disembunyikan."));
        foolishnessAndIgnorance.add(new ProverbEntry("F07","Seperti si buta baru celik.",
                "Menjadi sombong kerana beroleh kekayaan atau pangkat."));
        foolishnessAndIgnorance.add(new ProverbEntry("F08","Dituba sajakah ikan, dijala dijaring bukankah ikan."
                ,"Membuat jahat tanpa mengerti malu."));
        foolishnessAndIgnorance.add(new ProverbEntry("F09","Seperti katak di bawah tempurung.",
                "Orang yang cetek pengetahuannya."));
        foolishnessAndIgnorance.add(new ProverbEntry("F10","Bagai alah menang tak tahu, bersorak boleh.",
                "Tidak tahu teknik pertandingan, berani pula bertaruh siapa menang."));
        foolishnessAndIgnorance.add(new ProverbEntry("F11","Kurang kerat, rengkuh yang lebih.",
                "Usaha tiada seberapa , hanya cakap yang lebih."));
        foolishnessAndIgnorance.add(new ProverbEntry("F12","Gajah berak besar, kancil pun hendak berak besar, akhirnya mati kebebangan.",
                "Hendak meniru perbuarang orang besar(kaya), akhirnya diri sendiri yang binasa."));
        foolishnessAndIgnorance.add(new ProverbEntry("F13","Minta darah pada dabung.",
                "Minta bantuan daripada orang yang tidak berupaya membantu."));
        foolishnessAndIgnorance.add(new ProverbEntry("F14","Menanti ara tidak bergetah.",
                "Mengharapkan sesuatu yang tidak mungkin dapat."));
        foolishnessAndIgnorance.add(new ProverbEntry("F15","Menanti pelir kambing terputus.",
                "Menantikan sesuatu yang mustahil sampainya."));
        categorizedProverbs.put("Kebodohan dan Kejahilan", foolishnessAndIgnorance);

        // Finality and Fate
        List<ProverbEntry> finalityAndFate = new ArrayList<>();
        finalityAndFate.add(new ProverbEntry("FF01","Padam menyala tarik puntung.",
                "Barang di mana mati di situlah ditanamkan."));
        categorizedProverbs.put("Takdir dan Keredaan",finalityAndFate);

        // Generosity and Gratitude
        List<ProverbEntry> generosityAndGratitude = new ArrayList<>();
        generosityAndGratitude.add(new ProverbEntry("G01", "Air dicincang tak akan putus.",
                "Hubungan persaudaraan yang tidak dapat dipisahkan walau ada perselisihan."));
        generosityAndGratitude.add(new ProverbEntry("G02", "Hati gajah sama dilapah, hati kuman sama dicecah.",
                "Bersikap adil dalam pembahagian rezeki."));
        generosityAndGratitude.add(new ProverbEntry("G03", "Seperti kacang lupakan kulit.",
                "Orang yang lupa asal usulnya atau tidak tahu berterima kasih."));
        generosityAndGratitude.add(new ProverbEntry("G04","Anak baik, menantu molek",
                "Kalau orang mendapat untung baik, maka keuntungan itu datang berganda-ganda."));
        generosityAndGratitude.add(new ProverbEntry("G05","Bagai kera dapat canggung",
                "Perihal orang yang telah ditolong mendampingkan dirinya kepada orang yang menolongnya"));
        generosityAndGratitude.add(new ProverbEntry("G06","Bagai emak mandul baru beranak.",
                "Sangat gembira kerana mendapat sesuatu yang sangat diidam-idamkan"));
        categorizedProverbs.put("Kedermawanan dan Kesyukuran", generosityAndGratitude);

        //Greed and Ingratitude
        List<ProverbEntry> greedAndIngratitude = new ArrayList<>();
        greedAndIngratitude.add(new ProverbEntry("GI01","Diberi betis hendak paha.",
                "Diberi sedikit, kemudian hendak banyak."));
        greedAndIngratitude.add(new ProverbEntry("GI02","Seperti tebu lepas ke dalam mulut gajah, payah akan keluar.",
                "Seseorang itu tidak akan melepaskan barang kegemarannya yang telah diperoleh."));
        greedAndIngratitude.add(new ProverbEntry("GI03","Lelar makan di upih, lagi putih lagi dikeruk.",
                "Perbuatan yang dilakukan kerana nafsu akhirnya akan mendatangkan bencana dan penyesalan."));
        greedAndIngratitude.add(new ProverbEntry("GI04","Makin murah makin menawar.",
                "Seseorang yang telah diturut orang sesuatu kehendaknya, ia berkehendak lebih lagi (telah memperoleh kesenangan ia berkehendakkan kesenangan yang lebih)."));
        categorizedProverbs.put("Ketamakan dan Ketidaksyukuran",greedAndIngratitude);

        //Hypocrisy and Self-Reflection
        List<ProverbEntry> hypocrisyAndSelfReflection = new ArrayList<>();
        hypocrisyAndSelfReflection.add(new ProverbEntry("HS01","Bicarakan rumput di halaman orang, di halaman sendiri rumput sampai ke kaki tangga.",
                "Kesalahan orang lain nampak, kesalahan sendiri tidak sedar."));
        hypocrisyAndSelfReflection.add(new ProverbEntry("HS02","Terkena pada ikan bersorak, terkena pada batang masam.",
                "Hal orang berkawan yang mahu beruntung sahaja dan tidak mahu berugi."));
        hypocrisyAndSelfReflection.add(new ProverbEntry("HS03","Gaharu dibakar, kemenyan berbau.",
                "Memperlihatkan kelebihan supaya dipercayai orang."));
        hypocrisyAndSelfReflection.add(new ProverbEntry("HS04","Mengata dulang paku serpih, mengata orang aku yang lebih.",
                "Mencela orang tetapi diri sendiri lebih buruk daripada yang dicela."));
        categorizedProverbs.put("Hipokrit dan Muhasabah Diri",hypocrisyAndSelfReflection);

        //Hardship and Suffering
        List<ProverbEntry> hardshipAndSuffering = new ArrayList<>();
        hardshipAndSuffering.add(new ProverbEntry("H01","bagai anjing tersepit di pagar",
                "Seseorang yang dalam kesusahan serba salah halnya"));
        hardshipAndSuffering.add(new ProverbEntry("H02","bagai ikan kena tuba",
                "Tidak berdaya lagi"));
        hardshipAndSuffering.add(new ProverbEntry("H03","Bagai ayam dimakan tungau",
                "Pucat dan kuning kerana menghidap penyakit."));
        hardshipAndSuffering.add(new ProverbEntry("H04","Pelanduk di cerang rimba.",
                " Dalam ketakutan; sangat gelisah. cerang = tempat yang terang (sudah ditebang atau sedikit pohon-pohonnya) di dalam rimba"));
        hardshipAndSuffering.add(new ProverbEntry("H05","Bagai galuh di tengah arus.",
                "Menggigil keras, selalu berkeluh-kesah"));
        hardshipAndSuffering.add(new ProverbEntry("H06","Seperti pisau raut, bongkok orang boleh dibetulkan, bongkok sendiri tak boleh betul.",
                "Kesusahan orang dapat diselesaikan tetapi kesusahan sendiri berkehendakkan pertolongan orang lain."));
        hardshipAndSuffering.add(new ProverbEntry("H07","Kita baru capai pengayuh, orang sudah tiba di seberang.",
                "Cita-cita yang telah didahului oleh orang lain."));
        categorizedProverbs.put("Kesusahan dan Keseksaan",hardshipAndSuffering);

        //Influence and Corruption
        List <ProverbEntry> influenceAndCorruption = new ArrayList<>();
        influenceAndCorruption.add(new ProverbEntry("I01","Seperti dedalu api hinggap di pohon kayu; hinggap di batang, batangnya mati, hinggap di ranting, rantingnya patah.",
                "Apabila orang yang jahat bercampur dengan orang yang baik-baik, akhirnya rosaklah orang yang baik-baik itu."));
        categorizedProverbs.put("Pengaruh Buruk dan Korupsi",influenceAndCorruption);

        //Justice and Fairness
        List<ProverbEntry> justiceAndFairness = new ArrayList<>();
        justiceAndFairness.add(new ProverbEntry("J01","Susu di dada tak dapat dielakkan.",
                "Nasib seseorang itu tidak boleh ditolak (baik atau jahat)."));
        categorizedProverbs.put("Keadilan dan Kesaksamaan", justiceAndFairness);

        //Kindness
        List<ProverbEntry> kindness = new ArrayList<>();
        kindness.add(new ProverbEntry("K01","Mulut manis mematahkan tulang.",
                "Jika seseorang disuruh dengan lemah lembut nescaya sukalah hatinya dan pastilah ia bekerja dengan sehabis-habis upayanya."));
        categorizedProverbs.put("Baik Hati",kindness);

        // Leadership and Responsibility
        List<ProverbEntry> leadershipAndResponsibility = new ArrayList<>();
        leadershipAndResponsibility.add(new ProverbEntry("L01", "Adakah hilang bisa ular jika menyusur di bawah akar.",
                "Pemimpin yang rendah diri tidak kehilangan kedudukannya."));
        leadershipAndResponsibility.add(new ProverbEntry("L02", "Belakang parang pun kalau diasah pasti tajam.",
                "Seseorang yang kurang cerdik boleh menjadi pandai jika diajar."));
        leadershipAndResponsibility.add(new ProverbEntry("L03", "Hendak seribu daya, tak hendak seribu dalih.",
                "Jika mahu sesuatu, pasti ada cara mencapainya."));
        leadershipAndResponsibility.add(new ProverbEntry("L04", "Tangan mencencang bahu memikul.",
                "Seseorang harus bertanggungjawab atas perbuatannya."));
        leadershipAndResponsibility.add(new ProverbEntry("L05","Adakah hilang bisa ular jika menyusur di bawah akar.",
                "Pemimpin yang rendah diri tidak kehilangan kedudukannya."));
        categorizedProverbs.put("Kepimpinan dan Tanggungjawab", leadershipAndResponsibility);

        //Power
        List <ProverbEntry> Power = new ArrayList<>();
        Power.add(new ProverbEntry("PO01","Seperti anjing menyalak bukit.",
                "Seseorang yang menentang orang besar yang berpengaruh tidak akan memberi kesan apa-apa."));
        Power.add(new ProverbEntry("PO02","Gajah mati meninggalkan tulang, harimau mati meninggalkan belang, manusia mati meninggalkan nama.",
                "Orang yang banyak jasa tetap akan dikenang meskipun sudah meninggal dunia."));
        categorizedProverbs.put("Kekuasaan",Power);

        // Perseverance and Patience
        List<ProverbEntry> perseveranceAndPatience = new ArrayList<>();
        perseveranceAndPatience.add(new ProverbEntry("P01", "Alang-alang mandi biar basah.",
                "Melakukan sesuatu dengan sepenuh hati."));
        perseveranceAndPatience.add(new ProverbEntry("P02", "Bagai menarik rambut di dalam tepung.",
                "Melakukan sesuatu dengan hati-hati supaya kedua-dua pihak tidak terjejas."));
        perseveranceAndPatience.add(new ProverbEntry("P03", "Biar lambat asalkan selamat.",
                "Melakukan sesuatu dengan berhati-hati lebih baik walaupun lambat."));
        perseveranceAndPatience.add(new ProverbEntry("P04", "Setinggi-tinggi terbang bangau, akhirnya hinggap di belakang kerbau juga.",
                "Walau ke mana kita pergi, akhirnya kita akan pulang ke tempat asal."));
        perseveranceAndPatience.add(new ProverbEntry("P05", "Ikan biar dapat, serampang jangan pukah",
                "Pekerjaan yang dibuat itu hendaklah mendapat faedah supaya jerih payah jangan hilang percuma"));
        perseveranceAndPatience.add(new ProverbEntry("PO6","Disangkakan langit itu rendah, dipandang dekat, dicapai tak boleh.",
                "Sebelum mencuba sesuatu pekerjaan itu janganlah dikatakan mudah atau senang."));
        perseveranceAndPatience.add(new ProverbEntry("P07", "Jangan disesar gunung berlari, hilang kabut tampaklah dia.",
                " Sesuatu perkara yang telah tentu itu tidak perlu digopoh-gopohkan sangat (hendaklah sabar mengerjakannya)."));
        perseveranceAndPatience.add(new ProverbEntry("P08"," Malas berkayuh perahu hanyut, segan bertanya sesat jalan.",
                "Kalau segan berusaha tak akan mendapat kemajuan."));
        categorizedProverbs.put("Ketabahan dan Kesabaran", perseveranceAndPatience);

        // Relationships and Society
        List<ProverbEntry> relationshipsAndSociety = new ArrayList<>();
        relationshipsAndSociety.add(new ProverbEntry("R01", "Bulat air kerana pembuluh, bulat kata kerana muafakat.",
                "Kesepakatan hanya tercapai melalui perbincangan bersama."));
        relationshipsAndSociety.add(new ProverbEntry("R02", "Bagai aur gantung ke tebing.",
                "Bergantung pada sesuatu yang tidak kukuh."));
        relationshipsAndSociety.add(new ProverbEntry("R03", "Seperti telur di hujung tanduk.",
                "Keadaan yang sangat berbahaya atau tidak stabil."));
        relationshipsAndSociety.add(new ProverbEntry("R04", "Bagai kerbau dicucuk hidung.",
                "Seseorang yang menurut sahaja tanpa mempersoalkan."));
        relationshipsAndSociety.add(new ProverbEntry("R05","Bagai haruan makan anak.",
                "Bapa yang memperkosa anak sendiri"));
        relationshipsAndSociety.add(new ProverbEntry("R06","Belum tuarang panjang, buah sengkuang sebesar betis.",
                "Khabar angin yang dilebih-lebihkan. (Tuarang = musim kemarau)."));
        relationshipsAndSociety.add(new ProverbEntry("R07","Tak sungguh getah daun, yang sebenarnya getah batang.",
                "Sejahat-jahat saudara kandung, lebih baik juga daripada kerabat yang lain."));
        relationshipsAndSociety.add(new ProverbEntry("R08","Teras terunjam, gubal melayang.",
                "Penduduk bumiputera akan tetap tinggal di negerinya sedangkan orang asing akan keluar."));
        relationshipsAndSociety.add(new ProverbEntry("R09","Carik-carik bulu ayam lama-lama akan jereket semula.",
                "Perselisihan sesama keluarga akan berakhir dengan berbaik kembali."));
        relationshipsAndSociety.add(new ProverbEntry("R10","Seekor kerbau membawa lumpur, semuanya habis terpalit.",
                "Seorang yang berbuat salah, semuanya terbawa-bawa."));
        categorizedProverbs.put("Hubungan dan Masyarakat", relationshipsAndSociety);

        //Resilience and Strength
        List <ProverbEntry> resilienceAndStrength = new ArrayList<>();
        resilienceAndStrength.add(new ProverbEntry("SR01","Batu hitam tak bersanding",
                "Walaupun kelihatan lemah tetapi sukar mengalahkannya"));
        resilienceAndStrength.add(new ProverbEntry("SR02","Patah tongkat berjeremang, habis hulubalang bersiak.",
                "Terus berusaha (tidak pernah putus asa)."));
        resilienceAndStrength.add(new ProverbEntry("SR03","Patah tumbuh, hilang berganti.",
                "Terus menerus ada gantinya."));
        resilienceAndStrength.add(new ProverbEntry("SR04","Lemah liat kayu akar, dilentur boleh dipatah tak dapat.",
                "Pada lahirnya kelihatan lemah tetapi sebenarnya tidak dapat dipengaruhi atau dipermain-mainkan."));
        categorizedProverbs.put("Kekuatan dan Ketekunan", resilienceAndStrength);

        //Social Critique
        List <ProverbEntry> socialCritique = new ArrayList<>();
        socialCritique.add(new ProverbEntry("S01","Batu kecil berguling naik, batu besar berguling turun",
                "Orang yang hina menjadi mulia kerana harta dan orang yang berasal menjadi hina kerana tidak berharta"));
        socialCritique.add(new ProverbEntry("S02","Seperti anak dara mabuk andam",
                "Perempuan berasa dirinya cantik"));
        socialCritique.add(new ProverbEntry("S03","ayam hitam terbang malam, hinggap di pokok pandan, bergeresek ada, rupanya tiada.",
                "Kejahatan yang sulit dan diketahui orang tetapi tidak ada bukti yang nyata"));
        socialCritique.add(new ProverbEntry("S04","Bagai harimau beranak muda.",
                "Orang perempuan yang terlalu bengis."));
        socialCritique.add(new ProverbEntry("S05","Bercerai tidak bertalak, bernikah tidak berkadi",
                "Perhubungan jenis lelaki perempuan yang tidak sah (seperti pelacuran dan lain-lain)."));
        socialCritique.add(new ProverbEntry("S06"," Dempit tidak bersanggit, ditambat tidak bertali.",
                " Lelaki perempuan yang bersekedudukan dengan tidak bernikah."));
        socialCritique.add(new ProverbEntry("S07","85. Kasihan gajah berusung.",
                "Kasihan yang tidak bertempat."));
        socialCritique.add(new ProverbEntry("S08","Bunga bukan sekuntum, kumbang bukan seekor.",
                "Orang atau sesuatu yang sudah terlepas dari tangan boleh dicari ganti."));
        categorizedProverbs.put("Kritik Sosial",socialCritique);

        //Words and Communication
        List <ProverbEntry> wordsAndCommunication = new ArrayList<>();
        wordsAndCommunication.add(new ProverbEntry("WC01", "Keris lembing tiada tajam, lebih tajam mulut manusia.",
                "Kata-kata lebih tajam daripada senjata."));
        categorizedProverbs.put("Kata-kata dan Komunikasi",wordsAndCommunication);

        //Violence
        List <ProverbEntry> violence = new ArrayList<>();
        violence.add(new ProverbEntry("V01","Anak harimau diajar makan daging.",
                "Orang yang zalim digalakkan membunuh."));
        categorizedProverbs.put("Keganasan",violence);

        //Value and Perception
        List<ProverbEntry> valueAndPerception = new ArrayList<>();
        valueAndPerception.add(new ProverbEntry("VP01","Sama juga kain basahan sehelai, timah sesuku, kalau tak timah pergi terasa mendapat.",
                " Benda yang tiada berharga tiada berapa diendahkan orang, kecuali oleh orang yang benar-benar berkehendakkan benda itu."));
        valueAndPerception.add(new ProverbEntry("VP02","Sembelih ayam dengan pisau, sembelih orang dengan kapas",
                "Manusia harus diperlakukan dengan lemah lembut."));
        valueAndPerception.add(new ProverbEntry("VP03","Ibarat burung, mata lepas badan terkurung.",
                "Tiada kemerdekaan atau kebebasan."));
        categorizedProverbs.put("Nilai dan Persepsi",valueAndPerception);

        // Wisdom and Knowledge
        List<ProverbEntry> wisdomAndKnowledge = new ArrayList<>();
        wisdomAndKnowledge.add(new ProverbEntry("W01", "Seperti menarik rambut di dalam tepung.",
                "Melakukan sesuatu dengan hati-hati supaya kedua-dua pihak tidak terjejas."));
        wisdomAndKnowledge.add(new ProverbEntry("W02", "Biar putih tulang, jangan putih mata.",
                "Berani berjuang untuk mempertahankan sesuatu."));
        wisdomAndKnowledge.add(new ProverbEntry("W03", "Berkayuh sambil membaiki perahu.",
                "Melakukan dua perkara pada masa yang sama dengan berkesan."));
        wisdomAndKnowledge.add(new ProverbEntry("W04", "Sediakan payung sebelum hujan.",
                "Bersiap sedia sebelum sesuatu yang buruk berlaku."));
        wisdomAndKnowledge.add(new ProverbEntry("W05","Ular bukan, ikan pun bukan",
                "Seseorang yang tidak dapat ditentukan jahat atau baiknya."));
        wisdomAndKnowledge.add(new ProverbEntry("W06","Ular biar mati, benih jangan rosak",
                "Bijaksana menyelesaikan masalah"));
        wisdomAndKnowledge.add(new ProverbEntry("W07","Ikan terkilat, jala tiba",
                "Sangat cepat memahami maksud perkataan orang; tindakan yang cepat"));
        wisdomAndKnowledge.add(new ProverbEntry("W08","Tak usah diajar anak buaya berenang, ia sudah pandai sedia.",
                "Tidak usah diajar orang yang sudah tahu."));
        wisdomAndKnowledge.add(new ProverbEntry("W09","Seperti mengajar itik berenang.",
                "Mengaar seseorang yang arif atau pandai."));
        wisdomAndKnowledge.add(new ProverbEntry("W10", "Kecil tak boleh disangka anak, besar tak boleh disangka bapa.",
                "Pengetahuan dan kelebihan itu tidak khusus pada orang- orang tua sahaja."));
        categorizedProverbs.put("Kebijaksanaan dan Ilmu", wisdomAndKnowledge);


        return categorizedProverbs;
    }

    // Method to get explanations directly
    public static Map<String, String> getProverbExplanations() {
        Map<String, String> proverbsWithExplanations = new HashMap<>();

        // Add all proverbs with their explanations
        for (List<ProverbEntry> entries : getProverbsByCategoryWithKeys().values()) {
            for (ProverbEntry entry : entries) {
                proverbsWithExplanations.put(entry.getProverb(), entry.getExplanation());
            }
        }

        return proverbsWithExplanations;
    }
}
