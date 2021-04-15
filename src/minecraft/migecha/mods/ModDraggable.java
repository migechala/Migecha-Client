package migecha.mods;

import java.io.File;

import migecha.ManageFiles;
import migecha.gui.hud.IRenderer;
import migecha.gui.hud.ScreenPosition;

public abstract class ModDraggable extends Mod implements IRenderer{

	protected ScreenPosition pos;
	private final String positionFiles = "pos.json";
	
	public ModDraggable() {
		pos = loadPositions();
	}
	
	private ScreenPosition loadPositions() {
		ScreenPosition loaded = ManageFiles.readJson(new File(getBaseDir(), positionFiles), ScreenPosition.class);
		
		if(loaded == null) {
			loaded = ScreenPosition.fromRelativePosition(0.5, 0.5);
			this.pos = loaded;
			savePositions();
		}
		
		return loaded;
	}
	private void savePositions() {
		ManageFiles.writeJsonFile(new File(getBaseDir(), positionFiles), pos);
	}
	
	private File getBaseDir() {
		File folder = new File(ManageFiles.getModDir(), this.getClass().getSimpleName());
		folder.mkdir();
		return folder;
	}
	
	@Override
	public ScreenPosition load() {
		return pos;
	}
	
	@Override
	public void save(ScreenPosition pos) {
		this.pos = pos;
		savePositions();
	}
	
	public final int getLineOffset(ScreenPosition pos, int lineNum) {
		return pos.getAbsoluteY() + getLineOffset(lineNum);
	}

	private int getLineOffset(int lineNum) {
		return (font.FONT_HEIGHT + 3) * lineNum;
	}
}
