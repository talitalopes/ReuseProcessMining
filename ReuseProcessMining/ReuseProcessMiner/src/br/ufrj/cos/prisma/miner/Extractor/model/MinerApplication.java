package br.ufrj.cos.prisma.miner.Extractor.model;

import java.util.List;

import miner.MinerFactory;
import miner.MinerPackage;
import miner.ProcessInstance;

public class MinerApplication {
	ProcessInstance application;
	List<MinerCommit> commits;
	
	public MinerApplication(String applicationName) {
		this.application = MinerFactory.eINSTANCE.createProcessInstance();
		this.application.eSet(MinerPackage.eINSTANCE.getProcess_Name(),
				applicationName);
	}
	
	public ProcessInstance getApplication() {
		return this.application;
	}
	
	public void addCommit(MinerCommit mc) {
		this.application.getCommits().add(mc.getCommit());
	}
}
