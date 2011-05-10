package org.got5.tapestry5.jquery.test.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.PersistentLocale;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.utils.JQueryUtils;
import org.slf4j.Logger;

/**
 * Component for displaying a code source
 *
 */

@Import(library = {"context:js/jquery.snippet.js",
				  "context:js/my-snippet.js"}, 
		stylesheet = { "context:css/jquery.snippet.css"})
public class showSource {

	/**
	 * Code Source path
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String path;
	
	/**
	 * Specs for the JQuery Plugin
	 */
	@Parameter(defaultPrefix=BindingConstants.PROP)
	private JSONObject specs;
	
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String ext;
	
	@Parameter(value="0")
	private Integer beginLine;
	
	@Parameter 
	private Integer endLine;
	
	/**
	 * Code Source Lang for the JQuery Plugin
	 */
	private String lang;
	
	/**
	 * Code Source Extension 
	 */
	private String extension;
	
	@Inject
	private AssetSource assetSource;
	
	@Inject
	private PersistentLocale locale;
	
	@Inject 
	private Logger logger;
	
	@Inject
    private JavaScriptSupport support;
	
	private Map<String, String> langs;
	
	@Inject
    @Symbol("demo-src-dir")
    private String srcDir;
	
	@Inject 
	private ComponentResources componentResources;
	
	@Inject 
	private Messages message;
	
	@Inject 
	private Block fromBody;
	
	@Inject 
	private Block fromFile;
	
	@SetupRender
	private boolean setupRender()
	{	
		
		
		
		if(componentResources.isBound("path") && 
				componentResources.getBody().toString()
					.equalsIgnoreCase("<PlaceholderBlock>")){
			
			logger.warn("We have to specify a path " +
					"or a body for the showSource component");
			
		}
		
		if(componentResources.isBound("endLine"))
		{
			if(endLine<beginLine)
			{
				logger.warn("The endLine parameter has to be greater than beginLine");
				
				return false;
			}
		}
		
		langs = new HashMap<String, String>();
		
		langs.put("js", "javascript");
		
		langs.put("tml", "html");
		
		if(componentResources.isBound("path"))
			extension  = path.substring((path.lastIndexOf('.')+1));
		else extension = ext; 
			
		lang = extension;
		
		if(langs.get(extension) != null) 
			lang = langs.get(lang);
		
		return true;
	}
	
	public Block getChooseBlock(){
		
		if(componentResources.isBound("path")) return fromFile;
		
		return fromBody;
		
	}
	
	public String getSrcContent() 
	{
		StringBuffer buffer = new StringBuffer();
		
		InputStream is = null;	
		
		File file = null;
		
		String rootSrc; 
		
		if(InternalUtils.isBlank(srcDir)) 
			rootSrc=System.getProperty("projectPath")
				.substring(0,(System.getProperty("projectPath").length()-13));
		else rootSrc=srcDir;  
		
		file = new File(rootSrc+"/"+path);
		
		try 
		{
			is = new FileInputStream(file);
		} 
		catch (FileNotFoundException fnfEx) 
		{
			logger.error("Error file.");
		}
		
		
		if (is != null) 
		{	
			try 
			{
				Integer numLine = 1;
				
				int goodNumberWhiteSpace = getNumberWhiteSpace(new FileInputStream(file));
				
				BufferedReader buffReader = new BufferedReader(new InputStreamReader(is));

				buffer.append(new String(new byte[] { Character.LINE_SEPARATOR }));
				
				String line = buffReader.readLine();
				
				while (line != null) 
				{
					if(numLine>=beginLine)
					{
						if(componentResources.isBound("endLine")){
							
							if(numLine>endLine) break;
							
						}
												
						buffer.append(deleteSpace(line,goodNumberWhiteSpace));
						
						buffer.append(new String(new byte[] { Character.LINE_SEPARATOR }));
						
						
					}
					
					numLine++;
					
					line = buffReader.readLine();
				}
				
				buffReader.close();
				
			} 
			catch (IOException ioEx) 
			{
				buffer.append(ioEx.getMessage());
			} 
			finally 
			{
				if(is != null)
				{
					try 
					{
						is.close();
					}
					catch(Exception ioEx)
					{
						logger.error("Error closing the Asset");
					}
				}
			}
		}
		
		return buffer.toString();
		
	}
	
	public String deleteSpace(String line,int goodNumberWhiteSpace)
	{
		try
		{
			return line.substring(goodNumberWhiteSpace);
		}
		catch(Exception e)
		{
			return line;
		}
	}
	public int getNumberWhiteSpace(InputStream is)
	{
		int goodNumberWhiteSpace = 0;
		
		int NumberWhiteSpace = 0;
		
		Integer numLine = 1;
		
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(is));
		
		if(beginLine==0) return 0;
		
		try
		{
			String line = buffReader.readLine();
		
			
			while(line!=null)
			{	
				
				if(componentResources.isBound("endLine")){
					
					if(numLine>endLine) break;
					
				}
				
				NumberWhiteSpace = 0;
				
				for(int i = 0; i < line.length(); ++i)
				{
					if(Character.isWhitespace(line.charAt(i)))
					{
						NumberWhiteSpace++;
					}
					
				}
				
				if(numLine>=beginLine)
				{
					
					if(goodNumberWhiteSpace==0) 
						goodNumberWhiteSpace=NumberWhiteSpace;
					else goodNumberWhiteSpace = Math.min(goodNumberWhiteSpace, NumberWhiteSpace);
					
				}
				
				numLine++;
				
				line = buffReader.readLine();
				
			}
			
			return (goodNumberWhiteSpace);
		}
		catch(Exception e)
		{
			
		}
		
		return 0;
	}
		
	@AfterRender
	public void afterRender()
	{
		JSONObject params = new JSONObject();
		
		params.put("lang", lang);
		
		if(!componentResources.isBound("specs")){
			
			specs = new JSONObject();
			
			specs.put("showMsg", message.get("ShowSource-showMsg"));
			
			specs.put("hideMsg", message.get("ShowSource-hideMsg"));
			
			specs.put("style", message.get("ShowSource-style"));
			
			specs.put("collapse", Boolean.parseBoolean(message.get("ShowSource-collapse")));
			
			specs.put("showNum", Boolean.parseBoolean(message.get("ShowSource-showNum")));
		}
		
		JQueryUtils.merge(params, specs);
		
		support.addInitializerCall("source", params);
		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Map<String, String> getLangs() {
		return langs;
	}

	public void setLangs(Map<String, String> langs) {
		this.langs = langs;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public JSONObject getSpecs() {
		return specs;
	}

	public void setSpecs(JSONObject specs) {
		this.specs = specs;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
}
