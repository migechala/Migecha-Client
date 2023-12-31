package migecha.gui.hud;

import java.util.Collection;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

import migecha.event.EventManager;
import migecha.event.EventTarget;
import migecha.event.imp.RenderEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;

public class HUDManager {

	private HUDManager() {}
	
    private static HUDManager instance = null;

    public static HUDManager getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new HUDManager();
        EventManager.register(instance);

        return instance;
    }

    private Set<IRenderer> registerRenderers = Sets.newHashSet();
    private Minecraft mc = Minecraft.getMinecraft();

    public void register(IRenderer... renderers) {
        for (IRenderer render : renderers) {
            this.registerRenderers.add(render);
        }
    }

    public void unregister(IRenderer... renderers) {
        for (IRenderer render : renderers) {
            this.registerRenderers.remove(render);
        }
    }

    public Collection<IRenderer> getRegisterRenderers() {
        return Sets.newHashSet(registerRenderers);
    }

    public void openConfigScreen() {
        mc.displayGuiScreen(new HUDConfigScreen(this));
    }

    @EventTarget
    public void onRender(RenderEvent event) {
        if (mc.currentScreen == null || mc.currentScreen instanceof GuiChat || mc.currentScreen instanceof GuiContainer) {
            for (IRenderer renderer : registerRenderers) {
                callRenderer(renderer);
            }
        }
    }

    private void callRenderer(IRenderer renderer) {
        if (!renderer.isEnabled()) {
            return;
        }
        ScreenPosition pos = renderer.load();

        if (pos == null) {
            pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
        }
        renderer.render(pos);
    }
}

