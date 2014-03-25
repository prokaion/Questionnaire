package de.mondry.questionnaire.parse;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import de.mondry.questionnaire.parse.beans.Questionnaire;

/**
 * 
 * @author daniel
 * 
 */
public class XmlConverter {
    
    private JAXBContext jaxbContext;
    
    public XmlConverter() throws JAXBException {
        jaxbContext = JAXBContext.newInstance("de.mondry.questionnaire.parse.beans");
    }
    
    public void marshalToFile(Questionnaire questionnaire, String filename) throws JAXBException, FileNotFoundException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        
        PrintWriter out = new PrintWriter(filename);
        
        marshaller.marshal(questionnaire, out);
        out.close();
    }
    
    public Questionnaire unmarshal(String filename) throws JAXBException, FileNotFoundException {
        InputStream is = this.getClass().getResourceAsStream(filename);
        if (is == null) {
            throw new FileNotFoundException("no file found at: " + filename);
        }
        return doUnmarshal(is);
    }
    
    public Questionnaire unmarshal(InputStream is) throws JAXBException {
        return doUnmarshal(is);
    }
    
    private Questionnaire doUnmarshal(InputStream is) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<Questionnaire> root = unmarshaller.unmarshal(new StreamSource(is), Questionnaire.class);
        return root.getValue();
    }
    
}
