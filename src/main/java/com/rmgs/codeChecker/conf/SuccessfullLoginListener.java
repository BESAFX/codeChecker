package com.rmgs.codeChecker.conf;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class SuccessfullLoginListener implements ApplicationListener<AuthenticationSuccessEvent> {
	
	//@Autowired ApiClient apiClient;
	@Autowired HttpSession httpSession;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		 String tempUnit ="C";
		 String timeZoneString="UTC";
		try {
			/* String username = ((UserDetails) event.getAuthentication().getPrincipal()).getUsername();
			 UsersDto user =apiClient.getUser(username);
			 UserDetailsDto userDetails = user.getUserDatils();
			 tempUnit=userDetails.getTemperatureUnit();
			 TimeZoneDto timezoneObject= userDetails.getTimeZone();
			 if(timezoneObject!=null){
				 timeZoneString=timezoneObject.getName();
			 }*/
		} catch (Exception e) {
			
		}
		httpSession.setAttribute("timeZone", timeZoneString);
		httpSession.setAttribute("tempUnit", tempUnit);
		
	}
	
	

}
