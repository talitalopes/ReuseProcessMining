package br.ufrj.cos.prisma.helpers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.revwalk.RevCommit;

import br.ufrj.cos.prisma.model.GithubRepository;

public class GitRepositoryHelper {

	String repoURL;
	File repoFile;
	
	public GitRepositoryHelper(GithubRepository repo) {
		this.repoURL = repo.getCloneUrl();
		this.repoFile = repo.getRepoFile();
	}
	
	public GitRepositoryHelper(String url, File repoFile) {
		this.repoURL = url;
		this.repoFile = repoFile;		
	}
	
	public Git getRepo() {
		Git git = null;
		try {
			// Repository not found. A clone command will be called.
			if (!repoExists(repoFile)) {
				cloneGitRepo();
			}
			git = Git.open(repoFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return git;
	}
	
	public List<RevCommit> getCommitsHistory() {
		List<RevCommit> commitsHistory = new ArrayList<RevCommit>(); 
		LogCommand logcommand;
		try {
			logcommand = getRepo().log().all();
			Iterable<RevCommit> commitsIterable = logcommand.call();
			
			for (RevCommit commit: commitsIterable) {
				commitsHistory.add(0, commit);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoHeadException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
		
		return commitsHistory;
	}
	
	public void cloneFromCommit(RevCommit commit) {
		if (commit == null)
			return;
		
		deleteRepo();
		try {
			Git git = getRepo();
			git.checkout().setName(commit.name()).call();

		} catch (RefAlreadyExistsException e) {
			e.printStackTrace();
		} catch (RefNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidRefNameException e) {
			e.printStackTrace();
		} catch (CheckoutConflictException e) {
			System.out.println("ERROR: CheckoutConflictException");
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}
	
	public void cloneFromOldestCommit() {
		List<RevCommit> commits = getCommitsHistory();
		if (commits == null || commits.size() == 0) {
			System.out.println("No commits were made to this repository.");
			return;
		}
		
		RevCommit firstCommit = getCommitsHistory().get(0);
		cloneFromCommit(firstCommit);
	}
	
	public File cloneGitRepo() {
		CloneCommand clone = new CloneCommand();
		clone.setURI(repoURL).setNoCheckout(true).setDirectory(repoFile);

		try {
			clone.call();

		} catch (InvalidRemoteException e) {
			System.out.println("InvalidRemoteException");
			e.printStackTrace();
		} catch (TransportException e) {
			System.out.println("TransportException");
			e.printStackTrace();
		} catch (GitAPIException e) {
			System.out.println("GitAPIException");
			e.printStackTrace();
		}
		
		return repoFile;
	}
	
	public boolean repoExists(File repoFile) {
		try {
			if (repoFile.isDirectory()) {
				File[] fileNames = repoFile.listFiles();
				for (int i = 0; i < fileNames.length; i++) {
					if (fileNames[i].toString().contains(".git")) {
						return true;
					}
				}
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public void deleteRepo() {
		try {
			FileUtils.deleteDirectory(repoFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File getRepoFile() {
		return this.repoFile;
	}

}
