package dk.ku.di.oodcr;

import java.util.HashMap;

public class DCRGraph {
	public HashMap<String, Event> events = new HashMap<String, Event>();
	
	public DCRGraph() { 
	}
	
	public Event addEvent(String n)
	{
		return addEvent(n, n);
	}	
	
	public Event addEvent(String n, String l)
	{
		Event e = new Event(n, l);
		events.put(n, e);
		return e;
	}
	
	public void addEvent(Event e)
	{
		events.put(e.name, e);
	}
	
	
	// src -->* trg
	public void addCondition(String src, String trg)
	{
		if (! events.containsKey(src))
			return;
		
		if (! events.containsKey(trg))
			return;
		
		events.get(trg).conditions.add(events.get(src));		
	}
	
	// src --><> trg
	public void addMilestone(String src, String trg)
	{
		if (! events.containsKey(src))
			return;
		
		if (! events.containsKey(trg))
			return;
		
		events.get(trg).milestones.add(events.get(src));		
	}
	
		
	// src *--> trg
	public void addResponse(String src, String trg)
	{
		if (! events.containsKey(src))
			return;
		
		if (! events.containsKey(trg))
			return;
		
		events.get(src).respones.add(events.get(trg));		
	}
	
	
	// src -->+ trg
	public void addInclude(String src, String trg)
	{
		if (! events.containsKey(src))
			return;
		
		if (! events.containsKey(trg))
			return;
		
		events.get(src).includes.add(events.get(trg));		
	}		
	

	// src -->% trg
	public void addExclude(String src, String trg)
	{
		if (! events.containsKey(src))
			return;
		
		if (! events.containsKey(trg))
			return;
		
		events.get(src).excludes.add(events.get(trg));		
	}	
	
	
	public void execute(String e)
	{
		if (! events.containsKey(e))
			return;		
		events.get(e).execute();
	}
	
	public Boolean isAccepting()
	{
		for (Event e : events.values())
			if (!e.isAccepting())
				return false;
						
		return true;	
	}
}
