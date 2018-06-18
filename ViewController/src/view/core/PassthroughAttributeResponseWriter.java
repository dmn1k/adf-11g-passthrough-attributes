package view.core;

import java.io.IOException;

import java.io.Serializable;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashSet;
import java.util.Map;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;

public class PassthroughAttributeResponseWriter extends ResponseWriterWrapper {
    // Optimierung da wir uns hier im Hot Rendering Path befinden und das Parsen und Allokieren von Strings reduzieren moechten
    private static final Map<String, PassthroughDefinition> PASSTHROUGH_DEFINITIONS = new ConcurrentHashMap<String, PassthroughDefinition>();
    
    private ResponseWriter underlying;
    
    public PassthroughAttributeResponseWriter(ResponseWriter underlying) {
        this.underlying = underlying; 
    }

    @Override
    public void startElement(String element, UIComponent uiComponent) throws IOException {
        underlying.startElement(element, uiComponent);
        
        if(uiComponent != null && uiComponent.getAttributes() != null){
            for(Map.Entry<String, Object> entry : uiComponent.getAttributes().entrySet()){
                PassthroughDefinition definition = PASSTHROUGH_DEFINITIONS.get(entry.getKey());
                if(definition == null){
                    definition = PassthroughDefinition.parse(entry.getKey());
                    PASSTHROUGH_DEFINITIONS.put(entry.getKey(), definition);
                }
                
                
                if(definition.getAppliesToElements().contains(element.toLowerCase())){
                    underlying.writeAttribute(definition.getAttributeName(), entry.getValue(), null);
                }
            }
        }
    }

    @Override
    protected ResponseWriter getWrapped() {
        return underlying;
    }
    
    private static class PassthroughDefinition implements Serializable {
        private static final long serialVersionUID = 1L;
        
        // Example: pass(input,textarea):aria-label
        private static final Pattern PASS_PATTERN = Pattern.compile("pass\\(([a-zA-Z0-9-,\\s]+)\\):([a-zA-Z0-9-\\s]+)");
        private static final PassthroughDefinition NULL_DEFINITION = new PassthroughDefinition(Collections.<String> emptySet(), "");
        
        private Set<String> appliesToElements;
        private String attributeName;
        
        public PassthroughDefinition(Set<String> appliesToElements, String attributeName){
            this.appliesToElements = appliesToElements;
            this.attributeName = attributeName;
        }
        
        public static PassthroughDefinition parse(String input){
            Matcher matcher = PASS_PATTERN.matcher(input);
            if(matcher.matches()){
                return new PassthroughDefinition(toNormalizedElements(matcher.group(1)), matcher.group(2).trim());
            }
            
            return NULL_DEFINITION;
        }
        
        private static final Set<String> toNormalizedElements(String elementsMatch){
            Set<String> result = new HashSet<String>();
            for(String match : elementsMatch.split(",")){
                result.add(match.toLowerCase().trim());
            }
            
            return result;
        }
        
        public Set<String> getAppliesToElements(){
            return appliesToElements;
        }
        
        public String getAttributeName(){
            return attributeName;
        }
    }
}
