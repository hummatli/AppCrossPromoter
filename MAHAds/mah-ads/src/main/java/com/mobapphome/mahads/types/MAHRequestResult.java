package com.mobapphome.mahads.types;

import java.util.List;

/**
 * Created by settar on 12/16/16.
 */

public class MAHRequestResult {
    private List<Program> programs;
    private boolean success;

    public MAHRequestResult(List<Program> programs, boolean success) {
        this.programs = programs;
        this.success = success;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public boolean isSuccess() {
        return success;
    }
}
