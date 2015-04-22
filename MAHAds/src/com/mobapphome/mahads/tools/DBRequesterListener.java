package com.mobapphome.mahads.tools;

import java.util.List;

import com.mobapphome.mahads.types.Program;

public interface DBRequesterListener {
	public void onReadPrograms(List<Program> programs);
}
