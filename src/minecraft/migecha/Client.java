package migecha;

import migecha.event.EventManager; 
import migecha.event.EventTarget;
import migecha.event.imp.ClientTickEvent;
import migecha.gui.Progress;
import migecha.gui.hud.HUDManager;
import migecha.mods.ModInstances;
import net.minecraft.client.Minecraft;

public class Client {
	private static final Client INSTANCE = new Client();
	public static final Client getInstance() {
		return INSTANCE;
	}
	
	private DiscordRP discordRP = new DiscordRP();
	
	private HUDManager hudManager;
	
	public void init() {
		ManageFiles.init();
		Progress.setProg(1, "Client - Initializing discordRPC");
		discordRP.start();
		EventManager.register(this); 
	}
	
	public void start() {
		hudManager = HUDManager.getInstance();
		ModInstances.register(hudManager);
	}
	
	public void shutdown() {
		discordRP.shutdown();
	}

	public DiscordRP getDiscordRP() {
		return discordRP;
	}
	
	@EventTarget
	public void onTick(ClientTickEvent e) {
		if(Minecraft.getMinecraft().gameSettings.CLIENT_GUI_MOD_POS.isPressed()) {
			hudManager.openConfigScreen();
		}
	}
	
	
}
