package br.ufrj.cos.prisma.miner.Extractor.model;

import miner.Commit;
import miner.Event;
import miner.MinerFactory;

public class MinerEvent {

	Event event;
	
	public MinerEvent(MinerActivity activity, Commit c) {
		this.event = MinerFactory.eINSTANCE.createEvent();
		this.event.setActivity(activity.getActivity());
		this.event.setDate(c.getDate());
		this.event.setLifecycleStatus("COMPLETE");
	}
	
	public Event getEvent() {
		return this.event;
	}
}
