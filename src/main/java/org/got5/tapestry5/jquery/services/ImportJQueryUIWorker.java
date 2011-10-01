package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Flow;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.func.Worker;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.ioc.internal.util.TapestryException;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.TransformConstants;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;
import org.got5.tapestry5.jquery.internal.TapestryJQueryExceptionMessages;


public class ImportJQueryUIWorker implements ComponentClassTransformWorker2
{
	
	private final AssetSource assetSource;

    private final JavaScriptSupport javaScriptSupport;

    private final String jqueryUIBase;

    private final boolean productionMode;

    /**
     * @param javaScriptSupport
     */
    public ImportJQueryUIWorker(AssetSource assetSource,

            JavaScriptSupport javaScriptSupport,

            @Symbol(JQuerySymbolConstants.JQUERY_UI_PATH)
            String jqueryUIBase,

            @Symbol(SymbolConstants.PRODUCTION_MODE)
            boolean productionMode)
    {
        this.assetSource = assetSource;
        this.javaScriptSupport = javaScriptSupport;

        this.jqueryUIBase = jqueryUIBase;
        this.productionMode = productionMode;
    }
    
	public void transform(PlasticClass plasticClass,
			TransformationSupport support, MutableComponentModel model) {

		ImportJQueryUI annotation = plasticClass.getAnnotation(ImportJQueryUI.class);
		
		if(annotation == null)
			return;
		
		PlasticMethod setupRender = plasticClass.introduceMethod(TransformConstants.SETUP_RENDER_DESCRIPTION);
		
		final Flow<Asset> assetFlow = F.flow(annotation.value()).map(expandSimpleName).map(pathToAsset);
		
		setupRender.addAdvice(new MethodAdvice() {
			
			public void advise(MethodInvocation invocation) {
				assetFlow.each(importLibrary);

                invocation.proceed();
			}
		});

        model.addRenderPhase(SetupRender.class);
		
	}

    private final Mapper<String, String> expandSimpleName = new Mapper<String, String>()
    {
        public String map(String name)
        {
        	
        	if(InternalUtils.isBlank(name)) 
        		throw new TapestryException(TapestryJQueryExceptionMessages.importJQueryUiMissingValue(), null);
        		
        	
            final StringBuilder relativePath = new StringBuilder()
                .append(productionMode ? "/minified/" : "/")
                .append(name);

            return jqueryUIBase + relativePath.toString();
        }
    };

    private final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>()
    {
        public Asset map(String path)
        {
        	path = productionMode ? (path + ".min.js") : (path + ".js");
        	
        	Asset asset = assetSource.getExpandedAsset(path);
        	
        	if (!asset.getResource().exists())
        		throw new TapestryException(TapestryJQueryExceptionMessages.importJQueryUiFileMissing(path), null);
        		
        	return asset;

        }
    };

    private final Worker<Asset> importLibrary = new Worker<Asset>()
    {
        public void work(Asset value)
        {
            javaScriptSupport.importJavaScriptLibrary(value);
        }
    };
}
