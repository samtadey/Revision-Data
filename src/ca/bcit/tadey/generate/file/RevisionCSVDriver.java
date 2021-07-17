/**
 * 
 */
package ca.bcit.tadey.generate.file;

import java.util.Scanner;
import java.util.Set;

import ca.bcit.tadey.revision.revise.RankingOperator;
import ca.bcit.tadey.revision.revise.RevisionOperator;
import ca.bcit.tadey.revision.state.BeliefState;
import ca.bcit.tadey.revisiondata.RevisionDataGen;
import ca.bcit.tadey.revisiondata.RevisionDataGenFile;

/**
 * @author sam_t
 *
 */
public class RevisionCSVDriver {

	Scanner scan;

	public RevisionCSVDriver() {
		scan = new Scanner(System.in);
	}

	public int readNumVars() {
		String input;
		int num_vars;
		while (true)
		{
		    System.out.println("How many propositional variables?");
		    input = scan.nextLine();
		    try {
		    	num_vars = Integer.parseInt(input);
		    	break;
		    } catch (Exception ex) {
		    	System.out.println("Input must be an Integer");
		    }
		    
		}
		return num_vars;
	}
	
	public int readNumLines() {
		String input;
		int num_lines;
		
		while (true)
		{
		    System.out.println("How many data entries for the file? (10 - 10000 lines)");
		    System.out.println("This is number of lines PER initial belief state");
		    input = scan.nextLine();
		    try {
		    	num_lines = Integer.parseInt(input);
		    	if (num_lines >= 10 && num_lines <= 10000)
		    		break;
		    	else
		    		System.out.println("Number of lines must be within range");
		    } catch (Exception ex) {
		    	System.out.println("Input must be an Integer");
		    }	    
		}
		return num_lines;
	}
	
	public int readNumBeliefStates() {
		String input;
		int bel_states;
		
		while (true)
		{
		    System.out.println("How many initial belief states?");
		    input = scan.nextLine();
		    try {
		    	bel_states = Integer.parseInt(input);
		    	break;
		    } catch (Exception ex) {
		    	System.out.println("Input must be an Integer");
		    }
		}
		return bel_states;
	}
	
	public int readRevisionOperator() {
		String input;
		int rev_op, rev_max = 1;
		
		while (true)
		{
			System.out.println("Choose Revision Operator");
			System.out.println("1: Ranking Operator");
		    input = scan.nextLine();
		    try {
		    	rev_op = Integer.parseInt(input);
			    if (rev_op <= rev_max)
			    	break;
			    else
			    	System.out.println("Input not in list of choices");
		    } catch (Exception ex) {
		    	System.out.println("Input must be an Integer");
		    }
		}
		return rev_op;
	}
	
	
	public void closeScan() {
		this.scan.close();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int num_vars, num_lines, bel_states, rev_op, i;
		Set<BeliefState> all_beliefs;
		BeliefState sentence, goal;
		RevisionOperator op;
		String filename = "testing123.csv";
		
		RevisionDataGenFile genfile;
		
		op = new RankingOperator();
		
		RevisionCSVDriver read = new RevisionCSVDriver();
		
		//Get number of propositional variables
		num_vars = read.readNumVars();
		System.out.println(num_vars);
		
		//Get number of belief states
		bel_states = read.readNumBeliefStates();
	    System.out.println(bel_states);
	    
	    //get number of lines to generate
		num_lines = read.readNumLines();
	    System.out.println(num_lines);
	    
	    //get revision operator to do revision with
		rev_op = read.readRevisionOperator();
		System.out.println(rev_op);
	    
		//generate data structures
		RevisionDataGen gendata = new RevisionDataGen(num_vars);
		genfile = new RevisionDataGenFile(filename);
		//genfile.writeHeader((int) Math.pow(2, num_vars));
		
		all_beliefs = gendata.genBeliefStates(bel_states);
		for (BeliefState b : all_beliefs)
		{	
			for (i = 0; i < num_lines; i++)
			{
				//System.out.println("Beliefs");
				//System.out.println(b.toString());
				//System.out.println("Sentence");
				sentence = gendata.genSentence();
				//System.out.println(sentence.toString());
				goal = op.revise(b, sentence);
				//System.out.println("Goal");
				//System.out.println(goal.toString());
				genfile.writeLine(b, sentence, goal);
			}
		}
		
		//close scanner
		read.closeScan();
	}

}
