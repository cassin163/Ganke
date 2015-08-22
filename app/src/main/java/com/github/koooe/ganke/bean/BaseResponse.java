package com.github.koooe.ganke.bean;

import java.util.List;

public class BaseResponse {

    private boolean error;
    private List<DayData> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<DayData> getResults() {
        return results;
    }

    public void setResults(List<DayData> results) {
        this.results = results;
    }
}