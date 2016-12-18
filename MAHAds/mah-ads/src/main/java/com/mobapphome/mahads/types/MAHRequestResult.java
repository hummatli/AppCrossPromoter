package com.mobapphome.mahads.types;

import java.util.List;
import java.util.Map;

/**
 * Created by settar on 12/16/16.
 */

public class MAHRequestResult {
    private Map<String, List<Program>> filteredProgramsMap;
    private boolean success;

    public MAHRequestResult(Map<String, List<Program>> filteredProgramsMap, boolean success) {
        this.filteredProgramsMap = filteredProgramsMap;
        this.success = success;
    }

    public Map<String, List<Program>> getFilteredProgramsMap() {
        return filteredProgramsMap;
    }

    public boolean isSuccess() {
        return success;
    }
}
