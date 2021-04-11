package migecha.minecraftLogin;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.Charsets;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import migecha.minecraftLogin.ToFile;
public class AutoLogin {
	private ToFile file;
	private LoginThread thread;
	private final ResourceLocation resource;
	private List<String> LoginCred;
	public AutoLogin(ResourceLocation resource) {
		this.file = new ToFile(resource);
		this.resource= resource;
	}
	private String getUsername() {
//		System.out.println(this.GetInfo(1));
		
		return this.file.GetInfo(0);
	}
	private String getPassword() {
//		System.out.println(this.GetInfo(2));

		return this.file.GetInfo(1);
	}
	private String getProxy() {
//		System.out.println(this.GetInfo(3));
		return "";
//		return this.file.GetInfo(2);
	}
	public boolean isLoggedIn() {
		return thread.loggedIn;
	}
	
	
	public boolean tryLogin() {
		this.thread = new LoginThread(this.getUsername(), this.getPassword(), this.getProxy());
        this.thread.start();
		if(thread.getSuccess()) {
			return true;
		}
		return false;
	}
	
	
}
