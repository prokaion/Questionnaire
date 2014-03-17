package de.mondry.home.web;

import java.util.HashMap;
import java.util.Map;

/**
 * Do I need this?
 * 
 * @author daniel
 * 
 */
public class QuestionForm {
    
    private Map<String, String> questionMap = new HashMap<>();
    
    public Map<String, String> getQuestionMap() {
        return questionMap;
    }
    
    public void setQuestionMap(Map<String, String> questionMap) {
        this.questionMap = questionMap;
    }
}
