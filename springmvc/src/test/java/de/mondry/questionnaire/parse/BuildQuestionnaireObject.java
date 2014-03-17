package de.mondry.questionnaire.parse;

import de.mondry.questionnaire.parse.beans.Answer;
import de.mondry.questionnaire.parse.beans.Answerlist;
import de.mondry.questionnaire.parse.beans.Question;
import de.mondry.questionnaire.parse.beans.Questionnaire;

public class BuildQuestionnaireObject {
    
    public static Questionnaire newQestionnaire() {
        Questionnaire questionnaire = new Questionnaire();
        
        Question question = new Question();
        question.setQuestion("Haben Sie Kinder?");
        Answerlist answerlist = new Answerlist();
        answerlist.setSingleChoice(true);
        
        Answer answerJa = new Answer();
        answerJa.setAnswerString("Ja");
        Question questKids = generateQuestKids();
        Question questLocationOfKids = generateLocationOfKids();
        
        answerJa.getQuestion().add(questKids);
        answerJa.getQuestion().add(questLocationOfKids);
        
        Answer answerNein = new Answer();
        answerNein.setAnswerString("Nein");
        
        answerlist.getAnswer().add(answerJa);
        answerlist.getAnswer().add(answerNein);
        
        question.setAnswerlist(answerlist);
        questionnaire.getQuestion().add(question);
        
        // independent question 2
        Question quest2 = new Question();
        quest2.setQuestion("In welchem Stadtteil wohnen Sie?");
        
        Answerlist answerlist2 = new Answerlist();
        answerlist2.setSingleChoice(true);
        
        Answer ans1 = new Answer();
        ans1.setAnswerString("Mitte");
        Answer ans2 = new Answer();
        ans2.setAnswerString("Pankow");
        
        answerlist2.getAnswer().add(ans1);
        answerlist2.getAnswer().add(ans2);
        quest2.setAnswerlist(answerlist2);
        
        questionnaire.getQuestion().add(quest2);
        return questionnaire;
        
    }
    
    private static Question generateLocationOfKids() {
        Question q = new Question();
        q.setQuestion("Welche Einrichtungen besuchen Ihre Kinder (mehrere Antworten sind hier möglich)?");
        Answerlist list = new Answerlist();
        list.setSingleChoice(false);
        
        Answer a1 = new Answer();
        a1.setAnswerString("Kindergarten");
        Answer a2 = new Answer();
        a2.setAnswerString("Schule");
        Answer a3 = new Answer();
        a3.setAnswerString("Sportverein");
        
        list.getAnswer().add(a1);
        list.getAnswer().add(a2);
        list.getAnswer().add(a3);
        
        q.setAnswerlist(list);
        return q;
    }
    
    private static Question generateQuestKids() {
        Question questKids = new Question();
        questKids.setQuestion("Wie viele Kinder haben Sie?");
        Answerlist answKids = new Answerlist();
        answKids.setSingleChoice(true);
        
        Answer a1 = new Answer();
        a1.setAnswerString("1");
        Answer a2 = new Answer();
        a2.setAnswerString("2");
        Answer a3 = new Answer();
        a3.setAnswerString("3");
        Answer a4 = new Answer();
        a4.setAnswerString("mehr");
        
        answKids.getAnswer().add(a1);
        answKids.getAnswer().add(a2);
        answKids.getAnswer().add(a3);
        answKids.getAnswer().add(a4);
        
        questKids.setAnswerlist(answKids);
        return questKids;
    }
    
}
