package com.mobapphome.mahads.types;

import java.util.List;

/**
 * Created by settar on 12/16/16.
 */

public class MAHRequestResult {
    private List<Program> programsTotal;
    private List<Program> programsFiltered;
    private List<Program> programsSelected;
    private ResultState resultState;

    public enum ResultState {SUCCESS, ERR_JSON_IS_NULL_OR_EMPTY, ERR_JSON_HAS_TOTAL_ERROR, ERR_SOME_ITEMS_HAS_JSON_SYNTAX_ERROR}

    public MAHRequestResult(List<Program> programsTotal, ResultState resultState) {
        this.programsTotal = programsTotal;
        this.resultState = resultState;
    }

    public List<Program> getProgramsTotal() {
        return programsTotal;
    }

    public ResultState getResultState() {
        return resultState;
    }

    public List<Program> getProgramsSelected() {
        return programsSelected;
    }

    public void setProgramsSelected(List<Program> programsSelected) {
        this.programsSelected = programsSelected;
    }

    public List<Program> getProgramsFiltered() {
        return programsFiltered;
    }

    public void setProgramsFiltered(List<Program> programsFiltered) {
        this.programsFiltered = programsFiltered;
    }
}