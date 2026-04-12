# Programming 2: Exercise 2

**Course:** PROG2 VT2025  
**Presentation:** Tuesday Apr 14 via Zoom

## Overview

A record shop's IT system has become slow as the number of customers has grown.  
The current system uses an unsorted list to find recordings, which is too slow.  
Your job is to build a fast search system using the right data structures (`Map` and `Set`) — **no `List`s allowed**.

Customers ask questions like:

- Are there any records by Bruce Springsteen?
- What genres are available?
- Does the album *Thriller* exist?
- What recordings are newer than 2010?
- What jazz records exist between 1950 and 1960?

## Files Provided (from iLearn)

- `Data.java` — a registry of recordings, **do not modify**
- `Recording.java` — represents a recording (CD or LP)
- `SearchOperations.java` — the interface with all search methods
- `Searcher.java` — the class where you implement the search methods
- `Exercise2.java` — example program showing how `Searcher` is used
- `Exercise2Test.java` — JUnit tests

## What You Need to Do

### Step 1 — Modify `Recording.java`

Make `Recording` work as a key in `HashMap`/`TreeMap` and as a value in a `Set`.  
You must implement:

- `equals()` and `hashCode()` — two `Recording`s are equal if they have the same **title, artist, and year**. Type and genre are ignored.
- `Comparable` / `Comparator` — define a sort order if needed for any ordered data structures.

### Step 2 — Build data structures in `Searcher` constructor

In the constructor, loop through the `Collection<Recording>` and fill **at least two `Map` member variables**.  
Think about which structure makes each search method fast before you code.

**Suggested structures:**

- `HashMap<String, Recording>` — for fast lookup by title
- `HashMap<String, Set<Recording>>` — for lookup by artist
- `HashMap<String, Set<Recording>>` — for lookup by genre
- `TreeMap<Integer, Set<Recording>>` — for year‑based searches (uses `headMap` / `tailMap` / `subMap`)
- `HashSet<Recording>` — for fast duplicate checking (`offerHasNewRecordings`)

### Step 3 — Implement the 11 mandatory methods in `Searcher`

**Simple counters:**

- `numberOfArtists()` — returns count of unique artists
- `numberOfGenres()` — returns count of unique genres
- `numberOfTitles()` — returns count of unique titles

**Direct lookups:**

- `doesArtistExist(String name)` — returns `true`/`false`
- `getGenres()` — returns unmodifiable collection of all genres
- `getRecordingByName(String title)` — returns matching `Recording` or `null`

**Collection methods:**

- `getRecordingsAfter(int year)` — use `tailMap()` on `TreeMap`
- `getRecordingsByArtistOrderedByYearAsc(String artist)` — sorted ascending by year
- `getRecordingsByGenre(String genre)` — all recordings in a genre
- `getRecordingsByGenreAndYear(String genre, int from, int to)` — use `subMap()` for year range
- `offerHasNewRecordings(Collection<Recording> offer)` — return recordings in offer that don't exist in the database

### Step 4 — Test

Run the JUnit tests via **VPL in iLearn** or locally with `Exercise2Test.java`.  
All 11 mandatory methods must pass.

## Rules and Restrictions

- **No `List` classes anywhere** — no `ArrayList`, no `LinkedList`
- **No sequential loops in search methods** — only in the constructor to fill structures
- **Return unmodifiable collections** — use `Collections.unmodifiableXXX()`
- **Return empty collections (not `null`)** — use `Collections.emptyXXX()` when nothing found
- **`Searcher` must implement `SearchOperations`** — no extra `public` methods

## Suggested Work Split (3 people)

1. **Person 1** — `Recording.java`: `equals()`, `hashCode()`, `Comparable`
2. **Person 2** — `Searcher` constructor + simple methods:  
   `numberOfArtists`, `numberOfGenres`, `numberOfTitles`, `doesArtistExist`, `getGenres`, `getRecordingByName`
3. **Person 3** — Complex search methods:  
   `getRecordingsAfter`, `getRecordingsByArtistOrderedByYearAsc`, `getRecordingsByGenre`,  
   `getRecordingsByGenreAndYear`, `offerHasNewRecordings`

## Optional Extensions (bonus)

- `optionalGetRecordingsBefore(int year)` — use `headMap()`
- `optionalGetRecordingsByArtistOrderedByTitleAsc(String artist)` — sorted A–Z by title
- `optionalGetRecordingsFrom(int year)` — recordings from an exact year

## Key Reminder

The point of this exercise is to **choose the right data structure for each method**.  
A good solution requires **at least two `Map` member variables** and should **NOT** use sequential loops in the search methods.