/**
 * 
 */
package ca.bcit.tadey.revision.revise;

import ca.bcit.tadey.revision.state.BeliefState;
import ca.bcit.tadey.revision.state.State;

/**
 * The RevisionOperator class is an abstract implementation of a revision operator type for the data generation.
 * a revision operator will be run to generate the goal states for given belief states and sentences
 * 
 * @author sam_t
 */
public abstract class RevisionOperator {

	/**
	 * Default RevisionOperator constructor
	 */
	public RevisionOperator() { }
	
	/**
	 * A basic revision strategy implementation, shifts the state at the index up one slot in the ordered belief state
	 * 
	 * @param beliefs
	 * @param idx
	 */
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

	/**
	 * Revise the beliefs and sentence into a goal beliefstate
	 * 
	 * @param beliefs as a beliefstate
	 * @param sentence as a set of observed states
	 * @return a goal state as revised beliefs
	 */
	public abstract BeliefState revise(BeliefState beliefs, BeliefState sentence);
	
}
