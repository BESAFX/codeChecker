package com.rmgs.codeChecker.git;

import java.io.File;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


public class GitController {
    public GitController() {

    }

    private CredentialsProvider credentialsProvider;

    GitController(String username, String password) {
        credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
    }

    public Boolean cloneRepo(String directory, String uri, String username, String password) throws Exception {
        credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
        CloneCommand cmd = Git.cloneRepository();
        cmd.setDirectory(new File(directory));
        cmd.setURI(uri);
        cmd.setCredentialsProvider(credentialsProvider);
        try {
            Git git = cmd.call();
            git.close();
            return true;
        } catch (Exception e) {
            if ((e instanceof TransportException)
                    && ((e.getMessage().contains("Authentication is required but no CredentialsProvider has been registered")
                    || e.getMessage().contains("not authorized")))) {

            }
        }
        return false;
    }

    public void cloneRepoWithBranch(final String gitUrl, final String localPath, final String branchName) throws Exception {
        try {
            Git.cloneRepository()
                    .setURI(gitUrl)
                    .setDirectory(new File(localPath))
                    .setBranch(branchName)
                    .call();
        } catch (TransportException e) {
            e.printStackTrace();
        }
    }
	
	/*public void checkoutBranch(final String branchName) {
		Git.checkout()
	    .setCreateBranch(true)
	    .setName(branchName)
	    .call();
	}*/


}
