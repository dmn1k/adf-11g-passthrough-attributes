package view.core;

import javax.faces.render.Renderer;

import oracle.adfinternal.view.faces.renderkit.rich.RichRenderKit;

public class CustomRenderKit extends RichRenderKit {
    @Override
    public Renderer getRenderer(String family, String type) {
        Renderer renderer = super.getRenderer(family, type);
        if(renderer == null){
            return null;
        }
        
        if(renderer instanceof PassthroughAttributeRendererWrapper){
            return renderer;
        }
        
        return new PassthroughAttributeRendererWrapper(renderer);
    }
}
