package edu.upc.backend.classes;

import java.util.List;

public class RankingResponse {
    private List<RankingEntry> podium;
    private RankingEntry userEntry;

    public RankingResponse() {
    }

    public RankingResponse(List<RankingEntry> podium, RankingEntry userEntry) {
        this.podium = podium;
        this.userEntry = userEntry;
    }

    public List<RankingEntry> getPodium() {
        return podium;
    }

    public void setPodium(List<RankingEntry> podium) {
        this.podium = podium;
    }

    public RankingEntry getUserEntry() {
        return userEntry;
    }

    public void setUserEntry(RankingEntry userEntry) {
        this.userEntry = userEntry;
    }
}

