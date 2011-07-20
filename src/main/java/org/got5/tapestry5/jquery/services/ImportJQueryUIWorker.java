package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Flow;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.func.Worker;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.ComponentMethodAdvice;
import org.apache.tapestry5.services.ComponentMethodInvocation;
import org.apache.tapestry5.services.TransformConstants;
import org.apache.tapestry5.services.TransformMethod;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;


public class ImportJQueryUIWorker implements ComponentClassTransformWorker
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

    private final Mapper<String, String> expandSimpleName = new Mapper<String, String>()
    {
        public String map(String name)
        {
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

            if (productionMode)
            {
                String minPath = path + ".min.js";

                Asset asset = assetSource.getExpandedAsset(minPath);

                if (asset.getResource().exists())
                    return asset;
            }

            return assetSource.getExpandedAsset(path + ".js");
        }
    };

    private final Worker<Asset> importLibrary = new Worker<Asset>()
    {
        public void work(Asset value)
        {
            javaScriptSupport.importJavaScriptLibrary(value);
        }
    };

    public void transform(ClassTransformation transformation, MutableComponentModel model)
    {
        ImportJQueryUI annotation = transformation.getAnnotation(ImportJQueryUI.class);

        if (annotation == null)
            return;

        Flow<Asset> assetFlow = F.flow(annotation.value()).map(expandSimpleName).map(pathToAsset);

        addAdvicetoBeginRender(transformation, assetFlow);

        model.addRenderPhase(BeginRender.class);
    }

    private void addAdvicetoBeginRender(ClassTransformation transformation, Flow<Asset> assetFlow)
    {
        TransformMethod method = transformation.getOrCreateMethod(TransformConstants.BEGIN_RENDER_SIGNATURE);

        method.addAdvice(createBeginRenderAdvice(assetFlow));
    }

    private ComponentMethodAdvice createBeginRenderAdvice(final Flow<Asset> assetFlow)
    {

        return new ComponentMethodAdvice()
        {
            public void advise(ComponentMethodInvocation invocation)
            {
                assetFlow.each(importLibrary);

                invocation.proceed();
            }
        };
    }

}
