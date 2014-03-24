package de.mondry.questionnaire.parse;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mondry.questionnaire.parse.beans.Answer;
import de.mondry.questionnaire.parse.beans.Question;
import de.mondry.questionnaire.parse.beans.Questionnaire;

/**
 * Converts form data in json to a questionnaire. Probably this will get messy!
 * 
 */
public class FormDataParser {
    private static final Logger LOG = LoggerFactory.getLogger(FormDataParser.class);
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    public FormDataParser() {
    }
    
    public Questionnaire parseFormDataFromJson(String jsonFormData, Questionnaire questionnaire) throws JsonParseException, JsonMappingException, IOException {
        
        if (jsonFormData == null) {
            throw new IllegalArgumentException("json is null! " + jsonFormData);
        }
        
        List<FormTuple> formTuples = objectMapper.readValue(jsonFormData, objectMapper.getTypeFactory().constructCollectionType(List.class, FormTuple.class));
        
        LOG.info(formTuples.toString());
        
        return parseFromFormTuples(formTuples, questionnaire);
        
    }
    
    private Questionnaire parseFromFormTuples(List<FormTuple> formTuples, Questionnaire questionnaire) {
        Iterator<FormTuple> it = formTuples.listIterator();
        
        while (it.hasNext()) {
            FormTuple formTuple = it.next();
            if (!formTuple.getName().contains("label")) {
                // its is a value which was set by user, there must be the next tuple with the label and questionId!
                FormTuple labelTuple = it.next();
                // find the corresponding answer in questionnaireObject and set its value.
                
                setCorrespondingAnswer(formTuple, labelTuple, questionnaire.gatherAllQuestions());
            }
            
        }
        return questionnaire;
    }
    
    /**
     * if called this answer is considered as set by user so it is set to true. Later we may introduce text answer also..
     * 
     * @param formTuple
     * @param labelTuple
     * @param allQuestions
     */
    private void setCorrespondingAnswer(FormTuple formTuple, FormTuple labelTuple, List<Question> allQuestions) {
        // performance should be no issue, so we run through the whole Questionnaire each time to find the answer and set it.
        String answerString = labelTuple.value;
        String questionId = formTuple.name;
        
        for (Question question : allQuestions) {
            if (question.getId().equals(questionId)) {
                List<Answer> answers = question.getAnswerlist().getAnswer();
                for (Answer answer : answers) {
                    if (answerString.equals(answer.getAnswerString())) {
                        answer.setChecked(true);
                    }
                }
            }
        }
        
    }
    
    public static class FormTuple {
        private String name;
        private String value;
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getValue() {
            return value;
        }
        
        public void setValue(String value) {
            this.value = value;
        }
        
        @Override
        public String toString() {
            return "FormTuple [name=" + name + ", value=" + value + "]";
        }
        
    }
    
}
