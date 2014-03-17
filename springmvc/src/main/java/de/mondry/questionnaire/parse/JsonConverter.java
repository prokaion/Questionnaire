package de.mondry.questionnaire.parse;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import de.mondry.questionnaire.parse.beans.Questionnaire;

/**
 * 
 * @author daniel
 * 
 */
public class JsonConverter {
    
    private ObjectMapper objectMapper;
    
    public JsonConverter() {
        objectMapper = new ObjectMapper();
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
        objectMapper.getDeserializationConfig().withAnnotationIntrospector(introspector);
        objectMapper.getSerializationConfig().withAnnotationIntrospector(introspector);
    }
    
    public String convert2Json(Questionnaire questionnaire) throws JsonGenerationException, JsonMappingException, IOException {
        String json = objectMapper.writeValueAsString(questionnaire);
        return json;
    }
    
    public Questionnaire convert2Qestionnaire(String jsonString) throws JsonParseException, JsonMappingException, IOException {
        
        return objectMapper.readValue(jsonString, Questionnaire.class);
    }
    
}
