package br.ufrj.cos.prisma.miner.Extractor.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import miner.Commit;
import miner.Event;
import miner.MinerFactory;
import miner.MinerPackage;

public class MinerCommit {

	Commit c;
	
	public MinerCommit(String[] keys) {
		this.c = MinerFactory.eINSTANCE.createCommit();
		c.eSet(MinerPackage.eINSTANCE.getCommit_Id(), keys[keys.length - 1]);
		c.eSet(MinerPackage.eINSTANCE.getCommit_Date(),
				getCommitDate(keys[keys.length - 2]));
	}
	
	private Date getCommitDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date commitDate = null;
		try {
			commitDate = formatter.parse(date);
		} catch (ParseException e) {
			commitDate = Calendar.getInstance().getTime();
		}
		return commitDate;
	}

	public void addEvent(Event e) {
		this.c.getEvents().add(e);
	}

	public Commit getCommit() {
		return this.c;
	}
}
