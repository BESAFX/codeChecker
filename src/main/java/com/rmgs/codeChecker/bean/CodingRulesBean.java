package com.rmgs.codeChecker.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rmgs.codeChecker.api.ApiClient;
import com.rmgs.codeChecker.dto.CodingRules;
import com.rmgs.codeChecker.dto.Facet;
import com.rmgs.codeChecker.dto.KeyCount;
import com.rmgs.codeChecker.dto.Rule;
import com.rmgs.codeChecker.dto.RuleDetails;

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
public class CodingRulesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5419689685746609263L;
	public static final Logger logger = LoggerFactory.getLogger(CodingRulesBean.class);
	private CodingRules codingRules;
	private List<Rule> filteredRules;
	private List<String> tags;
	FacesContext facesContext = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
	private Rule rule;
	private RuleDetails details = new RuleDetails();

	@Autowired
	ApiClient apiClient;

	public CodingRules getCodingRules() {
		return codingRules;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void setCodingRules(CodingRules codingRules) {
		this.codingRules = codingRules;
	}

	public RuleDetails getDetails() {
		return details;
	}

	public void setDetails(RuleDetails details) {
		this.details = details;
	}

	public List<Rule> getFilteredRules() {
		return filteredRules;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public void setFilteredRules(List<Rule> filteredRules) {
		this.filteredRules = filteredRules;
	}

	@PostConstruct
	public void init() {

		try {

			codingRules = apiClient.getRules();
			filteredRules = codingRules.getRules();
			tags = getTags(codingRules.getFacets());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<String> getTags(List<Facet> facets) {
		List<String> tags = new ArrayList<>();
		for (Facet facet : facets) {
			if (facet.getProperty().equalsIgnoreCase("tags")) {
				for (KeyCount keys : facet.getValues()) {
					tags.add(keys.getVal());
				}
				break;
			}
		}
		return tags;

	}

	public final void handleEvent(final AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		this.rule = context.getApplication().evaluateExpressionGet(context, "#{rule}", Rule.class);
	}

	public String initEditModal(Rule rule) throws IOException {
		session.setAttribute("editRuleModal", true);
		setDetails(apiClient.getRuleDetails(rule.getKey()));
		for (String tag : getDetails().getTags()) {
			if (!tags.contains(tag)) {
				tags.add(tag);
			}
		}
		this.rule = rule;
		session.setAttribute("originalEditedRuleObject", rule);
		session.setAttribute("originalEditedDetailsObject", getDetails());
		return null;
	}

	public String proccessModalAction() throws IOException {
		boolean editAction = (boolean) session.getAttribute("editRuleModal");
		if (editAction) {
			RuleDetails originalEditedDetails = (RuleDetails) session.getAttribute("originalEditedDetailsObject");
			originalEditedDetails.setMdNote(details.getMdNote());
			originalEditedDetails.setTags(details.getTags());

			boolean successfulyAdded = apiClient.updateRuleDetails(originalEditedDetails);

		}
		reload();
		return null;
	}

	private void reload() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}

	public void buttonAction(ActionEvent actionEvent) {
		addMessage("Welcome to Primefaces!!");
		codingRules.setRules(null);
		filteredRules = null;
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void buttonAction(ComponentSystemEvent event) {
		addMessage("Welcome to Primefaces!!");
		codingRules.setRules(null);
		filteredRules = null;
	}

	public void buttonAction() {
		addMessage("Welcome to Primefaces!!");
		codingRules.setRules(null);
		filteredRules = null;
	}

}
