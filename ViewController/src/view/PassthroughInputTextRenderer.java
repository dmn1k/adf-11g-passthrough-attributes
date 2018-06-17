package view;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.render.ClientComponent;

import oracle.adfinternal.view.faces.renderkit.rich.FormInputRenderer;
import oracle.adfinternal.view.faces.renderkit.rich.LabeledInputRenderer;

import oracle.adfinternal.view.faces.renderkit.rich.SimpleInputTextRenderer;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class PassthroughInputTextRenderer extends LabeledInputRenderer {
    public PassthroughInputTextRenderer() {
        super(RichInputText.TYPE);
    }

    @Override
    protected FormInputRenderer createFormInputRenderer(FacesBean.Type type) {
        return new PassthroughSimpleInputTextRenderer(type);
    }
    
    // Reverse engineerter Code aus InputTextRenderer (final und damit nicht überschreibbar): 
    
    @Override
    protected boolean isLeafRenderer(UIComponent component) {
        UIComponent contextComp = component.getFacet("context");
        return contextComp == null;
    }

    @Override
    protected void addClientPropertyLabel(ClientComponent client, String label) throws IOException {
    }

    @Override
    protected String getAccessKeyStyleClass() {
        return "af|inputText::access-key";
    }

    @Override
    protected String getRootStyleClass() {
        return "af|inputText";
    }

    @Override
    protected List<String> getRootStateStyleClasses(FacesContext context, RenderingContext arc, FacesBean bean, UIComponent component) {
        PassthroughSimpleInputTextRenderer tempRenderer = (PassthroughSimpleInputTextRenderer)this.getFormInputRenderer();
        return tempRenderer.getRootStateStyleClasses(context, arc, bean, component);
    }

    @Override
    protected String getDefaultLabelValign(UIComponent component, FacesBean bean) {
        return !((SimpleInputTextRenderer)this.getFormInputRenderer()).isTextArea(bean) && !this.isReadOnly(bean) ? null : "top";
    }

    @Override
    protected Boolean getWrapFieldCell(UIComponent component, FacesBean bean) {
        return this.isReadOnly(bean) ? Boolean.FALSE : super.getWrapFieldCell(component, bean);
    }

    @Override
    protected String getLabelFor(FacesContext context, RenderingContext arc, UIComponent component, FacesBean bean) {
        PassthroughSimpleInputTextRenderer tempRenderer = (PassthroughSimpleInputTextRenderer)this.getFormInputRenderer();

        return !tempRenderer.renderAsElementPublic(context, arc, bean) && !tempRenderer.isTextArea(bean) ? null : arc.getCurrentClientId();
    }
}
