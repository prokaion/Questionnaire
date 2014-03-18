package de.mondry.questionnaire.parse;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;

import de.mondry.questionnaire.parse.beans.Questionnaire;

public class XmlConverterTest {
    
    private Questionnaire questionnaire;
    private String filename = "src/test/resources/testxml/nosferatu.xml";
    private XmlConverter xmlConverter;
    
    @Before
    public void setup() throws JAXBException {
        questionnaire = BuildQuestionnaireObject.newQestionnaire();
        xmlConverter = new XmlConverter();
    }
    
    @Test
    public void testMarshal() {
        try {
            
            xmlConverter.marshalToFile(questionnaire, filename);
            
            File file = new File(filename);
            assertTrue(file.exists());
            
            InputStream is = new FileInputStream(file);
            
            Questionnaire unmarshalResult = xmlConverter.unmarshal(is);
            
            assertNotNull(unmarshalResult);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test(expected = FileNotFoundException.class)
    public void testFileNotFoundUnmarshal() throws FileNotFoundException, JAXBException {
        
        xmlConverter.unmarshal("noFileHere");
    }
    
}
