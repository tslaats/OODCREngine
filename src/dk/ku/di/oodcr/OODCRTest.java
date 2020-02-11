package dk.ku.di.oodcr;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OODCRTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testScenario1() {
		DCRGraph g = new DCRGraph();
		Event e1 = g.addEvent("e1");
		Event e2 = g.addEvent("e2");
		Event e3 = g.addEvent("e3");		
		Event e4 = g.addEvent("e4");

		g.addCondition("e1", "e2");
		g.addCondition("e2", "e3");		
		g.addResponse("e1", "e3");
		g.addMilestone("e3", "e4");
		
		assert(e1.enabled());
		assert(!e2.enabled());
		assert(!e3.enabled());
		assert(e4.enabled());
		
		assert(g.isAccepting());
		
		e1.execute();
		
		assert(e1.enabled());
		assert(e2.enabled());
		assert(!e3.enabled());
		assert(!e4.enabled());
		
		assert(!g.isAccepting());
		
		g.execute("e2");
		
		assert(e1.enabled());
		assert(e2.enabled());
		assert(e3.enabled());
		assert(!e4.enabled());		
		
		assert(!g.isAccepting());
		
		g.execute("e3");		
		
		assert(e1.enabled());
		assert(e2.enabled());
		assert(e3.enabled());
		assert(e4.enabled());
		
		assert(g.isAccepting());
		
		assert(e1.marking.executed);
		assert(e2.marking.executed);
		assert(e3.marking.executed);
		assert(!e4.marking.executed);		
		
		assert(!e1.marking.pending);
		assert(!e2.marking.pending);
		assert(!e3.marking.pending);
		assert(!e4.marking.pending);				
	}
	
	@Test
	void testScenario2() {
		DCRGraph g = new DCRGraph();
		Event e1 = g.addEvent("e1");
		Event e2 = g.addEvent("e2");
		Event e3 = g.addEvent("e3");
		
		e2.marking.included = false;
		e2.marking.pending = true;

		g.addInclude("e1", "e2");
		g.addExclude("e2", "e2");
		g.addCondition("e2", "e3");
		g.addInclude("e3", "e1");
		g.addExclude("e3", "e1");		
		
		assert(e1.enabled());
		assert(!e2.enabled());
		assert(e3.enabled());
		
		assert(g.isAccepting());
		
		e1.execute();
		
		assert(e1.enabled());
		assert(e2.enabled());
		assert(!e3.enabled());
		
		assert(!g.isAccepting());
		
		g.execute("e2");
		
		assert(e1.enabled());
		assert(!e2.enabled());
		assert(e3.enabled());	
		
		assert(g.isAccepting());
		
		g.execute("e3");		
		
		assert(e1.enabled());
		assert(!e2.enabled());
		assert(e3.enabled());
		
		assert(g.isAccepting());
		
		assert(e1.marking.executed);
		assert(e2.marking.executed);
		assert(e3.marking.executed);
		
		assert(!e1.marking.pending);
		assert(!e2.marking.pending);
		assert(!e3.marking.pending);
		
		assert(e1.marking.included);
		assert(!e2.marking.included);
		assert(e3.marking.included);		
	}		

}
