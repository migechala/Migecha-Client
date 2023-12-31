package migecha.minecraftLogin;

import java.io.IOException;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

public final class GuiLogin
extends GuiScreen {
    private PasswordField password;
    private final GuiScreen previousScreen;
    private LoginThread thread;
    private GuiTextField username;
    private GuiTextField proxyIP;
    private GuiTextField proxyPassword;
    
    public GuiLogin(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 1: {
                this.mc.displayGuiScreen(this.previousScreen);
                break;
            }
            case 0: {
                this.thread = new LoginThread(this.username.getText(), this.password.getText(), this.proxyIP.getText());
                this.thread.start();
            }
        }
    }

    @Override
    public void drawScreen(int x2, int y2, float z2) {
        this.drawDefaultBackground();
        this.username.drawTextBox();
        this.password.drawTextBox();
        this.proxyIP.drawTextBox();
        this.proxyPassword.drawTextBox();
        this.drawCenteredString(this.mc.fontRendererObj, "Login", width / 2, 20, -1);
        this.drawCenteredString(this.mc.fontRendererObj, this.thread == null ? (Object)((Object)EnumChatFormatting.GRAY) + "Not Logged In" : this.thread.getStatus(), width / 2, 29, -1);
        if (this.username.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Username/Email", width / 2 - 96, 66, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Password", width / 2 - 96, 106, -7829368);
        }
        if (this.proxyIP.getText().isEmpty()) {
        	this.drawString(this.mc.fontRendererObj, "Proxy IP (leave blank if none)", width / 2 - 96, 146, -7829368);
        }
        if (this.proxyPassword.getText().isEmpty()) {
        	this.drawString(this.mc.fontRendererObj, "Proxy Password (UNAVALABLE)", width / 2 - 96, 186, -7829368);
        }
        super.drawScreen(x2, y2, z2);
    }

    @Override
    public void initGui() {
        int var3 = height / 4 + 24;
        this.buttonList.add(new GuiButton(0, width / 2 - 100, var3 + 100 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, var3 + 100 + 12 + 24, "Back"));

        this.username = new GuiTextField(var3, this.mc.fontRendererObj, width / 2 - 100, 60, 200, 20);
        this.password = new PasswordField(this.mc.fontRendererObj, width / 2 - 100, 100, 200, 20);
        this.proxyIP = new GuiTextField(var3, this.mc.fontRendererObj, width / 2 - 100, 140, 200, 20);
        this.proxyPassword = new GuiTextField(var3, this.mc.fontRendererObj, width / 2 - 100, 180, 200, 20);
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    protected void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (character == '\t') {
            if (!this.username.isFocused() && !this.password.isFocused()) {
                this.username.setFocused(true);
            } else {
                this.username.setFocused(this.password.isFocused());
                this.password.setFocused(!this.username.isFocused());
            }
        }
        if (character == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
            System.out.println(this.buttonList.get(1));
            
            System.out.println(thread.loggedIn);
            if(thread.loggedIn) {
                this.mc.displayGuiScreen(this.previousScreen);
            }
        }

        this.username.textboxKeyTyped(character, key);
        this.password.textboxKeyTyped(character, key);
        this.proxyIP.textboxKeyTyped(character, key);
        this.proxyPassword.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseClicked(int x2, int y2, int button) {
        try {
            super.mouseClicked(x2, y2, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(x2, y2, button);
        this.password.mouseClicked(x2, y2, button);
        this.proxyIP.mouseClicked(x2, y2, button);
        this.proxyPassword.mouseClicked(x2, y2, button);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
        this.proxyIP.updateCursorCounter();
        this.proxyPassword.updateCursorCounter();

    }
}

