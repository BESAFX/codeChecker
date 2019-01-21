package com.rmgs.codeChecker.conf;

import com.sun.faces.config.FacesInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConfigureJSF {

    @Bean
    public ServletRegistrationBean facesServletRegistration() {

        ServletRegistrationBean servletRegistrationBean = new JsfServletRegistrationBean();

        return servletRegistrationBean;
    }

    public class JsfServletRegistrationBean extends ServletRegistrationBean {

        public JsfServletRegistrationBean() {
            super();
        }

        @Override
        public void onStartup(ServletContext servletContext)
                throws ServletException {
            FacesInitializer facesInitializer = new FacesInitializer();
//            servletContext.setInitParameter("primefaces.THEME", "none");
            Set<Class<?>> clazz = new HashSet<Class<?>>();
            clazz.add(ConfigureJSF.class);
            facesInitializer.onStartup(clazz, servletContext);
        }
    }
}
