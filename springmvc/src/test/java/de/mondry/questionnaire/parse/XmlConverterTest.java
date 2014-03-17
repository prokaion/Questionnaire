package de.mondry.questionnaire.parse;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.mondry.questionnaire.parse.beans.Questionnaire;

public class XmlConverterTest {
    
    private Questionnaire questionnaire;
    private String filename = "nosferatu.xml";
    
    @Before
    public void setup() {
        questionnaire = BuildQuestionnaireObject.newQestionnaire();
    }
    
    @Test
    public void testMarshal() {
        try {
            XmlConverter xmlConverter = new XmlConverter();
            
            xmlConverter.marshalToFile(questionnaire, filename);
            
            File file = new File(filename);
            assertTrue(file.exists());
            
            Questionnaire unmarshalResult = xmlConverter.unmarshal(filename);
            
            assertNotNull(unmarshalResult);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
}
