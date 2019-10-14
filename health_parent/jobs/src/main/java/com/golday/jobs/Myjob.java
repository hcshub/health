package com.golday.jobs;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class Myjob {
    public void repetition(){
        System.out.println("进入了方法...");
        /*try {
            response.sendRedirect("http://localhost:8088/clear");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
