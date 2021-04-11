package migecha;

import net.arikia.dev.drpc.DiscordEventHandlers;

import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRP {
	
	private boolean running = true;
	private long created = 0;
	
	public void start() {
		
		this.created = System.currentTimeMillis();
		
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
			@Override
			public void apply(DiscordUser user) {
				System.out.println("Websome " + user.username + "#" + user.discriminator + ".");
				update("Booting up...", "");
			}
		}).build();
		
		DiscordRPC.discordInitialize("799009259865374741", handlers, true);
		
		new Thread("Discord RPC Callback") {
			
			@Override
			public void run() {
				while(running) {
					DiscordRPC.discordRunCallbacks();
				}
			}
			
		}.start();
		
	}
	public void shutdown() {
		running = false;
		DiscordRPC.discordShutdown();
	}
	public void update(String firstLine, String secondLine) {
		DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(secondLine);
		builder.setBigImage("largeclient", "");
		builder.setDetails(firstLine);
		builder.setStartTimestamps(created);
		
		DiscordRPC.discordUpdatePresence(builder.build());
	}
	
}
