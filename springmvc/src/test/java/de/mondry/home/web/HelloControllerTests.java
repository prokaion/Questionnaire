package de.mondry.home.web;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class HelloControllerTests {
    
    @Test
    public void test() {
        HelloController helloController = new HelloController();
        try {
            ModelAndView modelAndView = helloController.handleRequest();
            
            Assert.assertEquals("hello", modelAndView.getViewName());
            Assert.assertNotNull(modelAndView.getModel());
            String nowValue = (String) modelAndView.getModel().get("now");
            Assert.assertNotNull(nowValue);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
}
