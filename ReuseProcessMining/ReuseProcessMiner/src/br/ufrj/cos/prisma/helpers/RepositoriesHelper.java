package br.ufrj.cos.prisma.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.ufrj.cos.prisma.model.GithubRepository;

public class RepositoriesHelper {

	private static final String REPO_SEARCH_URL_FORMAT = "https://api.github.com/search/repositories?q=%s+language:java+in:name,description,readme&sort=stars&order=desc";
	public static List<GithubRepository> listRepositories(String searchKey) {
		String json = getRepositoriesJSON(searchKey);
		System.out.println(json);
		return new ArrayList<GithubRepository>();
	}

	private static String getRepositoriesJSON(String searchKey) {
		String output = "";
		String urlStr = String.format(REPO_SEARCH_URL_FORMAT, searchKey);

		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return output;
	}
}
