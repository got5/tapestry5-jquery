package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Flow;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.func.Worker;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.http.services.Request;
import org.apache.tapestry5.services.TransformConstants;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;

/**
 * 
 * @deprecated
 *
 */
public class ImportJQueryUIWorker implements ComponentClassTransformWorker2
{
	
	private final AssetSource assetSource;

    private final JavaScriptSupport javaScriptSupport;
    
    private final String jqueryUIBase;

    private final String themePath;
    
    private final Boolean mouseWheelIncluded;
    
    public ImportJQueryUIWorker(AssetSource assetSource,

            JavaScriptSupport javaScriptSupport, Request request, 
            
            @Symbol(JQuerySymbolConstants.JQUERY_UI_PATH)
            String jqueryUIBase,
            
    		@Symbol(JQuerySymbolConstants.JQUERY_UI_DEFAULT_THEME)
    		String themePath, 
    		@Symbol(JQuerySymbolConstants.ADD_MOUSEWHEEL_EVENT) boolean mouseWheelIncluded)
    {
        this.assetSource = assetSource;
        this.javaScriptSupport = javaScriptSupport;
                
        this.jqueryUIBase = jqueryUIBase;
        this.themePath = themePath;
        this.mouseWheelIncluded = mouseWheelIncluded;
    }
    
	public void transform(PlasticClass plasticClass,
			TransformationSupport support, MutableComponentModel model) {

		final ImportJQueryUI annotation = plasticClass.getAnnotation(ImportJQueryUI.class);
		
		PlasticMethod setupRender = plasticClass.introduceMethod(TransformConstants.SETUP_RENDER_DESCRIPTION);
		
		if(annotation != null){
			
			if(annotation.value().length > 0){
				
				final Flow<Asset> assetFlow = F.flow(annotation.value()).map(expandSimpleName).map(pathToAsset);
				
				setupRender.addAdvice(new MethodAdvice() {
					
					public void advise(MethodInvocation invocation) {
						
						assetFlow.each(importLibrary);

		                invocation.proceed();
					}
				});
			}
		}
		
		if(model.isPage()){
			setupRender.addAdvice(new MethodAdvice() {
				
				public void advise(MethodInvocation invocation) {
					
					String path = (annotation!=null && InternalUtils.isNonBlank(annotation.theme())) ? annotation.theme() : themePath;
					
					javaScriptSupport.importStylesheet(assetSource.getExpandedAsset(path));
					
					if(mouseWheelIncluded)
						javaScriptSupport.require("vendor/jquerymousewheel");
					invocation.proceed();
					
				}
			});
		}
		
		model.addRenderPhase(SetupRender.class);
		
	}
	
	private final Mapper<String, String> expandSimpleName = new Mapper<String, String>()
    {
        public String map(String name)
        {
        	return jqueryUIBase + String.format("/%s.js", name);
        }
    };

    private final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>()
    {
        public Asset map(String path)
        {
        	Asset asset = assetSource.getExpandedAsset(path);
        	
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
