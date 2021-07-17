/**
 * 
 */
package ca.bcit.tadey.revision.revise;

import ca.bcit.tadey.revision.state.BeliefState;
import ca.bcit.tadey.revision.state.State;
import ca.bcit.tadey.revision.state.StateHelper;

/**
 * @author sam_t
 *
 */
public class RankingOperator extends RevisionOperator {

	/**
	 * 
	 */
	public RankingOperator() {
		// TODO Auto-generated constructor stub
	}

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
	


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		BeliefState bs = new BeliefState(StateHelper.generateStates(3));
		System.out.println(bs.toString());
		
		BeliefState sent = new BeliefState();
		sent.addBelief(new State("010"));
		sent.addBelief(new State("111"));
		
		RevisionOperator op = new RankingOperator();
		BeliefState goal = op.revise(bs, sent);
		
		System.out.println(goal.toString());
		
//		op.shiftState(bs, 3);
//		System.out.println(bs.toString());
//		
//		op.shiftState(bs, 0);
//		System.out.println(bs.toString());
//		
//		op.shiftState(bs, 6);
//		System.out.println(bs.toString());
//		
//		op.shiftState(bs, 7);
//		System.out.println(bs.toString());
		
	}

}
