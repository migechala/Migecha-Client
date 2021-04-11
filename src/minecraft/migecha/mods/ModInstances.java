package migecha.mods;

import migecha.gui.hud.HUDManager;
import migecha.mods.impl.CoordinateMod;
import migecha.mods.impl.FPSMod;
import migecha.mods.impl.KeystrokesMod;
import migecha.mods.impl.ModHelloWorld;

public class ModInstances {
	
	private static FPSMod modFPS;
	private static CoordinateMod modCord;
	private static ModHelloWorld modHelloWorld;
	private static KeystrokesMod modKeys;
	
	
	public static void register(HUDManager api) {
		modFPS = new FPSMod();
		modCord = new CoordinateMod();
		modHelloWorld = new ModHelloWorld();
		modKeys = new KeystrokesMod(1);
		api.register(modFPS, modCord, modKeys);
	}

}
