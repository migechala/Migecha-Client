package migecha.gui.hud;

import java.awt.Color;
import java.awt.event.KeyEvent;


import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

import javax.swing.Renderer;

import org.lwjgl.input.Keyboard;

import java.util.function.Predicate;
import com.sun.jna.platform.win32.Wdm.KEY_BASIC_INFORMATION;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class HUDConfigScreen extends GuiScreen {
	
	private final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<IRenderer, ScreenPosition>();
	
	private Optional<IRenderer> selectedRenderer = Optional.empty();
	
	private int prevX, prevY;
	
	public HUDConfigScreen(HUDManager api) {
		
		Collection<IRenderer> registeredRenderers = api.getRegisterRenderers();
		if(registeredRenderers.isEmpty()) {
			System.out.println("No Registered Renderers");
		}
		else {
			System.out.println(registeredRenderers.size());
		}
		for(IRenderer ren : registeredRenderers) {
			if(!ren.isEnabled()) {
				continue;
			}
			
			ScreenPosition pos = ren.load();
			
			if(pos == null) {
				pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
			}
			
			
			adjustBounds(ren, pos);
			this.renderers.put(ren, pos);
		}
		
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		

		super.drawDefaultBackground();
		
		final float zBackup = this.zLevel;
		this.zLevel = 200;
		
		this.drawHollowRect(0, 0, this.width - 1, this.height -1, 0xFFFF0000);
		
		for(IRenderer renderer : renderers.keySet()) {
			
			ScreenPosition pos = renderers.get(renderer);
			
			renderer.renderDummy(pos);
			
			this.drawHollowRect(pos.getAbsoluteX(), pos.getAbsoluteY(), renderer.getWidth(), renderer.getHeight(), 0xFF00FFFF);
		}
		
		this.zLevel = zBackup;
		
	}

	private void drawHollowRect(int x, int y, int w, int h, int color) {
		this.drawHorizontalLine(x, x + w, y, color);
		this.drawHorizontalLine(x, x + w, y + h, color);
		
		this.drawVerticalLine(x, y + h, y, color);
		this.drawVerticalLine(x + w, y + h, y, color);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == Keyboard.KEY_ESCAPE) {
			renderers.entrySet().forEach((entry) -> {
				entry.getKey().save(entry.getValue());		
			});
			this.mc.displayGuiScreen(null);
		}
	}
	
	@Override
	protected void mouseClickMove(int x, int y, int button, long time) {
		if(selectedRenderer.isPresent()) {
			moveSelectedRenderBy(x - prevX, y - prevY);
			System.out.println("renderer is present");

		}
		
		this.prevX = x;
		this.prevY = y;
		
	}

	private void moveSelectedRenderBy(int offsetX, int offsetY) {
		IRenderer renderer = selectedRenderer.get();
		ScreenPosition pos = renderers.get(renderer);
		
		pos.setAbsolute(pos.getAbsoluteX() + offsetX, pos.getAbsoluteY() + offsetY);
		
		adjustBounds(renderer, pos);
	}
	
	@Override
	public void onGuiClosed() {
		if(renderers.isEmpty()) {
			System.out.println("No Registered Renderers for \"renderers\"");
		}
		else {
			System.out.println(renderers.size());
		}
		for(IRenderer renderer : renderers.keySet()) {
			renderer.save(renderers.get(renderer));
		}
		
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
	
	
	private void adjustBounds(IRenderer renderer, ScreenPosition pos) {
		
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		
		int screenWidth = res.getScaledWidth();
		int screenHeight = res.getScaledHeight();
		
	    int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - renderer.getWidth(), 0)));
	    int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - renderer.getHeight(), 0)));
		
		pos.setAbsolute(absoluteX, absoluteY);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int mobuttonuseButton) throws IOException {
		this.prevX = x;
		this.prevY = y;
		System.out.println("Mouse click");
		loadMouseOver(x, y);
		
	}

	private void loadMouseOver(int x, int y) {
		this.selectedRenderer = renderers.keySet().stream()
				.filter(new MouseOverFinder(x, y))
				.findFirst();
	}
	
	  private class MouseOverFinder implements Predicate<IRenderer> {
		    private int mousex;
		    
		    private int mousey;
		    
		    public MouseOverFinder(int x, int y) {
		      this.mousex = x;
		      this.mousey = y;
		    }
		    
		    public boolean test(IRenderer r) {
		      ScreenPosition screenpos = (ScreenPosition)HUDConfigScreen.this.renderers.get(r);
		      int absolutX = screenpos.getAbsoluteX();
		      int absolutY = screenpos.getAbsoluteY();
		      if (this.mousex >= absolutX && this.mousex <= absolutX + r.getWidth())
		        if (this.mousey >= absolutY && this.mousey <= absolutY + r.getHeight())
		          return true;  
		      return false;
		    }
		  }
	
}