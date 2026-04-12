Vad som faktiskt står
Uppgiftstexten säger bara att Recording ska kunna fungera i både hashade och trädbaserade strukturer — om du skulle behöva det. Det är ett krav på klassens förmåga, inte ett krav på att du använder båda i lösningen.
Exakt formulering (4.2): "objekt kan fungera väl som nycklar i en avbildingstabell eller mängd (både hashade och trädbaserade)"
Och meningen direkt efter säger uttryckligen att sorteringsordning bara behövs "om det krävs för att lösa någon del av uppgiften".
Vad du faktiskt måste använda
Det enda hårda kravet på datastrukturer i hela PDF:en är:

Ingen List (ingen ArrayList, LinkedList).
Minst en TreeMap — indirekt, eftersom metoderna getRecordingsAfter, getRecordingsByGenreAndYear m.fl. kräver effektiva intervallfrågor på år, och det löses bäst med TreeMap via headMap/tailMap/subMap.
Minst två Maps totalt (PDF säger "En bra lösning kommer helt säkert kräva minst två avbildningar").

Set-typen (HashSet vs TreeSet) är helt upp till dig. För de flesta metoder räcker HashSet gott och väl — det är snabbare och enklare.
Praktiskt för din lösning
Du kommer sannolikt klara hela uppgiften med:

HashMap för de flesta uppslag (artist → skivor, genre → skivor, titel → skiva)
TreeMap för år-relaterade frågor (år → skivor)
HashSet som värden i dessa maps och för offerHasNewRecordings

TreeSet behövs typiskt inte alls. Comparable på Recording behövs typiskt inte heller.
Så: din Recording är klar. Gå vidare till Searcher.


    //package se.su.ovning2;

    // Vi importerar BARA det vi faktiskt använder i koden nedan.
    // Collection: för att ta emot inkommande data i konstruktorn.
    // Map + HashMap: vår medlemsvariabel som kopplar artist -> skivor.
    // Set + HashSet: mängden av skivor för varje artist (värdet i Map:en).
    //import java.util.Collection;
    //import java.util.HashMap;
    //import java.util.HashSet;
    //import java.util.Map;
    //import java.util.Set;

    //public class Searcher implements SearchOperations {

    // ───────────── MEDLEMSVARIABLER ─────────────
    //
    // En Map som kopplar ett artistnamn till alla skivor av den artisten.
    // - Nyckel (String): artistens namn, t.ex. "Miles Davis"
    // - Värde (Set<Recording>): alla Recording-objekt med den artisten
    //
    // Varför Map? För att uppslag på artistnamn ska vara snabbt (O(1)).
    // Varför Set som värde? För att samma skiva inte ska kunna läggas in
    // två gånger, och för att vi inte får använda List enligt uppgiften.
    //
    // "final" betyder att variabeln inte kan bytas ut mot ett annat Map-objekt
    // efter att den skapats. Vi kan fortfarande lägga in/ta bort saker I den.
    private final Map<String, Set<Recording>> recordingsByArtist = new HashMap<>();


    // ───────────── KONSTRUKTOR ─────────────
    //
    // Tar emot all data (alla skivor) och fyller våra datastrukturer.
    // Detta är det ENDA stället där vi loopar igenom hela samlingen.
    // Efter konstruktorn ska alla metoder kunna svara snabbt utan loopar.
    public Searcher(Collection<Recording> data) {

        // Loopa igenom varje skiva i inkommande samling.
        for (Recording r : data) {

            // Hämta artistnamnet från skivan. Detta blir vår nyckel i Map:en.
            String artist = r.getArtist();

            // Finns artisten redan som nyckel i Map:en?
            // Om inte, skapa en ny tom mängd (HashSet) för den artisten.
            if (!recordingsByArtist.containsKey(artist)) {
                recordingsByArtist.put(artist, new HashSet<>());
            }

            // Hämta mängden som hör till artisten (antingen den vi nyss
            // skapade, eller en som redan fanns) och lägg till skivan.
            recordingsByArtist.get(artist).add(r);
        }
    }


    // ───────────── METOD 1: numberOfArtists ─────────────
    //
    // Antalet UNIKA artister = antalet nycklar i vår Map.
    // .size() är snabb (O(1)) på en HashMap.
    //
    // Gränssnittet vill ha en "long", men size() returnerar "int".
    // Java konverterar automatiskt från int till long vid return,
    // så vi behöver inte göra något extra.
    @Override
    public long numberOfArtists() {
        return recordingsByArtist.size();
    }
}