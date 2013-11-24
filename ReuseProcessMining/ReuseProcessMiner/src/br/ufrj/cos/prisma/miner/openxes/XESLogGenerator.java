package br.ufrj.cos.prisma.miner.openxes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.extension.std.XIdentityExtension;
import org.deckfour.xes.extension.std.XLifecycleExtension;
import org.deckfour.xes.extension.std.XTimeExtension;
import org.deckfour.xes.factory.XFactoryBufferedImpl;
import org.deckfour.xes.id.XID;
import org.deckfour.xes.id.XIDFactory;
import org.deckfour.xes.model.XAttribute;
import org.deckfour.xes.model.XAttributeMap;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.deckfour.xes.out.XesXmlSerializer;
import org.deckfour.xes.xstream.XesXStreamPersistency;

import br.ufrj.cos.prisma.model.miner.Event;
import br.ufrj.cos.prisma.model.miner.Process;
import br.ufrj.cos.prisma.model.miner.ProcessInstance;

import com.thoughtworks.xstream.XStream;

public class XESLogGenerator {

	private XFactoryBufferedImpl factory;
	private XLog log;
	
	public XESLogGenerator() {
		factory = new XFactoryBufferedImpl();
		
		try {
			XAttributeMap attributes = factory.createAttributeMap();
			XAttribute nameAttr = factory.createAttributeLiteral(XConceptExtension.KEY_NAME, 
					"Framework process", XConceptExtension.instance());
			attributes.put(XConceptExtension.KEY_NAME, nameAttr);
			
			log = factory.createLog(attributes);

		} catch (Exception e) {
			log = null;
			e.printStackTrace();
		}
	}
	
	public XTrace createNewTrace(String frameworkInstance) {
		XAttributeMap attributes = factory.createAttributeMap();
		XID traceId = XIDFactory.instance().createId();
		XAttribute idAttr = factory.createAttributeID(XIdentityExtension.KEY_ID, 
				traceId, XIdentityExtension.instance());
		XAttribute nameAttr = factory.createAttributeLiteral(XConceptExtension.KEY_NAME, 
				frameworkInstance, XConceptExtension.instance());
		attributes.put(XIdentityExtension.KEY_ID, idAttr);
		attributes.put(XConceptExtension.KEY_NAME, nameAttr);
		
		XTrace trace = factory.createTrace(attributes);
		return trace;
	}
	
	public XEvent createEvent(String type, String name, Date timestamp) {
		XEvent event = null;
		
		String[] nameArray = name.split("\\.");
		if (type.toLowerCase().equals("class_extension")) {
			type = "C";
		} else {
			type = "M";
		}
		
		String activityName = String.format("%s_%s", type, nameArray[nameArray.length - 1]);
		try {
			XAttributeMap attributes = factory.createAttributeMap();
			XID eventId = XIDFactory.instance().createId();
			XAttribute idAttr = factory.createAttributeID(XIdentityExtension.KEY_ID, 
					eventId, XIdentityExtension.instance());
			XAttribute nameAttr = factory.createAttributeLiteral(XConceptExtension.KEY_NAME, 
					activityName, XConceptExtension.instance());
			
			XAttribute lifecycleAttr = factory.createAttributeLiteral(XLifecycleExtension.KEY_TRANSITION, 
					"complete", XLifecycleExtension.instance());

			if (timestamp != null) {
				XAttribute timestampAttr = factory.createAttributeTimestamp(XTimeExtension.KEY_TIMESTAMP, timestamp, 
						XTimeExtension.instance());
				attributes.put(XTimeExtension.KEY_TIMESTAMP, timestampAttr);
			}
			
			attributes.put(XIdentityExtension.KEY_ID, idAttr);
			attributes.put(XConceptExtension.KEY_NAME, nameAttr);
			attributes.put(XLifecycleExtension.KEY_TRANSITION, lifecycleAttr);
			
			event = factory.createEvent(attributes);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return event;
	}
	
	public void getXESRepresentationFromProcess(Process p) {
		try {
			String traceName = null;
			XTrace trace = null;
			XEvent event = null;
			Date cDate = null;
			
			for (ProcessInstance pi: p.getInstances()) {
				traceName = pi.getName();
				trace = createNewTrace(traceName);
				if (trace != null) {
            		for (Event e: pi.getCommits().get(0).getEvents()) {
            			cDate = e.getDate();
            			event = createEvent(e.getActivity().getType().getName(), e.getActivity().getName(), cDate);
            			trace.add(event);
            		}            		
            	}	
				log.add(trace);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void serialize(String filename) {
		XStream xstream = new XStream();
		XesXStreamPersistency.register(xstream);
		XesXmlSerializer serializer = new XesXmlSerializer();
		
		try {
			File sFile = new File("/users/talitalopes/Documents/UFRJ/workspace/facts-extractor/ProcessReuseMiner/logs/" + filename);
			System.out.println("Serializing log with XStream");
			
			OutputStream oStream = new BufferedOutputStream(new FileOutputStream(sFile));
			// xstream.toXML(log, oStream);
			serializer.serialize(log, oStream);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public XLog getLog() {
		return log;
	}
}