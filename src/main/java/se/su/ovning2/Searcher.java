package se.su.ovning2;

import java.util.*;

public class Searcher implements SearchOperations {

  //Map som koppålar artistnamn till alla skivor av den artisten.
  //- Nyckel String: artistens namn.
  //- Värde Set<Recording>: alla Recording objekt med den artisten.
  private final Map<String, Set<Recording>> recordingsByArtist = new HashMap<>();
  private final Map<String, Set<Recording>> genreIndex = new HashMap<>();
  private final Map<String, TreeMap<Integer, Set<Recording>>> genreYearIndex = new HashMap<>();
  private final Set<Recording> allRecordings = new HashSet<>();
  private final Map<String, Recording> recordingsByTitle = new HashMap<>();
  private final TreeMap<Integer, Set<Recording>> recordingsByYear = new TreeMap<>();


  //Konstruktor som tar emot all data och fyller våra datastrukturer.
  //Bara här vi loopar igenom hela samlingen.
  //Metoder kan svara snabbt utan loop.
  public Searcher(Collection<Recording> data) {

    //Loopa igenom varje skiva från inkommande samling.
    for (Recording r: data) {

      //Hämta artistnamne från skivan. Blir nyckel i Map
      String artist = r.getArtist();

      // lägga till allRecordings
      allRecordings.add(r);

      // lägg till titel-map
      recordingsByTitle.put(r.getTitle(), r);

      //lägg till år map
      int year = r.getYear();
      if (!recordingsByYear.containsKey(year)) {
        recordingsByYear.put(year, new HashSet<>());
      }

      recordingsByYear.get(year).add(r);


      //Finns artisten redan som nyckel i map? Om inte skapa ny tom mängd.
      if (!recordingsByArtist.containsKey(artist)) {
        recordingsByArtist.put(artist, new HashSet<>());
      }

      //Hämta mängden och lägg till i skivan
      recordingsByArtist.get(artist).add(r);

      Collection<String> genres = r.getGenre();
      // hämta året
      int recordingYear = r.getYear();
      for(String g : genres) {
        if(g!=null && !g.isEmpty()) {
          genreIndex.computeIfAbsent(g , k -> new HashSet<>()).add(r); // kolla om det finns genre finns and lägg till om skivan.
        }

       genreYearIndex.computeIfAbsent(g, k-> new TreeMap<>())
               .computeIfAbsent(recordingYear, k-> new HashSet<>())
               .add(r);
      }

    }

    //Collection<Recording> recordings = data; GAMMAL från template
  }

  @Override
  public long numberOfArtists() {
    return recordingsByArtist.size();

  }

  @Override
  public long numberOfGenres() {
    // TODO Auto-generated method stub
    Set<String> genres = new HashSet<>();

    for(Set<Recording> recordings : recordingsByArtist.values()){
        for(Recording r : recordings){
            genres.addAll(r.getGenre());
        }
    }
    return genres.size();
  }

  @Override
  public long numberOfTitles() {
    // TODO Auto-generated method stub
    Set<String> titles = new HashSet<>();

    for(Set<Recording> recordings : recordingsByArtist.values()){
        for(Recording r : recordings){
            titles.add(r.getTitle());
        }
    }
    return titles.size();
  }

  @Override
  public boolean doesArtistExist(String name) {
    // TODO Auto-generated method stub
    return recordingsByArtist.containsKey(name);
  }

  @Override
  public Collection<String> getGenres() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getGenres'");
  }

  @Override
  public Recording getRecordingByName(String title) {
    return recordingsByTitle.get(title);

  }

  @Override
  public Collection<Recording> getRecordingsAfter(int year) {
    Map<Integer, Set<Recording>> subMap = recordingsByYear.tailMap(year);

    Set<Recording> result = new HashSet<>();

    for (Set<Recording> yearSet : subMap.values()) {
      result.addAll(yearSet);
    }
    return Collections.unmodifiableCollection(result);

  }

  @Override
  public SortedSet<Recording> getRecordingsByArtistOrderedByYearAsc(String artist) {
    Set<Recording> recordings = recordingsByArtist.get(artist);

    if (recordings == null) {
      return Collections.emptySortedSet();
    }

    TreeSet<Recording> sorted = new TreeSet<>(recordings);
    return Collections.unmodifiableSortedSet(sorted);
  }

  @Override
  public Collection<Recording> getRecordingsByGenre(String genre) {
    Set<Recording> recordings = genreIndex.get(genre);
    if(recordings == null) {
      return Collections.emptySet();
    }

    return Collections.unmodifiableCollection(recordings);
  }

  @Override
  public Collection<Recording> getRecordingsByGenreAndYear(String genre, int yearFrom, int yearTo) {
   TreeMap<Integer, Set<Recording>> yearMap = genreYearIndex.get(genre);

   if(yearMap == null) {
     return Collections.emptySet();
   }

   //check recordings within the given range YearFrom, YearTo
   NavigableMap<Integer, Set<Recording>> subMap = yearMap.subMap(yearFrom, true, yearTo, true);

   Set<Recording> result = new HashSet<>();
   for(Set<Recording> yearSet: subMap.values()) {
     result.addAll(yearSet);
   }

   return Collections.unmodifiableCollection(result);
  }

  @Override
  public Collection<Recording> offerHasNewRecordings(Collection<Recording> offered) {
    Set<Recording> result = new HashSet<>();

    for (Recording offeredRecording : offered) {
      if (offeredRecording == null) {
        continue;
      }
      if (!allRecordings.contains(offeredRecording)) {
        result.add(offeredRecording);
      }
    }

    return Collections.unmodifiableCollection(result);
  }
}
