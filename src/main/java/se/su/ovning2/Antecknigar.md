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