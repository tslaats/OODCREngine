package dk.ku.di.oodcr;

import java.util.HashSet;

public class Event {
	
	
	public String name;
	public String label;
	
	public Marking marking = new Marking(false, true, false);
	
	public HashSet<Event> conditions = new HashSet<>();
	public HashSet<Event> respones = new HashSet<>();
	public HashSet<Event> milestones = new HashSet<>();
	public HashSet<Event> includes = new HashSet<>();
	public HashSet<Event> excludes = new HashSet<>();

	public Event(String n, String l)
	{
		name = n;
		label = l;
	}	
	
	
	public Event(String n)
	{
		this(n, n);
	}	
	
	public Boolean enabled()
	{
		if (!marking.included)
			return false;
		
		for (Event e : conditions)
			if (e.marking.included && !e.marking.executed)
				return false;
		
		for (Event e : milestones)
			if (e.marking.included && e.marking.pending)
				return false;
		
		return true;
	}
	
	public void execute()
	{
		if(!enabled())
			return;
		
		marking.executed = true;
		marking.pending = false;
		
		for (Event e: respones)
			e.marking.pending = true;
		
		for (Event e: excludes)
			e.marking.included = false;

		for (Event e: includes)
			e.marking.included = true;
		
		return;		
	}
	
	
	public boolean isAccepting()
	{
		return (!(marking.pending && marking.included));		
	}
	
	
}
