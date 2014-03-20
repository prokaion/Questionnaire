package de.mondry.home.web;

import java.io.FileNotFoundException;
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

import de.mondry.questionnaire.parse.XmlConverter;
import de.mondry.questionnaire.parse.beans.Question;
import de.mondry.questionnaire.parse.beans.Questionnaire;

@org.springframework.stereotype.Controller
public class QuestionnaireController {
    private static final Logger LOG = LoggerFactory.getLogger(QuestionnaireController.class);
    
    private XmlConverter xmlConverter;
    
    private Questionnaire questionnaire;
    private List<Question> allQuestions;
    private String filename = "/xml/nosferatu.xml";
    
    public QuestionnaireController() {
        try {
            xmlConverter = new XmlConverter();
            
            InputStream is = this.getClass().getResourceAsStream(filename);
            if (is == null) {
                throw new FileNotFoundException("no file found at: " + filename);
            }
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
        
        LOG.info("In here: " + formAsJson);
        // model.addAttribute("question", questionnaire.getQuestion().get(0).getQuestion());
        return "result";
    }
}
