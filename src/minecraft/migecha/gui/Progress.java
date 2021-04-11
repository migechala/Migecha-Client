package migecha.gui;

import java.awt.Color;


import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;

public class Progress {
	private static final int max = 7;
	private static int progress = 0;
	private static String current = "";
	private static ResourceLocation splash;
	private static UnicodeFontRenderer ufr;
	
	public static void update() {
		if(Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getLanguageManager() == null) {
			return;
		}
		drawSplash(Minecraft.getMinecraft().getTextureManager());
	}
	public static void setProg(int givenProg, String givenText) {
		progress =  givenProg;
		current = givenText;
		update();
	}
	public static void drawSplash(TextureManager tm) {
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		int scaledF = scaledResolution.getScaleFactor();
		
		Framebuffer framebuf = new Framebuffer(scaledResolution.getScaledWidth() * scaledF, scaledResolution.getScaledHeight() * scaledF, true);
		framebuf.bindFramebuffer(false);
		
		GlStateManager.matrixMode(GL11.GL_PROJECTION);
		GlStateManager.loadIdentity();
		GlStateManager.ortho(0.0D, (double)scaledResolution.getScaledWidth(), (double)scaledResolution.getScaledHeight(), 0.0D, 1000.0, 3000.0);
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		GlStateManager.loadIdentity();
		GlStateManager.translate(0.0F, 0.0F, -2000.0F);
		GlStateManager.disableLighting();
		GlStateManager.disableFog();
		GlStateManager.disableDepth();
		GlStateManager.enableTexture2D();
		if(splash == null) {
			splash = new ResourceLocation("migecha/splash.png");
		}
		tm.bindTexture(splash);
		GlStateManager.resetColor();
		GlStateManager.color(1.0f, 1.0F, 1.0F, 1.0F);
		
		Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, 3830, 2160, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 3830, 2160);
		drawProg();
		framebuf.unbindFramebuffer();
		framebuf.framebufferRender(scaledResolution.getScaledWidth() * scaledF, scaledResolution.getScaledHeight() * scaledF);
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(516, 0.1F);
		
		Minecraft.getMinecraft().updateDisplay();
	}
	private static void drawProg() {
		if(Minecraft.getMinecraft().gameSettings == null || Minecraft.getMinecraft().getTextureManager() == null) {
			return;
		}
		
		if(ufr == null) {
			ufr = UnicodeFontRenderer.getFontOnPC("Arial", 20);
		}
		ScaledResolution s = new ScaledResolution(Minecraft.getMinecraft());
		
		double nProg = (double)progress;
		double calc = (nProg / max) * s.getScaledWidth();
		Gui.drawRect(0, s.getScaledHeight() - 35, s.getScaledWidth(), s.getScaledHeight(), new Color(0,0,0,50).getRGB());
		
		GlStateManager.resetColor();
		resetTextState();
		
		ufr.drawString(current, 20, s.getScaledHeight() - 25, 0xFFFFFF);
		
		String step = progress + "/" + max;
		ufr.drawString(step, s.getScaledWidth() - 20 - ufr.getStringWidth(step), s.getScaledHeight() - 25, 0x3434EB);
		
		GlStateManager.resetColor();
		resetTextState();
		
		Gui.drawRect(0, s.getScaledHeight() - 2, (int)calc, s.getScaledHeight(), 0x3434EB);
		
		Gui.drawRect(0, s.getScaledHeight() - 2, s.getScaledWidth(), s.getScaledHeight(), 0x000000);
		
	}
	private static void resetTextState() {
		GlStateManager.textureState[GlStateManager.activeTextureUnit].textureName = -1;
	}
}
