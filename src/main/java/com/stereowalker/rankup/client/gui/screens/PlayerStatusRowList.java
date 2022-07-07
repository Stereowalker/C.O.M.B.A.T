package com.stereowalker.rankup.client.gui.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;

public class PlayerStatusRowList<E extends ContainerObjectSelectionList.Entry<E>> extends ContainerObjectSelectionList<E> {
	int widgetWidth;
	public PlayerStatusRowList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int itemHeightIn) {
		super(mcIn, widthIn, heightIn, topIn, bottomIn, itemHeightIn);
		this.x0+=(widthIn/2);
		this.x1+=(widthIn/2);
		this.widgetWidth = widthIn;
//		this.setRenderBackground(false);
//		this.setRenderTopAndBottom(false);
	}

	@Override
	public int getRowWidth() {
		return this.widgetWidth;
	}

	@Override
	protected int getScrollbarPosition() {
		return this.x0 + widgetWidth - 5;
	}

}
