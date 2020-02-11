package dk.ku.di.oodcr;

public class Marking {
	
	public Boolean executed;
	public Boolean included;
	public Boolean pending;	

	public Marking(boolean ex, boolean in, boolean pe) {
		executed = ex;
		included = in;
		pending = pe;
	}

}
