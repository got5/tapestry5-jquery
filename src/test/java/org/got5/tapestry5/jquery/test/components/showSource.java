package org.got5.tapestry5.jquery.test.components;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.PersistentLocale;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.slf4j.Logger;


/**
 * Component for displaying a code source
 *
 */

@Import(library = {"jquery.snippet.js","my-snippet.js"}, 
		stylesheet = { "jquery.snippet.css"})
public class showSource {

	/**
	 * Code Source path
	 */
	@Parameter(required=true, defaultPrefix=BindingConstants.LITERAL)
	private String path;
	
	/**
	 * Specs for the JQuery Plugin
	 */
	@Parameter(required=true, defaultPrefix=BindingConstants.PROP)
	private JSONObject specs;
	
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
	private Messages message;
	
	@SetupRender
	private boolean setupRender()
	{	
		if(InternalUtils.isBlank(srcDir))
		{
			logger.warn("We have to specify the path of your project in the AppModule file");
			
			return false;
		}
		
		langs = new HashMap<String, String>();
		
		langs.put("js", "javascript");
		
		langs.put("tml", "html");
		
		extension  = path.substring((path.lastIndexOf('.')+1));
		
		lang = extension;
		
		if(langs.get(extension) != null) 
			lang = langs.get(lang);
		
		return true;
	}
	
	public String getSrcContent() 
	{
		StringBuffer buffer = new StringBuffer();
		
		InputStream is = null;	
		
		File file = null;
			
		file = new File(srcDir+"/"+path);
		   		    
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
				InputStreamReader reader = new InputStreamReader(is);
				
				BufferedReader buffReader = new BufferedReader(reader);

				buffer.append(new String(new byte[] { Character.LINE_SEPARATOR }));
				
				String line = buffReader.readLine();
				
				while (line != null) 
				{
					buffer.append(line);
					
					buffer.append(new String(new byte[] { Character.LINE_SEPARATOR }));
					
					line = buffReader.readLine();
				}
				
				buffReader.close();
				
				reader.close();

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
		
		if(specs.isNull("showMsg")) 
			params.put("showMsg", message.get("ShowSource-showMsg"));
		else params.put("showMsg", specs.get("showMsg"));
		
		if(specs.isNull("hideMsg")) 
			params.put("hideMsg", message.get("ShowSource-hideMsg"));
		else params.put("hideMsg", specs.get("hideMsg"));
		
		if(specs.isNull("style")) 
			params.put("style", message.get("ShowSource-style"));
		else params.put("style", specs.get("style"));
		
		if(specs.isNull("collapse")) 
			params.put("collapse", Boolean.parseBoolean(message.get("ShowSource-collapse")));
		else params.put("collapse", specs.get("collapse"));
		
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
}
