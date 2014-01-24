package br.ufrj.cos.prisma;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

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
		// TODO: replace project name
		for (RevCommit commit : commitsHistory) {
			String projectDir = localDir + "/" + commit.getCommitTime();
			log("projectDir: " + projectDir);

			GitRepositoryHelper localHelper = new GitRepositoryHelper(repoURL,
					projectDir);
			localHelper.cloneFromCommit(commit);
		}

		try {
			listFilesForFolder(new File(localDir));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void listFilesForFolder(final File folder) throws IOException {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				if (fileEntry.getName().equals(".project")) {
					String content = readFile(fileEntry);
					System.out.println(content);
				}
			}
		}
	}

	private static String readFile(File file) throws IOException {
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner = new Scanner(file);
	    String lineSeparator = System.getProperty("line.separator");

	    try {
	        while(scanner.hasNextLine()) {        
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        return fileContents.toString();
	    } finally {
	        scanner.close();
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
