package br.ufrj.cos.prisma;

import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;

public class GitManager {
	public static boolean verbose = true;
	public static boolean setNoCheckout = true;

	public static String repoURL;
	public static String localDir;


	public static void main(String[] args) {
		if (args.length < 2) {
			usage();
			return;
		}

		repoURL = args[0];
		localDir = args[1];

		GitRepositoryHelper repositoryHelper = new GitRepositoryHelper(repoURL,
				localDir);
		Git git = repositoryHelper.getRepo();
		if (git == null) {
			log("Something went wrong :(");
			return;
		}

		List<RevCommit> commitsHistory = repositoryHelper.getCommitsHistory();
		for (RevCommit commit: commitsHistory) {
			repositoryHelper.cloneFromCommit(commit);
		}
		
	}

	private static void log(String message) {
		if (verbose) {
			System.out.println(message);
		}
	}

	private static void usage() {
		String usage = String.format("Usage: repositoryUrl localDir");
		System.out.println(usage);
	}
}
