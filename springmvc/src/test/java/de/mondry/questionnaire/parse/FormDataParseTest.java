package de.mondry.questionnaire.parse;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;

import de.mondry.questionnaire.parse.beans.Questionnaire;

public class FormDataParseTest {
    
    private FormDataParser formDataParser;
    String jsonFormData = "[{\"name\":\"1\",\"value\":\"on\"},{\"name\":\"label.1\",\"value\":\"Ja\"},{\"name\":\"label.1\",\"value\":\"Nein\"},{\"name\":\"1.1\",\"value\":\"on\"},{\"name\":\"label.1.1\",\"value\":\"1\"},{\"name\":\"label.1.1\",\"value\":\"2\"},{\"name\":\"label.1.1\",\"value\":\"3\"},{\"name\":\"label.1.1\",\"value\":\"mehr\"},{\"name\":\"label.1.2\",\"value\":\"Kindergarten\"},{\"name\":\"1.2\",\"value\":\"on\"},{\"name\":\"label.1.2\",\"value\":\"Schule\"},{\"name\":\"label.1.2\",\"value\":\"Sportverein\"},{\"name\":\"label.2\",\"value\":\"Mitte\"},{\"name\":\"2\",\"value\":\"on\"},{\"name\":\"label.2\",\"value\":\"Pankow\"}]";
    
    // private String jsonFormData =
    // \"[{'name':'1','value':'on'},{'name':'label.1','value':'Ja'},{'name':'label.1','value':'Nein'},{'name':'1.1','value':'on'},{'name':'label.1.1','value':'1'},{'name':'label.1.1','value':'2'},{'name':'label.1.1','value':'3'},{'name':'label.1.1','value':'mehr'},{'name':'label.1.2','value':'Kindergarten'},{'name':'1.2','value':'on'},{'name':'label.1.2','value':'Schule'},{'name':'label.1.2','value':'Sportverein'},{'name':'label.2','value':'Mitte'},{'name':'2','value':'on'},{'name':'label.2','value':'Pankow'}]\";
    
    @Before
    public void setup() {
        formDataParser = new FormDataParser();
    }
    
    @Test
    public void test() {
        try {
            Questionnaire questionnaire = formDataParser.parseFormDataFromJson(jsonFormData, BuildQuestionnaireObject.newQestionnaire());
            assertNotNull(questionnaire);
            
            // System.out.println(allQuestions.toString());
            assertTrue(questionnaire.findAnswerByIdAndAnswerString("1", "Ja"));
            assertFalse(questionnaire.findAnswerByIdAndAnswerString("1", "Nein"));
            
            assertTrue(questionnaire.findAnswerByIdAndAnswerString("1.1", "1"));
            assertTrue(questionnaire.findAnswerByIdAndAnswerString("1.2", "Schule"));
            assertTrue(questionnaire.findAnswerByIdAndAnswerString("2", "Pankow"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNotNullAsParam() throws JsonParseException, JsonMappingException, IOException {
        
        formDataParser.parseFormDataFromJson(null, BuildQuestionnaireObject.newQestionnaire());
    }
    
}
