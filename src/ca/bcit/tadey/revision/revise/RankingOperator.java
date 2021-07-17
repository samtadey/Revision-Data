/**
 * 
 */
package ca.bcit.tadey.revision.revise;

import ca.bcit.tadey.revision.state.BeliefState;
import ca.bcit.tadey.revision.state.State;

/**
 * A RankingOperator is a simple ReivisonOperator that shifts states up one position if that state appears in the sentence to 
 * revise by
 * 
 * @author sam_t
 */
public class RankingOperator extends RevisionOperator {

	/**
	 * Default RankingOperator constructor
	 */
	public RankingOperator() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * revise implementation for the RankingOperator
	 * Shift a state up in the goal state if it appears in the sentence
	 */
	@Override
	public BeliefState revise(BeliefState beliefs, BeliefState sentence) {
		State s,t;
		BeliefState goal = new BeliefState(beliefs);
		
		//no change
		if (goal.getBeliefs().size() == sentence.getBeliefs().size())
			return goal;
		
		//for each sentence state, shift state up one 
		for (int i = 0; i < sentence.getBeliefs().size(); i++)
		{
			s = sentence.getBeliefs().get(i);
			for (int j = 0; j < goal.getBeliefs().size(); j++)
			{
				t = goal.getBeliefs().get(j);
				//if we've found it
				//shift the state up
				if (s.equals(t))
					shiftState(goal, j);
			}
		}
		
		return goal;
	}
	


}
