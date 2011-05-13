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
	
	private JSONObject defaultSpecs;
	
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
		
		if(componentResources.isBound("ext"))
		{
			lang = ext;
		}
		else if(componentResources.isBound("path"))
		{

			langs = new HashMap<String, String>();
			
			langs.put("js", "javascript");
			
			langs.put("tml", "html");
			
			extension  = path.substring((path.lastIndexOf('.')+1)); 
			
			if(langs.get(extension) != null) 
				lang = langs.get(extension);
			else lang = extension;
		}
		
		
		/**
		 * Init the Default parameter for the jQuery plugin
		 */
		defaultSpecs = new JSONObject();
		
		defaultSpecs.put("showMsg", message.get("ShowSource-showMsg"));
			
		defaultSpecs.put("hideMsg", message.get("ShowSource-hideMsg"));
			
		defaultSpecs.put("style", message.get("ShowSource-style"));
			
		defaultSpecs.put("collapse", message.get("ShowSource-collapse"));
			
		defaultSpecs.put("showNum", message.get("ShowSource-showNum"));
		
		defaultSpecs.put("clipboard", assetSource.getUnlocalizedAsset("context:js/ZeroClipboard.swf").toClientURL());
		
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
												
						buffer.append(line);
						
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
	
	@AfterRender
	public void afterRender()
	{
		JSONObject params = new JSONObject();
		
		params.put("lang", lang);
		
		params.put("beginLine", beginLine);
		
		JQueryUtils.merge(defaultSpecs,specs);
		
		defaultSpecs.put("collapse", Boolean.parseBoolean(defaultSpecs.getString("collapse")));
		
		defaultSpecs.put("showNum", Boolean.parseBoolean(defaultSpecs.getString("showNum")));
		
		JQueryUtils.merge(params, defaultSpecs);
		
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

	public JSONObject getDefaultSpecs() {
		return defaultSpecs;
	}

	public void setDefaultSpecs(JSONObject defaultSpecs) {
		this.defaultSpecs = defaultSpecs;
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
