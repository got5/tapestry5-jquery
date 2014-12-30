package org.got5.tapestry5.jquery.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.utils.JQueryUtils;
import org.slf4j.Logger;

/**
 * Component for displaying a code source
 * 
 * @since 2.1.1
 * 
 * @tapestrydoc
 */
@Import(stylesheet = { "${jquery.assets.root}/vendor/components/showsource/codemirror.css" })
public class ShowSource {

	/**
	 * Code Source path
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String path;

	/**
	 * Specs for the JQuery Plugin
	 */
	@Parameter(defaultPrefix = BindingConstants.PROP)
	private JSONObject specs;

	/**
	 * The ClientId of the ShowSource component
	 */
	@Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;

	/**
	 * The language you want to use for your snippet. If not bound, we will use
	 * the extension of the file.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String ext;

	/**
	 * Where to Start ? By default line 0.
	 */
	@Parameter(value = "0")
	private Integer beginLine;

	/**
	 * Where to finish ?
	 */
	@Parameter
	private Integer endLine;

	private JSONObject defaultSpecs;

	@Inject
	private AssetSource assetSource;

	@Inject
	private Logger logger;

	@Inject
	private JavaScriptSupport support;

	@Inject
	@Symbol("demo-src-dir")
	private String srcDir;

	@Inject
	private ComponentResources componentResources;

	@Inject
	private Messages message;

	boolean setupRender() {

		if (!componentResources.isBound("path")) {

			logger.warn("You have to specify a path for the showSource component");

			return false;
		}

		if (componentResources.isBound("endLine")) {

			if (endLine < beginLine) {

				logger.warn("The endLine parameter has to be greater than beginLine");

				return false;
			}
		}

		/**
		 * Init the Default parameter for the jQuery plugin
		 */
		defaultSpecs = new JSONObject();
		if (beginLine > 0) {

			defaultSpecs.put("firstLineNumber", beginLine);
		}
		defaultSpecs.put("readOnly", true);
		defaultSpecs.put("lineNumbers", true);
		// defaultSpecs.put("clipboard",
		defaultSpecs.put(
				"clipboard",
				assetSource.getUnlocalizedAsset(
						"META-INF/modules/tjq/source.js").toClientURL());

		return true;
	}

	public String getSrcContent() {

		final StringBuilder builder = new StringBuilder();

		InputStream is = null;

		File file = null;

		final String rootSrc = InternalUtils.isBlank(srcDir) ? String.format(
				"%s%s", System.getProperty("user.dir"), "/src/test/") : srcDir;
		final String pathFile = String.format("%s%s%s", rootSrc,
				File.separator, path);

		logger.info("The ShowSource Component displays the file : {}", pathFile);

		file = new File(pathFile);

		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException fnfEx) {
			logger.error("Error file not found.");
		}

		if (is != null) {

			try {

				final BufferedReader buffReader = new BufferedReader(
						new InputStreamReader(is));
				String line = buffReader.readLine();

				int numLine = 1;
				while (line != null) {

					if (numLine >= beginLine) {

						if (componentResources.isBound("endLine")
								&& numLine > endLine) {

							break;
						}

						builder.append(
								new String(
										new byte[] { Character.LINE_SEPARATOR }))
								.append(line);
					}

					numLine++;
					line = buffReader.readLine();
				}

				buffReader.close();

			} catch (IOException ioEx) {

				builder.append(ioEx.getMessage());

			} finally {

				IOUtils.closeQuietly(is);
			}
		}

		return builder.toString();
	}

	public void afterRender() {

		final JSONObject params = new JSONObject();

		params.put("id", getClientId());
		params.put("lang", getLanguage());
		params.put("beginLine", beginLine);

		JQueryUtils.merge(defaultSpecs, specs);
		params.put("options", defaultSpecs);

		support.require("tjq/source").with(params);
	}

	public String getLanguage() {

		if (componentResources.isBound("ext")) {

			return ext;
		}

		final Map<String, String> langs = new HashMap<String, String>();
		langs.put("js", "javascript");
		langs.put("java", "javascript");
		langs.put("tml", "html");
		langs.put("html", "html");

		final String extension = path.substring((path.lastIndexOf('.') + 1));

		return langs.get(extension);
	}

	public String getClientId() {

		return clientId;
	}

	public String getFilename() {

		return path.substring(path.lastIndexOf("/") + 1);
	}
}