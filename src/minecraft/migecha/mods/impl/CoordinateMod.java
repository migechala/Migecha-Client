package migecha.mods.impl;

import migecha.gui.hud.ScreenPosition;
import migecha.mods.ModDraggable;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CoordinateMod  extends ModDraggable {
	private ScreenPosition pos;
	private BlockPos getCord() {
		return Minecraft.getMinecraft().thePlayer.getPosition();
	}
	@Override
	public int getWidth() {
		return font.getStringWidth("XYZ: xxx, xxx, xxx");
	}

	@Override
	public int getHeight() {
		return font.FONT_HEIGHT;
	}

	@Override
	public void render(ScreenPosition pos) {
		font.drawString("XYZ: " + this.getCord().getX() + ", " + this.getCord().getY() + ", " + this.getCord().getZ(), pos.getAbsoluteX() , pos.getAbsoluteY(), -1);
		
	}
	
	@Override
	public void renderDummy(ScreenPosition pos) {
		font.drawString("XYZ: xxx, xxx, xxx", pos.getAbsoluteX(), pos.getAbsoluteY(), 0xFF00FF00);
	}
}
