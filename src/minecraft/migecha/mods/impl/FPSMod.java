package migecha.mods.impl;

import migecha.gui.hud.ScreenPosition;

import migecha.mods.ModDraggable;
import net.minecraft.client.Minecraft;


public class FPSMod extends ModDraggable {
private ScreenPosition pos;
	
	private int getFPS() {
		return Minecraft.debugFPS;
	}
	@Override
	public int getWidth() {
		return font.getStringWidth("FPS: " + this.getFPS());
	}

	@Override
	public int getHeight() {
		return font.FONT_HEIGHT;
	}

	@Override
	public void render(ScreenPosition pos) {
		font.drawString("FPS: " + this.getFPS(), pos.getAbsoluteX() , pos.getAbsoluteY(), 0xFFFFFFFF);
		
	}
	
	@Override
	public void renderDummy(ScreenPosition pos) {
		font.drawString("FPS: xxx", pos.getAbsoluteX(), pos.getAbsoluteY(), 0xFF00FF00);
	}

}
