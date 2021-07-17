/**
 * 
 */
package ca.bcit.tadey.revisiondata;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ca.bcit.tadey.revision.state.BeliefState;
import ca.bcit.tadey.revision.state.State;
import ca.bcit.tadey.revision.state.StateHelper;

/**
 * @author sam_t
 *
 */
public class RevisionDataGen {

	public static final int UPPER_RANGE = 2;
	
	private int num_vars;
	Random random;
	
	/**
	 * 
	 */
	public RevisionDataGen(int num_vars) {
		this.num_vars = num_vars;
		random = new Random();
	}
	
	
	public int getNumVars() {
		return num_vars;
	}


	public void setNumVars(int num_vars) {
		this.num_vars = num_vars;
	}
	
	
	private State genState() {
		StringBuilder build = new StringBuilder();
		
		for (int i = 0; i < this.num_vars; i++)
			build.append(random.nextInt(UPPER_RANGE));
		
		return new State(build.toString());
	}
	
	
	public BeliefState genSentence() {
		int num_possible_states, num_states;
		Set<State> sentence;
		//System.out.println(num_vars);
		num_possible_states = (int) Math.pow(2, num_vars);
		//System.out.println(num_possible_states);
		num_states = random.nextInt(num_possible_states) + 1;
		//System.out.println(num_states);
		
		//if full set returned
		if (num_states == num_possible_states)
			return new BeliefState(StateHelper.generateStates(num_vars));
		
		//if not full set, generate each state randomly
		sentence = new HashSet<State>();
		sentence.add(genState());
		
		while (sentence.size() < num_states)
			sentence.add(genState());
		
		return new BeliefState(sentence);
	}
	
	
	private BeliefState genOrderedFullSets() {
		int num_possible_states = (int) Math.pow(2, num_vars);
		//states in order
		BeliefState beliefs = new BeliefState(StateHelper.generateStates(this.num_vars));
		BeliefState reorder = new BeliefState();
		int upper = num_possible_states;
		int idx;
		//reorder states
		while (beliefs.getBeliefs().size() > 0 && upper > 0)
		{
			idx = random.nextInt(upper--); //see
			reorder.addBelief(beliefs.getBeliefs().get(idx));
			beliefs.removeBelief(idx);
		}

		return reorder;
	}
	
	//max beliefs?
	public Set<BeliefState> genBeliefStates(int num_beliefs) {
		Set<BeliefState> beliefs = new HashSet<BeliefState>();
		
		while (beliefs.size() < num_beliefs)
			beliefs.add(genOrderedFullSets());
		
		return beliefs;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RevisionDataGen gen = new RevisionDataGen(3);
		
		System.out.println(gen.genSentence().toString());
		
		System.out.println("Genning Belief States");
		Set<BeliefState> ss = gen.genBeliefStates(6);
		
		for (BeliefState sss : ss)
			System.out.println(sss.toString());
	}




}
