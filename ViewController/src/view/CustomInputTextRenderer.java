package view;

import oracle.adfinternal.view.faces.renderkit.rich.InputTextRenderer;

import view.core.PassthroughAttributeRendererWrapper;


public class CustomInputTextRenderer extends PassthroughAttributeRendererWrapper {
    public CustomInputTextRenderer() {
        super(new InputTextRenderer(), "input", "textarea");
    }
}
