package de.mondry.home.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import de.mondry.questionnaire.parse.FormDataParser;
import de.mondry.questionnaire.parse.XmlConverter;
import de.mondry.questionnaire.parse.beans.Question;
import de.mondry.questionnaire.parse.beans.Questionnaire;

@org.springframework.stereotype.Controller
public class QuestionnaireController {
    private static final Logger LOG = LoggerFactory.getLogger(QuestionnaireController.class);
    
    private XmlConverter xmlConverter;
    private FormDataParser formDataParser;
    
    private Questionnaire questionnaire;
    private List<Question> allQuestions;
    private String filename = "/xml/nosferatu.xml";
    
    public QuestionnaireController(FormDataParser formDataParser, XmlConverter xmlConverter) {
        this.formDataParser = formDataParser;
        this.xmlConverter = xmlConverter;
        try {
            LOG.info("constructed!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            
            InputStream is = loadStreamFromFile();
            questionnaire = xmlConverter.unmarshal(is);
            
            allQuestions = questionnaire.gatherAllQuestions();
            
        } catch (JAXBException | FileNotFoundException e) {
            LOG.error("", e);
            throw new RuntimeException(e);
        }
    }
    
    @RequestMapping("/questionnaire")
    public ModelAndView handleRequest() throws JAXBException {
        
        return new ModelAndView("questionnaire", "allQuestions", allQuestions);
        
    }
    
    /**
     * Json ist hier nicht wirklich sinnvoll, ev. gehts mit mvc besser, aber im moment weiss ich nicht wie...
     * 
     * @param formAsJson
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addQuestionnaire", method = RequestMethod.POST, consumes = "application/json")
    public String addQuestionnaire(@RequestBody String formAsJson) {
        
        LOG.info("json data: " + formAsJson);
        try {
            // get a fresh template object from xml
            Questionnaire questToPopulate = xmlConverter.unmarshal(loadStreamFromFile());
            
            questToPopulate = formDataParser.parseFormDataFromJson(formAsJson, questToPopulate);
            
            // do something with it...
            
        } catch (JAXBException | IOException e) {
            LOG.error("", e);
            throw new RuntimeException(e);
        }
        
        return "result";
    }
    
    private InputStream loadStreamFromFile() throws FileNotFoundException {
        InputStream is = this.getClass().getResourceAsStream(filename);
        if (is == null) {
            throw new FileNotFoundException("no file found at: " + filename);
        }
        return is;
    }
}
