package com.rmgs.codeChecker.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rmgs.codeChecker.api.ApiClient;
import com.rmgs.codeChecker.controllers.CommandController;
import com.rmgs.codeChecker.dto.ComponentDto;
import com.rmgs.codeChecker.dto.DashbaordDto;
import com.rmgs.codeChecker.dto.MeasureDto;
import com.rmgs.codeChecker.dto.Measures;
import com.rmgs.codeChecker.dto.Project;
import com.rmgs.codeChecker.dto.ProjectMeasure;
import com.rmgs.codeChecker.dto.UserDto;
import com.rmgs.codeChecker.git.GitController;
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
public class DashbaordBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3058431713747026813L;
	public static final Logger logger = LoggerFactory.getLogger(DashbaordBean.class);
	private DashbaordDto dashbaord ;
	private Measures measure;
	private List<ProjectMeasure> projectMeasure;
	private ProjectMeasure projectMeas = new ProjectMeasure();
	FacesContext facesContext = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
	@Value("${source.code.path}")
	private String sourceCodePath;
	
	@Value("${yasca.location}")
	private String yascaLocation;
	
	@Value("${analysis.files.location}")
	private String analysisFilesLocation;
	
	@Value("${licence.files.location}")
	private String licenceFilesLocation;
	
	
	@Value("${scane.code.location}")
	private String scanCodeLocation;
			
	GitController gitController;
	

	
	Project project =  new Project();
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

	@Autowired
	ApiClient apiClient;
	

	public DashbaordDto getDashbaord() {
		return dashbaord;
	}
	public void setDashbaord(DashbaordDto dashbaord) {
		this.dashbaord = dashbaord;
	}
	
	public ProjectMeasure getProjectMeas() {
		return projectMeas;
	}
	public void setProjectMeas(ProjectMeasure projectMeas) {
		this.projectMeas = projectMeas;
	}
	public Measures getMeasure() {
		return measure;
	}
	public void setMeasure(Measures measure) {
		this.measure = measure;
	}
	public List<ProjectMeasure> getProjectMeasure(){
		return this.projectMeasure;
	}
	
	public void setProjectMeasure(List<ProjectMeasure> projectMeasure) {
		this.projectMeasure = projectMeasure;
	}
	@PostConstruct
	public void init() {
		
		try{
			if(dashbaord==null)
				dashbaord=apiClient.getProjects(0);
			if(measure==null)
				measure = apiClient.getMeasures(getProjectKeys(dashbaord.getComponents()));
			if(projectMeasure==null)
				projectMeasure = getProjectMeasures();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private List<ProjectMeasure> getProjectMeasures(){
		List<ProjectMeasure> projectMeasures=  new ArrayList<>();
		Project project = null;
		for(ComponentDto componentDto:dashbaord.getComponents()){
			ProjectMeasure pm = new ProjectMeasure();
			pm.setProject(componentDto);
			for(MeasureDto measure:measure.getMeasures()){
				if(componentDto.getKey().equalsIgnoreCase(measure.getComponent())){
					if(measure.getMetric().equals("alert_status")){
						pm.setAlertStatus(measure.getValue().equals("OK")?"Passed":measure.getValue().equals("Warn")?"Warning":"Failed");
					}
					else if(measure.getMetric().equals("bugs")){
						pm.setBugs(measure.getValue());
					}
					else if(measure.getMetric().equals("code_smells")){
						pm.setCodeSmell(measure.getValue());
					}
					else if(measure.getMetric().equals("coverage")){
						pm.setCoverage(measure.getValue());
					}
					else if(measure.getMetric().equals("duplicated_lines_density")){
						pm.setDuplicatedLineDensity(measure.getValue());
					}
					else if(measure.getMetric().equals("ncloc")){
						pm.setNclocl(measure.getValue());
					}
					else if(measure.getMetric().equals("ncloc_language_distribution")){
						pm.setNclocLangDist(measure.getValue());
					}
					else if(measure.getMetric().equals("reliability_rating")){
						pm.setRelRating(measure.getValue());
					}
					else if(measure.getMetric().equals("security_rating")){
						pm.setSecRating(measure.getValue());
					}
					else if(measure.getMetric().equals("sqale_rating")){
						pm.setSqaleRating(measure.getValue());
					}
					else if(measure.getMetric().equals("vulnerabilities")){
						pm.setVulnerabilities(measure.getValue());
					}
					
					
				}
			}
			
			project = apiClient.getProject(pm.getProject().getKey());
			if(project != null){
				pm.getProject().setProjectPassword(project.getPassword());
				pm.getProject().setProjectUrl(project.getPath());
				pm.getProject().setProjectUserName(project.getUsername());
			}
			projectMeasures.add(pm);
		}
		return projectMeasures;
	}
	private String getProjectKeys(List<ComponentDto> list){
		String projectKeys="";
		for(ComponentDto component:list){
			projectKeys+=component.getKey()+",";
		}
		projectKeys.substring(0,projectKeys.length()-1);
		return projectKeys;
	}

	public final void handleEvent(final AjaxBehaviorEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();
		this.projectMeas = context.getApplication().evaluateExpressionGet(context, "#{pMeasure}", ProjectMeasure.class);
	}

	public void openModalForNewProject()
	{
		session.setAttribute("editProjectModal", false);
		project=new Project();
		
	}
	
	public void startAnalysYasca(ComponentDto project)throws Exception
	{
		gitController =  new GitController();
		gitController.cloneRepo(sourceCodePath + project.getName(), project.getProjectUrl(), project.getProjectUserName(), project.getProjectPassword());
		session.setAttribute("project", false);
		
		String projectURL = project.getProjectUrl();
		try{
		CommandController.excecuteCommand(new String[]{"cmd.exe", "/c","cd " + yascaLocation+" && yasca.bat "+ sourceCodePath + project.getName()});
		CommandController.excecuteCommand(new String[]{"cmd.exe", "/c","cd " + yascaLocation+" && copy report.html " +analysisFilesLocation+ project.getName()+"_"+ project.getId() + ".html"});
		CommandController.excecuteCommand(new String[]{"cmd.exe", "/c","cd " + scanCodeLocation +" && scancode "+" --format html " + sourceCodePath+ project.getName() +" "+licenceFilesLocation+ project.getName()+"_"+ project.getId() + ".html" });
		CommandController.excecuteCommand(new String[]{"cmd.exe", "/c","cd " + sourceCodePath + project.getName()+ " && mvn spring-boot:run -DskipTests " });
		CommandController.excecuteCommand(new String[]{"cmd.exe", "/c","cd " + sourceCodePath + project.getName()+ " && mvn sonar:sonar " });
		}catch(IOException e){
			
		}
		
	}
	
	public void startlicenceCheck(String projectName ,String projectURL)
	{
		session.setAttribute("project", false);
		project=new Project();
		projectURL = "E:\\Work\\cc\\sonarqube-6.7";
		try{
		CommandController.excecuteCommand(new String[]{"cmd.exe", "/c","cd " + scanCodeLocation +" && scancode "+ sourceCodePath +" //licences// " + projectName +" --only-findings"});
		}catch(IOException e){
			
		}
		
	}
	
	
	public String initEditModal(ProjectMeasure pMeasure) throws IOException {
		session.setAttribute("editProjectModal", true);

		
		this.projectMeas = pMeasure;
		
		
		project.setProjKey(projectMeas.getProject().getKey());
		project.setName(projectMeas.getProject().getName());
		project.setUsername(projectMeas.getProject().getProjectUserName());
		project.setPassword(projectMeas.getProject().getProjectPassword());
		project.setPath(projectMeas.getProject().getProjectUrl());
		session.setAttribute("originalEditedProjectObject", pMeasure);
		return null;
	}
	
	private void reload() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}
	
	public String proccessModalAction() throws IOException{
		boolean editAction = (boolean) session.getAttribute("editProjectModal");
		if(editAction)
		{
			//ProjectMeasure originalEditedProject = (ProjectMeasure) session.getAttribute("originalEditedProjectObject");
			//Project project =  new Project();
			/*project.setKey(projectMeas.getProject().getKey());
			project.setName(projectMeas.getProject().getName());
			project.setUsername(projectMeas.getProject().getProjectUserName());
			project.setPassword(projectMeas.getProject().getProjectPassword());
			project.setPath(projectMeas.getProject().getProjectUrl());*/
			
			boolean successfulyAdded = apiClient.updateProject(project);

		}else {
			
			project=apiClient.createProject(project);
			/*UserDto addedUser = null;
			String p=new BCryptPasswordEncoder().encode(user.getPassword());
			user.setPassword(p);
			
			user.setRoles(createRolesOjbect(user));
			addedUser=apiClient.addUser(user);
			users.add(addedUser);
			reload();*/
		}
		reload();
		return null;
	}

}
