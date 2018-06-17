package view;

import java.io.IOException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

public class PassthroughAttributeResponseWriter extends ResponseWriter {
    private ResponseWriter underlying;
    private Set<String> relevantPassthroughElements = new HashSet<String>();
    
    public PassthroughAttributeResponseWriter(ResponseWriter underlying, String... relevantPassthroughElements) {
        this.underlying = underlying; 
        for(String element : relevantPassthroughElements){
            this.relevantPassthroughElements.add(element.toLowerCase());
        }
    }

    public void startElement(String element, UIComponent uiComponent) throws IOException {
        underlying.startElement(element, uiComponent);
        
        if(this.relevantPassthroughElements.contains(element.toLowerCase())){
            for(Map.Entry<String, Object> entry : uiComponent.getAttributes().entrySet()){
                if(entry.getKey().startsWith("pass-")){
                    underlying.writeAttribute(entry.getKey().substring(5), entry.getValue(), null);
                }
            }
        }
    }
    
    public String getContentType() {
        return underlying.getContentType();
    }

    public String getCharacterEncoding() {
        return underlying.getCharacterEncoding();
    }

    public void flush() throws IOException {
        underlying.flush();
    }

    public void startDocument() throws IOException {
        underlying.startDocument();
    }

    public void endDocument() throws IOException {
        underlying.endDocument();
    }

    public void endElement(String element) throws IOException {
        underlying.endElement(element);
    }

    public void writeAttribute(String name, Object value, String property) throws IOException {
        underlying.writeAttribute(name, value, property);
    }

    public void writeURIAttribute(String name, Object value,
                                  String property) throws IOException {
        underlying.writeURIAttribute(name, value, property);
    }

    public void writeComment(Object comment) throws IOException {
        underlying.writeComment(comment);
    }

    public void writeText(Object text, String property) throws IOException {
        underlying.writeText(text, property);
    }

    public void writeText(char[] text, int off, int len) throws IOException {
        underlying.writeText(text, off, len);
    }

    public ResponseWriter cloneWithWriter(java.io.Writer writer) {
        return underlying.cloneWithWriter(writer);
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        underlying.write(cbuf, off, len);
    }

    public void close() throws IOException {
        underlying.close();
    }
}
