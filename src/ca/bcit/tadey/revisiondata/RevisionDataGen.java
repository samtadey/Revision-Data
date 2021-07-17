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
 * The RevisionDataGen class manages all the aspects of the csv and file that need to be generated randomly. This includes
 * states, beliefstates, and sentences.
 * 
 * @author sam_t
 */
public class RevisionDataGen {

	public static final int UPPER_RANGE = 2;
	
	private int num_vars;
	Random random;
	
	/**
	 * RevisionDataGen constructor
	 */
	public RevisionDataGen(int num_vars) {
		this.num_vars = num_vars;
		random = new Random();
	}
	
	/**
	 * Getter for number of variables
	 * @return number of variables as an int
	 */
	public int getNumVars() {
		return num_vars;
	}

	/**
	 * Setter for number of variables
	 * @param num_vars int
	 */
	public void setNumVars(int num_vars) {
		this.num_vars = num_vars;
	}
	
	/**
	 * Generates a random state with number of variables passed in teh constructor
	 * 
	 * @return State
	 */
	private State genState() {
		StringBuilder build = new StringBuilder();
		
		for (int i = 0; i < this.num_vars; i++)
			build.append(random.nextInt(UPPER_RANGE));
		
		return new State(build.toString());
	}
	
	/**
	 * Generates a random sentence as a belief state object. This sentence is comprised of random states, and can be of length
	 * 1 up to the max number of possible states given the number of variables
	 * 
	 * @return sentence as a BeliefState
	 */
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
	
	/**
	 * Generates a full ordered set of States, this is used to create unique belief states as a ranking
	 * of all possible states
	 * 
	 * @return a full ordered set as a belief state
	 */
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
	
	/**
	 * Generates a nummber of ranked belief states based on the parameter
	 * 
	 * @param num_beliefs number of completed ranked belief states to create
	 * @return set of unique belief states
	 */
	public Set<BeliefState> genBeliefStates(int num_beliefs) {
		Set<BeliefState> beliefs = new HashSet<BeliefState>();
		
		while (beliefs.size() < num_beliefs)
			beliefs.add(genOrderedFullSets());
		
		return beliefs;
	}


}
