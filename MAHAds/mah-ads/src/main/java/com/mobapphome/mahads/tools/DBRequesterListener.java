package com.mobapphome.mahads.tools;

import com.mobapphome.mahads.types.Program;

import java.util.List;

public interface DBRequesterListener {
	public void onReadPrograms(List<Program> programs);
}
