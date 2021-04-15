package migecha.mods.impl;

import migecha.gui.hud.ScreenPosition;
import migecha.mods.ModDraggable;
import net.minecraft.client.Minecraft;

public class ClicksPerSecondMod extends ModDraggable {

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return font.getStringWidth("CPS: [xx, xx]");
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return font.FONT_HEIGHT;
	}

	@Override
	public void render(ScreenPosition pos) {
		font.drawString("CPS: [" + Minecraft.getMinecraft().thePlayer, pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, -1);
		
	}
	
	public void renderDummy(ScreenPosition pos) {
		font.drawString("CPS: [xx, xx]", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, 0xFF00FF00);
	}

}
