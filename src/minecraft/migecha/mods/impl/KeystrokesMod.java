package migecha.mods.impl;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import migecha.gui.hud.ScreenPosition;
import migecha.mods.ModDraggable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;

public class KeystrokesMod extends ModDraggable{

	private static int scale;

	
	public KeystrokesMod(int scale){
		this.scale = scale;
	}
	
	public static enum KeyStrokes{
		
		WASD(Key.W, Key.A, Key.S, Key.D),
		WASD_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.RMB, Key.LMB),
		WASD_SPRINT(Key.W, Key.A, Key.S, Key.D, new Key("Sprint", Minecraft.getMinecraft().gameSettings.keyBindSprint, 1, 41, 58, 18)),
		WASD_SPRINT_MOUSE(Key.W, Key.A, Key.S, Key.D, new Key("Sprint", Minecraft.getMinecraft().gameSettings.keyBindSprint, 1, 61, 58, 18), Key.RMB, Key.LMB )
		;
		
		
		private final Key[] keys;
		private int w, h;
		
		private KeyStrokes(Key... keys) {
			this.keys = keys;
			
			for(Key key : keys) {
				this.w = Math.max(this.w, key.getX() + key.getWidth());
				this.h = Math.max(this.h, key.getY() + key.getHeight());

			}
		}
		public int getHeight() {
			return this.h;
		}
		public int getWidth() {
			return this.w;
		}
		public Key[] getKeys() {
			return this.keys;
		}
	}
	
	
	private static class Key{

		private static final Key W = new Key("W", Minecraft.getMinecraft().gameSettings.keyBindForward, 21, 1, 18, 18);
		private static final Key A = new Key("A", Minecraft.getMinecraft().gameSettings.keyBindLeft, 1, 21, 18, 18);
		private static final Key S = new Key("S", Minecraft.getMinecraft().gameSettings.keyBindBack, 21, 21, 18, 18);
		private static final Key D = new Key("D", Minecraft.getMinecraft().gameSettings.keyBindRight, 41, 21, 18, 18);

		
		private static final Key LMB = new Key("LMB", Minecraft.getMinecraft().gameSettings.keyBindAttack, 1, 41, 28, 18);
		private static final Key RMB = new Key("RMB", Minecraft.getMinecraft().gameSettings.keyBindUseItem, 32, 41, 28, 18);

		private final String name;
		private final KeyBinding keybind;
		private final int x;
		private final int y;
		private final int w;
		private final int h;
		
		public Key(String name, KeyBinding keybind, int x, int y, int w, int h) {
			this.name = name;
			this.keybind = keybind;
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}
		
		public boolean isDown(){ 
			return keybind.isKeyDown();
		}
		
		public int getHeight() {
			return this.h;
		}
		public int getWidth() {
			return this.w;
		}
		private String getName() {
			return this.name;
		}
		private int getX() {
			return this.x;
		}
		private int getY() {
			return this.y;
		}
	}
	
	private KeyStrokes mode = KeyStrokes.WASD_SPRINT_MOUSE;

	
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return mode.getWidth();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return mode.getHeight();
	}

	@Override
	public void render(ScreenPosition pos) {

		GL11.glPushMatrix();
		
		boolean blend = GL11.glIsEnabled(GL11.GL_BLEND);
		
		GL11.glDisable(GL11.GL_BLEND);
		
		for(Key key : mode.getKeys()) {
			int textWidth = font.getStringWidth(key.getName());
			
			Gui.drawRect(
					pos.getAbsoluteX() + key.getX(), pos.getAbsoluteY() + key.getY(),
					pos.getAbsoluteX() + key.getX() + key.getWidth(),
					pos.getAbsoluteY() + key.getY() + key.getHeight(),
					key.isDown() ? new Color(255, 255, 255, 90).getRGB() : new Color(0, 0, 0, 90).getRGB());
			
			font.drawString(key.getName(),
					pos.getAbsoluteX() + key.getX() + key.getWidth()/2 - textWidth/2,
					pos.getAbsoluteY() + key.getY() + key.getHeight()/2 - 4,
					key.isDown() ? Color.TRANSLUCENT : Color.GREEN.getRGB());
			}
		
		if(blend) {
			GL11.glEnable(GL11.GL_BLEND);
		}
		
		GL11.glPopMatrix();
	}


}
