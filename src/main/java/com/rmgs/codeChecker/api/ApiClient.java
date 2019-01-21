package com.rmgs.codeChecker.api;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.rmgs.codeChecker.dto.CodingRules;
import com.rmgs.codeChecker.dto.DashbaordDto;
import com.rmgs.codeChecker.dto.IssuesDto;
import com.rmgs.codeChecker.dto.Measures;
import com.rmgs.codeChecker.dto.PrivilegeDto;
import com.rmgs.codeChecker.dto.Profile;
import com.rmgs.codeChecker.dto.ProfileCopy;
import com.rmgs.codeChecker.dto.Project;
import com.rmgs.codeChecker.dto.ProjectMeasure;
import com.rmgs.codeChecker.dto.QualityProfiles;
import com.rmgs.codeChecker.dto.RoleDto;
import com.rmgs.codeChecker.dto.RuleDetails;
import com.rmgs.codeChecker.dto.UserDto;

@RestController
@RequestMapping(value = "/api")
public class ApiClient {

	@Value("${api.http.host}")
	private String apiUrl;

	public DashbaordDto getProjects(long favourite) {
		ResponseEntity<DashbaordDto> responseEntity = null;
		DashbaordDto dashbaordDto = null;
		String path = "/api/dashbaord/search_projects/0";
		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = apiUrl + path;
			responseEntity = restTemplate.getForEntity(url, DashbaordDto.class);
			dashbaordDto = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dashbaordDto;

	}

	public Measures getMeasures(String projectKeys) {
		ResponseEntity<Measures> responseEntity = null;
		Measures measures = null;
		String path = "/api/dashbaord/measures/search?projectKeys=" + projectKeys;

		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = apiUrl + path;
			responseEntity = restTemplate.getForEntity(url, Measures.class);
			measures = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return measures;

	}

	public CodingRules getRules() {
		ResponseEntity<CodingRules> responseEntity = null;
		CodingRules codingRules = null;
		String path = "/api/rules/coding_rules";
		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = apiUrl + path;
			responseEntity = restTemplate.getForEntity(url, CodingRules.class);
			codingRules = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codingRules;

	}

	public CodingRules getProfileRules(boolean activation, String qProfile) {
		ResponseEntity<CodingRules> responseEntity = null;
		CodingRules codingRules = null;
		String path = "/api/rules/coding_rules/" + qProfile + "/" + activation;
		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = apiUrl + path;
			responseEntity = restTemplate.getForEntity(url, CodingRules.class);
			codingRules = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codingRules;

	}

	public RuleDetails getRuleDetails(String ruleKey) {
		ResponseEntity<RuleDetails> responseEntity = null;
		RuleDetails details = null;
		String path = "/api/rules/" + ruleKey;
		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = apiUrl + path;
			responseEntity = restTemplate.getForEntity(url, RuleDetails.class);
			details = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return details;

	}

