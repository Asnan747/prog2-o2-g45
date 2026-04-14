package se.su.ovning2;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Data recordingsData = new Data();
        Set<Recording> mr = new HashSet<>();
        for(Recording r : recordingsData.getRecordings()) {
            if (r.getGenre().contains("Rock")) {
                mr.add(r);
            }
        }

        for(Recording rs : mr) {
            System.out.println(rs);
        }
    }
}
