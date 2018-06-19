package view.core;

import javax.faces.context.ResponseWriter;

import oracle.adfinternal.view.faces.renderkit.rich.RichRenderKit;

public class CustomRenderKit extends RichRenderKit {
    @Override
    protected ResponseWriter createDecoratedResponseWriter(ResponseWriter delegate) {
        ResponseWriter writer = super.createDecoratedResponseWriter(delegate);
        
        if(writer == null){
            return null;
        }
        
        if(writer instanceof PassthroughAttributeResponseWriter){
            return writer;
        }
        
        return new PassthroughAttributeResponseWriter(writer);
    }
}
