package migecha.minecraftLogin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.io.Charsets;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ToFile {
	private List<String> array;
	private final ResourceLocation resource;
	
	public ToFile(ResourceLocation resource){
		this.resource = resource;
	}
	
	public void WriteInfo(List<String> list) throws IOException {
		File resource = new File(Minecraft.getMinecraft().getResourceManager().getResource(this.resource).getInputStream().toString());
		if(resource.exists() && resource.isFile()) {
			resource.delete();
		}
		resource.createNewFile();
		
		FileWriter fw = new FileWriter(resource);

		@SuppressWarnings("resource")
		BufferedWriter bufferedwriter = new BufferedWriter(fw);
		
	}
	
	public String GetInfo(int index){
        BufferedReader bufferedreader = null;
        try
        {
        	this.array = Lists.<String>newArrayList();
            bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().
            		getResourceManager().
            		getResource(this.resource).
            		getInputStream(), Charsets.UTF_8));
            String line;
            while ((line = bufferedreader.readLine()) != null)
            {
                line = line.trim();
                if (!line.isEmpty())
                {
                	this.array.add(line);
                }
            }
        }
        catch (IOException var12)
        {
            System.out.println("Couldn't find file " + this.resource.toString());
        }
        finally
        {
            if (bufferedreader != null)
            {
                try
                {
                    bufferedreader.close();
                }
                catch (IOException var11)
                {
                    ;
                }
            }
        }
        System.out.println(this.array.get(index));
        return this.array.get(index);
	}
	
}
