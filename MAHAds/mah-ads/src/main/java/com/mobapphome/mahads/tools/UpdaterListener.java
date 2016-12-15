package com.mobapphome.mahads.tools;

import com.mobapphome.mahads.types.Program;

import java.util.List;

public interface UpdaterListener {
	public void onSuccsess(List<Program> programs);
	public void onError(String errorStr);
}
