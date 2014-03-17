package de.mondry.home.web;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.stereotype.Controller
public class HelloController {
    private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);
    
    @RequestMapping("/hello")
    public ModelAndView handleRequest(/* HttpServletRequest arg0, HttpServletResponse arg1 */) {
        
        String now = (new Date()).toString();
        LOG.info("Returning hello view with " + now);
        
        return new ModelAndView("hello", "now", now);
        
    }
    
}
