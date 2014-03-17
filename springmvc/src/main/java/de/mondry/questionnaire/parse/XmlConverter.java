package de.mondry.questionnaire.parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import de.mondry.questionnaire.parse.beans.Questionnaire;

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
    
    public Questionnaire unmarshal(String filename) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        
        JAXBElement<Questionnaire> root = unmarshaller.unmarshal(new StreamSource(new File(filename)), Questionnaire.class);
        return root.getValue();
    }
    
}
