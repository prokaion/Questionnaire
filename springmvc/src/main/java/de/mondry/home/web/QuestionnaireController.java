package de.mondry.home.web;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
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
import de.mondry.questionnaire.parse.beans.Answer;
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
            
            allQuestions = gatherAllQuestions(questionnaire.getQuestion());
        } catch (JAXBException | FileNotFoundException e) {
            LOG.error("", e);
            throw new RuntimeException(e);
        }
    }
    
    @RequestMapping("/questionnaire")
    public ModelAndView handleRequest() throws JAXBException {
        
        return new ModelAndView("questionnaire", "allQuestions", allQuestions);
        
    }
    
    @ResponseBody
    @RequestMapping(value = "/addQuestionnaire", method = RequestMethod.POST, consumes = "application/json")
    public String addQuestionnaire(@RequestBody String questionForm) {
        
        LOG.info("In here: " + questionForm);
        // model.addAttribute("question", questionnaire.getQuestion().get(0).getQuestion());
        return "result";
    }
    
    /**
     * Iterate over all questions and gather all subquestion into one big list.
     * 
     * @return
     */
    private List<Question> gatherAllQuestions(List<Question> questions) {
        List<Question> allQuestions = new ArrayList<>();
        
        for (Question question : questions) {
            // add a unique id to question
            // question.setId(UUID.randomUUID());
            
            allQuestions.add(question);
            List<Answer> answers = question.getAnswerlist().getAnswer();
            for (Answer answer : answers) {
                if (answer.getQuestion().size() > 0) {
                    // allQuestions.addAll(answer.getQuestion());
                    allQuestions.addAll(gatherAllQuestions(answer.getQuestion()));
                }
            }
        }
        return allQuestions;
    }
}