	public boolean updateRuleDetails(RuleDetails rule) {
		RestTemplate restTemplate = new RestTemplate();

		try {
			String url = apiUrl + "/api/rules/";
			restTemplate.put(url, rule);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return true;

	}

	public IssuesDto getIssues() {
		ResponseEntity<IssuesDto> responseEntity = null;
		IssuesDto issues = null;
		String path = "/api/issues/search";
		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = apiUrl + path;
			responseEntity = restTemplate.getForEntity(url, IssuesDto.class);
			issues = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return issues;

	}

	public ArrayList<RoleDto> getRoles() {
		RestTemplate restTemplate = new RestTemplate();
		RoleDto[] body = null;
		String path = "/api/role/";
		try {
			String url = apiUrl + path;
			ResponseEntity<RoleDto[]> responseEntity = restTemplate.getForEntity(url, RoleDto[].class);
			body = responseEntity.getBody();
			System.out.println(body);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ArrayList<>(Arrays.asList(body));
	}

	public ArrayList<PrivilegeDto> getPrivilages() {
		RestTemplate restTemplate = new RestTemplate();
		PrivilegeDto[] body = null;
		String path = "/api/privilege/";
		try {
			String url = apiUrl + path;
			ResponseEntity<PrivilegeDto[]> responseEntity = restTemplate.getForEntity(url, PrivilegeDto[].class);
			body = responseEntity.getBody();
			System.out.println(body);
		} catch (Exception e) {
			e.printStackTrace();
			// new ArrayList<>(Arrays.asList(body));
		}

		return new ArrayList<>(Arrays.asList(body));
	}

	public boolean deleteRole(Long roleId) {
		RestTemplate restTemplate = new RestTemplate();
		String url = apiUrl + "/api/role/" + roleId;
		try {
			restTemplate.delete(url);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return true;
	}

	public boolean updateRole(RoleDto role) {
		RestTemplate restTemplate = new RestTemplate();

		try {
			String url = apiUrl + "/api/role/";
			restTemplate.put(url, role);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return true;

	}

	public RoleDto addRole(RoleDto role) {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<RoleDto> responseEntity = null;

		try {
			String url = apiUrl + "/api/role/";
			HttpEntity<RoleDto> entity = new HttpEntity<RoleDto>(role);
			responseEntity = restTemplate.postForEntity(url, entity, RoleDto.class);

		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return responseEntity.getBody();
	}

	public boolean deleteUser(Long userId) {
		RestTemplate restTemplate = new RestTemplate();
		String url = apiUrl + "/api/user/" + userId;
		try {
			restTemplate.delete(url);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return true;
	}

	public boolean updateUser(UserDto user) {
		RestTemplate restTemplate = new RestTemplate();

		try {
			String url = apiUrl + "/api/user/";
			restTemplate.put(url, user);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return true;

	}

	public UserDto addUser(UserDto user) {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<UserDto> responseEntity = null;

		try {
			String url = apiUrl + "/api/user/";
			HttpEntity<UserDto> entity = new HttpEntity<UserDto>(user);
			responseEntity = restTemplate.postForEntity(url, entity, UserDto.class);

		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return responseEntity.getBody();
	}

	public ArrayList<UserDto> getUsers() {
		RestTemplate restTemplate = new RestTemplate();
		UserDto[] body = null;
		String path = "/api/user/";
		try {
			String url = apiUrl + path;
			ResponseEntity<UserDto[]> responseEntity = restTemplate.getForEntity(url, UserDto[].class);
			body = responseEntity.getBody();
			System.out.println(body);
		} catch (Exception e) {
			e.printStackTrace();
			// new ArrayList<>(Arrays.asList(body));
		}

		return new ArrayList<>(Arrays.asList(body));
	}

	public QualityProfiles getQualityProfiles() {
		ResponseEntity<QualityProfiles> responseEntity = null;
		QualityProfiles qualityProfiles = null;
		String path = "/api/profile/search";
		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = apiUrl + path;
			responseEntity = restTemplate.getForEntity(url, QualityProfiles.class);
			qualityProfiles = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qualityProfiles;

	}

	public boolean deleteProfile(String key) {
		RestTemplate restTemplate = new RestTemplate();
		String url = apiUrl + "/api/profile/" + key;
		try {
			restTemplate.delete(url);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return true;
	}

	public ProfileCopy copyProfile(Profile profile) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ProfileCopy> responseEntity = null;
		try {
			String url = apiUrl + "/api/profile/copy";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			ProfileCopy profileCopy = new ProfileCopy();
			profileCopy.setFromKey(profile.getKey());
			profileCopy.setToName(profile.getName());

			responseEntity = restTemplate.postForEntity(url, profileCopy, ProfileCopy.class);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return responseEntity.getBody();

	}

	public Profile updateProfile(Profile profile) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Profile> responseEntity = null;
		try {
			String url = apiUrl + "/api/profile/update";

			responseEntity = restTemplate.postForEntity(url, profile, Profile.class);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return responseEntity.getBody();

	}

	public Profile createProfile(Profile profile) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Profile> responseEntity = null;
		try {
			String url = apiUrl + "/api/profile/create";

			responseEntity = restTemplate.postForEntity(url, profile, Profile.class);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return responseEntity.getBody();

	}

	public Project getProject() {
		ResponseEntity<Project> responseEntity = null;
		Project project = null;
		String path = "/api/Project/search";
		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = apiUrl + path;
			responseEntity = restTemplate.getForEntity(url, Project.class);
			project = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return project;
	}

	public boolean deleteProject(long projectId) {
		RestTemplate restTemplate = new RestTemplate();
		String url = apiUrl + "/api/project/" + projectId;
		try {
			restTemplate.delete(url);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return true;
	}

	/*public Project updateProject(Project originalEditedProject) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Project> responseEntity = null;
		try {
			String url = apiUrl + "/api/project/update";

			responseEntity = restTemplate.postForEntity(url, originalEditedProject, Project.class);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return responseEntity.getBody();
	}*/

	public Project createProject(Project project) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Project> responseEntity = null;
		try {
			String url = apiUrl + "/api/project/";

			responseEntity = restTemplate.postForEntity(url, project, Project.class);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return responseEntity.getBody();
	}
	
	/*public List<Project> getAllProjects() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Project> responseEntity = null;
		try {
			String url = apiUrl + "/api/project/getAll";

			responseEntity = restTemplate.postForEntity(url, null, Project.class);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return responseEntity.getBody();
	}*/
	
	public Project getProject(String projectKey){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Project> responseEntity = null;
		try {
			String url = apiUrl + "/api/project/getProject/" + projectKey;

			responseEntity = restTemplate.getForEntity(url, Project.class);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return responseEntity.getBody();

	}
	
	public boolean updateProject(Project project) {
		RestTemplate restTemplate = new RestTemplate();

		try {
			String url = apiUrl + "/api/project/";
			restTemplate.put(url, project);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return true;

	}

}
