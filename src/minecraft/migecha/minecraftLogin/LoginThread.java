package migecha.minecraftLogin;

import java.net.InetSocketAddress;
import java.net.Proxy;


import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;

public final class LoginThread
extends Thread {
    private final String password;
    private String status;
    private final String username;
    public boolean loggedIn;
    private Minecraft mc = Minecraft.getMinecraft();
    private final String proxyIP;

    
    public LoginThread(String username, String password, String proxyIP) {
        this.username = username;
        this.password = password;
        this.proxyIP = proxyIP;
        this.loggedIn = false;
        this.status = (Object)((Object)EnumChatFormatting.GRAY) + "Waiting...";
    }

    private Session createSession(String username, String password, String proxyIP) {
    	YggdrasilAuthenticationService service = null;
    	if(proxyIP == "") {
            service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
    	}
    	else {
    		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIP, 8080));
    		
            service = new YggdrasilAuthenticationService(proxy, "");
    	}
    	
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        }
        catch (AuthenticationException localAuthenticationException) {
            localAuthenticationException.printStackTrace();
            return null;
        }
    }

    
    public String getStatus() {
        return this.status;
    }
    public boolean getSuccess() {
    	return loggedIn;
    }
 
    @Override
    public void run() {
        this.status = (Object)((Object)EnumChatFormatting.YELLOW) + "Logging in...";
        Session auth = this.createSession(this.username, this.password, this.proxyIP);
        if (auth == null) {
            this.status = (Object)((Object)EnumChatFormatting.RED) + "Login failed!";
            this.loggedIn = false;
        } else {
            this.status = (Object)((Object)EnumChatFormatting.GREEN) + "Logged in. (" + auth.getUsername() + ")";
            System.out.println(this.loggedIn);
            this.mc.session = auth;
            this.loggedIn = true;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

