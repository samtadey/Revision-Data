/**
 * 
 */
package ca.bcit.tadey.revision.revise;

import ca.bcit.tadey.revision.state.BeliefState;
import ca.bcit.tadey.revision.state.State;

/**
 * @author sam_t
 *
 */
public abstract class RevisionOperator {

	/**
	 * 
	 */
	public RevisionOperator() {
		// TODO Auto-generated constructor stub
	}
	
	protected void shiftState(BeliefState beliefs, int idx) {
		State temp, s, t;
		
		if (idx == 0)
			return;
		
		s = beliefs.getBeliefs().get(idx);
		t = beliefs.getBeliefs().get(idx-1);
		
		temp = s;
		beliefs.getBeliefs().set(idx, t);
		beliefs.getBeliefs().set(idx-1, temp);
	}

	public abstract BeliefState revise(BeliefState beliefs, BeliefState sentence);
	
}
