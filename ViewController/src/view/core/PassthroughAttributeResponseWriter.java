package view.core;

import java.io.IOException;

import java.util.HashSet;
import java.util.Map;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

public class PassthroughAttributeResponseWriter extends ResponseWriter {
    // Example: pass(input,textarea):aria-label
    private static final Pattern PASS_PATTERN = Pattern.compile("pass\\(([a-zA-Z0-9-,\\s]+)\\):([a-zA-Z0-9-]+)");
    
    private ResponseWriter underlying;
    
    public PassthroughAttributeResponseWriter(ResponseWriter underlying) {
        this.underlying = underlying; 
    }

    public void startElement(String element, UIComponent uiComponent) throws IOException {
        underlying.startElement(element, uiComponent);
        
        if(uiComponent != null && uiComponent.getAttributes() != null){
            for(Map.Entry<String, Object> entry : uiComponent.getAttributes().entrySet()){
                Matcher matcher = PASS_PATTERN.matcher(entry.getKey());
                if(matcher.matches()){
                    Set<String> relevantElements = toNormalizedElements(matcher.group(1));
                    if(relevantElements.contains(element.toLowerCase())){
                        underlying.writeAttribute(matcher.group(2), entry.getValue(), null);
                    }
                }
            }
        }
    }
    
    private static final Set<String> toNormalizedElements(String elementsMatch){
        Set<String> result = new HashSet<String>();
        for(String match : elementsMatch.split(",")){
            result.add(match.toLowerCase().trim());
        }
        
        return result;
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
