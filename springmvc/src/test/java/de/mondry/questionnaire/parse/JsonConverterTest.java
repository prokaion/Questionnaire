package de.mondry.questionnaire.parse;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.mondry.questionnaire.parse.beans.Questionnaire;

public class JsonConverterTest {
    
    private JsonConverter jsonConverter;
    
    @Before
    public void setup() {
        jsonConverter = new JsonConverter();
    }
    
    @Test
    public void testBuildQuestionnaire2JsonAndBack2JavaBean() {
        Questionnaire questionnaire = BuildQuestionnaireObject.newQestionnaire();
        
        String jsonString;
        try {
            jsonString = jsonConverter.convert2Json(questionnaire);
            System.out.println(jsonString);
            assertNotNull(jsonString);
            
            Questionnaire buildedFromJson = jsonConverter.convert2Qestionnaire(jsonString);
            
            assertNotNull(buildedFromJson);
        } catch (IOException e) {
            
            e.printStackTrace();
            fail("Baad!");
        }
    }
    
}
