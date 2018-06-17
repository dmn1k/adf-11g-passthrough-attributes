package view;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.render.ClientComponent;

import oracle.adfinternal.view.faces.renderkit.rich.SimpleInputTextRenderer;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class PassthroughSimpleInputTextRenderer extends SimpleInputTextRenderer {
    public PassthroughSimpleInputTextRenderer(FacesBean.Type type) {
        super(type);
    }

    public PassthroughSimpleInputTextRenderer() {
        super();
    }

    @Override
    protected void renderElementContent(FacesContext context, RenderingContext rc, UIComponent component, ClientComponent client, FacesBean bean) throws IOException {
        FacesContext wrappedContext = new PassthroughAttributeFacesContext(context, "input", "textarea");
        super.renderElementContent(wrappedContext, rc, component, client, bean);
    }
    
    
    // Methoden auf public setzen um sie in PassthroughInputTextRenderer nutzen zu können:
    @Override
    public List<String> getRootStateStyleClasses(FacesContext context, RenderingContext rc, FacesBean bean, UIComponent uiComponent) { 
        return super.getRootStateStyleClasses(context, rc, bean, uiComponent);
    }
    
    public final boolean renderAsElementPublic(FacesContext context, RenderingContext rc, FacesBean bean) { 
        return super.renderAsElement(context, rc, bean);
    }

         
}
