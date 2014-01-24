package br.ufrj.cos.prisma.miner.Extractor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import miner.Process;
import miner.ProcessInstance;

public class MinerProcess {
	
	Process process;
	HashMap<String, MinerActivity> processActivities;
	HashMap<String, MinerApplication> applicationsMap;
	
	public MinerProcess(Process process) {
		this.process = process;
		processActivities = new HashMap<String, MinerActivity>();
		applicationsMap = new HashMap<String, MinerApplication>();
	}
	
	public void addActivity(MinerActivity minerActivity) {
		this.process.getActivities().add(minerActivity.getActivity());
		this.processActivities.put(minerActivity.getActivity().getId(), minerActivity);
	}
	
	public MinerActivity getActivityById(String key) {
		return this.processActivities.get(key);	
	}
	
	/**
	 * Returns an application with the given name.
	 * If no application with the provided name exists a new application is created.
	 * **/
	public MinerApplication getApplicationByName(String name) {
		if (applicationsMap.containsKey(name)) { 
			return applicationsMap.get(name);
		}
		MinerApplication app = new MinerApplication(name);
		return app;
	}
	
	public void addApplication(MinerApplication application) {
		this.applicationsMap.put(application.getApplication().getName(), application);
	}
	
	public List<ProcessInstance> getAllApplications() {
		List<ProcessInstance> applications = new ArrayList<ProcessInstance>();
		for (MinerApplication app: this.applicationsMap.values()) {
			applications.add(app.getApplication());
		}
		return applications;
	}
}
