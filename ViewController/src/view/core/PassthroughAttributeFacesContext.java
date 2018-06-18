package view.core;

import java.util.Iterator;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;

public class PassthroughAttributeFacesContext extends FacesContext {
    private FacesContext underlying;
    
    public PassthroughAttributeFacesContext(FacesContext underlying) {
        this.underlying = underlying;
    }
    
    public ResponseWriter getResponseWriter() {
        ResponseWriter responseWriter = underlying.getResponseWriter();
        if(responseWriter instanceof PassthroughAttributeResponseWriter){
            return responseWriter;
        }
        
        return new PassthroughAttributeResponseWriter(responseWriter);
    }

    public Application getApplication() {
        return underlying.getApplication();
    }

    public Iterator<String> getClientIdsWithMessages() {
        return underlying.getClientIdsWithMessages();
    }

    public ExternalContext getExternalContext() {
        return underlying.getExternalContext();
    }

    public FacesMessage.Severity getMaximumSeverity() {
        return underlying.getMaximumSeverity();
    }

    public Iterator<FacesMessage> getMessages() {
        return underlying.getMessages();
    }

    public Iterator<FacesMessage> getMessages(String key) {
        return underlying.getMessages(key);
    }

    public RenderKit getRenderKit() {
        return underlying.getRenderKit();
    }

    public boolean getRenderResponse() {
        return underlying.getRenderResponse();
    }

    public boolean getResponseComplete() {
        return underlying.getResponseComplete();
    }

    public ResponseStream getResponseStream() {
        return underlying.getResponseStream();
    }

    public void setResponseStream(ResponseStream responseStream) {
        underlying.setResponseStream(responseStream);
    }

    public void setResponseWriter(ResponseWriter responseWriter) {
        underlying.setResponseWriter(responseWriter);
    }

    public UIViewRoot getViewRoot() {
        return underlying.getViewRoot();
    }

    public void setViewRoot(UIViewRoot uIViewRoot) {
        underlying.setViewRoot(uIViewRoot);
    }

    public void addMessage(String key, FacesMessage facesMessage) {
        underlying.addMessage(key, facesMessage);
    }

    public void release() {
        underlying.release();
    }

    public void renderResponse() {
        underlying.renderResponse();
    }

    public void responseComplete() {
        underlying.responseComplete();
    }
}
