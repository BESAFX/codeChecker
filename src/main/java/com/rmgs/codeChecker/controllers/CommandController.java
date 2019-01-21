package com.rmgs.codeChecker.controllers;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandController {

    public static void excecuteCommand(String[] command) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        ProcessBuilder builder = new ProcessBuilder(command);
        //builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) {
                break;
            }
            System.out.println(line);
        }
    }
}
