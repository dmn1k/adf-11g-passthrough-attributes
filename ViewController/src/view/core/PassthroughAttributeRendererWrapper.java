package view.core;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;

public class PassthroughAttributeRendererWrapper extends Renderer {
    private Renderer underlying;
    private String[] relevantPassthroughElements;
    
    public PassthroughAttributeRendererWrapper(Renderer underlying, String... relevantPassthroughElements){
        this.underlying = underlying;
        this.relevantPassthroughElements = relevantPassthroughElements;
    }

    @Override
    public void decode(FacesContext facesContext, UIComponent uiComponent) {
        underlying.decode(wrap(facesContext), uiComponent);
    }

    @Override
    public void encodeBegin(FacesContext facesContext,
                            UIComponent uiComponent) throws IOException {
        underlying.encodeBegin(wrap(facesContext), uiComponent);
    }

    @Override
    public void encodeChildren(FacesContext facesContext,
                               UIComponent uiComponent) throws IOException {
        underlying.encodeChildren(wrap(facesContext), uiComponent);
    }

    @Override
    public void encodeEnd(FacesContext facesContext,
                          UIComponent uiComponent) throws IOException {
        underlying.encodeEnd(wrap(facesContext), uiComponent);
    }

    @Override
    public String convertClientId(FacesContext facesContext, String id) {
        return underlying.convertClientId(facesContext, id);
    }

    @Override
    public Object getConvertedValue(FacesContext facesContext,
                                    UIComponent uiComponent,
                                    Object value) throws ConverterException {
        return underlying.getConvertedValue(facesContext, uiComponent, value);
    }

    @Override
    public boolean getRendersChildren() {
        return underlying.getRendersChildren();
    }
    
    private FacesContext wrap(FacesContext ctx){
        return new PassthroughAttributeFacesContext(ctx, relevantPassthroughElements);
    }
}
