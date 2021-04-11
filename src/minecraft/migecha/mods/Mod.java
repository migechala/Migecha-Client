package migecha.mods;

import net.minecraft.client.gui.FontRenderer;
import migecha.Client;
import migecha.event.EventManager;
import net.minecraft.client.Minecraft;
public class Mod {
	private boolean isEnabled = true;
	protected final Minecraft mc;
	protected final FontRenderer font;
	protected final Client client;
	
	public Mod() {
		this.mc = Minecraft.getMinecraft();
		this.font = mc.fontRendererObj;
		this.client = Client.getInstance();
		
		setEnabled(isEnabled);
	}
	
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
		
		if(isEnabled) {
			EventManager.register(this);
			
		}
		else {
			EventManager.unregister(this);
		}
	}
	public boolean isEnabled() {
		return this.isEnabled;
	}
}
