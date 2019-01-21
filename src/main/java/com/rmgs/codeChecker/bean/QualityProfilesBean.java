package com.rmgs.codeChecker.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rmgs.codeChecker.api.ApiClient;
import com.rmgs.codeChecker.dto.PLanguages;
import com.rmgs.codeChecker.dto.Profile;
import com.rmgs.codeChecker.dto.QualityProfiles;
import com.rmgs.codeChecker.dto.Rule;

/**
 * 
 * @author rehab.sayed
 *
 */
@ManagedBean
@RequestScoped
// @ViewScoped
@Component
@Scope(value = "request")
public class QualityProfilesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5419689685746609263L;
	public static final Logger logger = LoggerFactory.getLogger(QualityProfilesBean.class);
	private QualityProfiles qualityProfiles;
	private List<Profile> filteredProfiles;
	private Profile profile = new Profile();
	private Profile deletedProfile;
	private PLanguages currentLanguage = new PLanguages();

	public PLanguages getCurrentLanguage() {
		return currentLanguage;
	}

	public void setCurrentLanguage(PLanguages currentLanguage) {
		this.currentLanguage = currentLanguage;
	}

	public List<PLanguages> getLanguageOptions() {
		return languageOptions;
	}

	public void setLanguageOptions(List<PLanguages> languageOptions) {
		this.languageOptions = languageOptions;
	}

	private List<PLanguages> languageOptions;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Profile getDeletedProfile() {
		return deletedProfile;
	}

	public void setDeletedProfile(Profile deletedProfile) {
		this.deletedProfile = deletedProfile;
	}

	FacesContext facesContext = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

	@Autowired
	ApiClient apiClient;

	public QualityProfiles getQualityProfiles() {
		return qualityProfiles;
	}

	public void setQualityProfiles(QualityProfiles qualityProfiles) {
		this.qualityProfiles = qualityProfiles;
	}

	public List<Profile> getFilteredProfiles() {
		return filteredProfiles;
	}

	public void setFilteredProfiles(List<Profile> filteredProfiles) {
		this.filteredProfiles = filteredProfiles;
	}

	@PostConstruct
	public void init() {

		try {

			qualityProfiles = apiClient.getQualityProfiles();
			filteredProfiles = qualityProfiles.getProfiles();
			if(session.getAttribute("originalEditedProfileObject")!=null){
				profile=(Profile) session.getAttribute("originalEditedProfileObject");
			}
			languageOptions = PLanguages.getAllLanguage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String initEditModal(Profile profile) throws IOException {
		session.setAttribute("editProfileModal", true);

		profile.setActiveRules(apiClient.getProfileRules(true, profile.getKey()));
		profile.setInActiveRules(apiClient.getProfileRules(false, profile.getKey()));
		this.profile = profile;
		session.setAttribute("originalEditedProfileObject", profile);
		return null;
	}

	public String initCopyModal(Profile profile) throws IOException {
		session.setAttribute("copyModal", true);
		this.profile = profile;
		session.setAttribute("originalCopiedProfileObject", profile);
		return null;
	}

	public void openModalForNewProfile() {
		session.setAttribute("createProfileModal", false);
		this.profile = new Profile();
	}

	public final void handleEvent(final AjaxBehaviorEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();
		this.profile = context.getApplication().evaluateExpressionGet(context, "#{profile}", Profile.class);
	}

	/*
	 * 
	 * Delete
	 */
	public String deleteAction(Profile profile) throws IOException {
		session.setAttribute("deletedProfile_object", profile);
		deletedProfile = profile;
		return null;
	}

	public String confirmDelete() throws IOException {
		deletedProfile = (Profile) session.getAttribute("deletedProfile_object");
		qualityProfiles.getProfiles().remove(deletedProfile);
		apiClient.deleteProfile(deletedProfile.getKey());
		reload();
		return null;
	}

	private void reload() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}

	public void proccessModalAction() throws IOException {
		boolean editAction = false;
		boolean copyAction = false;
		if (session.getAttribute("editProfileModal") != null) {
			editAction = (boolean) session.getAttribute("editProfileModal");
		}

		if (session.getAttribute("copyModal") != null) {
			copyAction = (boolean) session.getAttribute("copyModal");
		}

		if (editAction) {
			Profile originalEditedProfile = (Profile) session.getAttribute("originalEditedProfileObject");

			originalEditedProfile.setName(profile.getName());
			originalEditedProfile.setActiveR(profile.getActiveR());
			if (profile.getActiveRules() != null) {
				for (Rule rule : profile.getActiveRules().getRules()) {
					if (profile.getActiveR() != null && profile.getActiveR().contains(rule.getKey())) {
						profile.getActiveMap().put(rule.getKey(), rule.getSeverity());
					}
				}
			}
			if (profile.getInActiveRules() != null) {
				for (Rule rule : profile.getInActiveRules().getRules()) {
					if (profile.getInactiveR() != null && profile.getInactiveR().contains(rule.getKey())) {
						profile.getInactiveMap().put(rule.getKey(), rule.getSeverity());
					}
				}
			}
			originalEditedProfile.setInactiveR(profile.getInactiveR());
			apiClient.updateProfile(originalEditedProfile);
		} else if (copyAction) {
			Profile originalCopiedProfile = (Profile) session.getAttribute("originalCopiedPrSofileObject");
			originalCopiedProfile.setName(profile.getName());
			apiClient.copyProfile(originalCopiedProfile);
		} else {
			this.profile.setName(profile.getName());
			this.profile.setLanguage(currentLanguage.getLangName());
			apiClient.createProfile(this.profile);
		}
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "What we do in life",
				"Echoes in eternity.");

		PrimeFaces.current().dialog().showMessageDynamic(message);
		reload();
	}

}
