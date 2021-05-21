package com.stereowalker.rankup.stat;

import com.stereowalker.unionlib.config.CommentedEnum;

public enum LevelType implements CommentedEnum<Enum<?>>{
	ASSIGN_POINTS(""), UPGRADE_POINTS("");
	
	String comment;
	private LevelType(String commentIn) {
		this.comment = commentIn;
	}

	@Override
	public String getComment() {
		return this.comment;
	}

	@Override
	public Enum<?>[] getValues() {
		return LevelType.values();
	}
}
