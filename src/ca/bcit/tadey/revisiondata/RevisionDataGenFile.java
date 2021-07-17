/**
 * 
 */
package ca.bcit.tadey.revisiondata;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ca.bcit.tadey.revision.state.BeliefState;

/**
 * The RevisionDataGenFile class is responsible for creating and writing the data for the generated csv file
 * 
 * @author sam_t
 */
public class RevisionDataGenFile {

	File outputfile;
	String filepath;
	
	/**
	 * RevisionDataGenFile constructor
	 * 
	 * @param filename as a string
	 */
	public RevisionDataGenFile(String filename) {
		
		this.filepath = filename;
		
		 try {
			  this.outputfile = new File(filename);
		      if (this.outputfile.createNewFile()) {
		        System.out.println("File created: " + this.outputfile.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}

	/**
	 * Writes the header for all columns to the csv file
	 * This method overwrites all previous contents of that file, if it exists
	 * 
	 * @param size number of columns for each header type
	 */
	public void writeHeader(int size) {
		try {
			  //overwrite mode
		      FileWriter myWriter = new FileWriter(this.filepath);
		      myWriter.write(buildHeader(size));
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	/**
	 * Writes a line of data to the csv file
	 * 
	 * @param bel beliefstate to write
	 * @param sent sentence to write
	 * @param goal goalstate to write
	 */
	public void writeLine(BeliefState bel, BeliefState sent, BeliefState goal) {
		try {
			  //append mode
		      FileWriter myWriter = new FileWriter(this.filepath, true);
		      myWriter.append(buildLine(bel,sent,goal));
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	/**
	 * Builds the header string to write to the csv file
	 * For each header column type, create 'size' number of columns
	 * 
	 * @param size
	 * @return
	 */
	private String buildHeader(int size) {
		String[] c = {"b", "s", "g"};
		int i,j,col;
		StringBuilder build = new StringBuilder();
		
		for (i = 0; i < c.length; i++)
		{
			for (j = 0; j < size; j++)
			{
				col = j+1;
				build.append(c[i] + col);
				build.append(",");
			}
		}
		build.append("\n");
		
		return build.toString();
	}
	
	/**
	 * Builds the string to write as the line of data to the csv file
	 *
	 * @param bel beliefstate to write
	 * @param sent sentence to write
	 * @param goal goalstate to write
	 * @return String to write to the csv file
	 */
	private String buildLine(BeliefState bel, BeliefState sent, BeliefState goal) {
		StringBuilder line = new StringBuilder();
		int i;
		
		//beliefs
		for (i = 0; i < bel.getBeliefs().size(); i++)
		{
			line.append(bel.getBeliefs().get(i));
			line.append(",");
		}
		//sentence
		for (i = 0; i < sent.getBeliefs().size(); i++)
		{
			line.append(sent.getBeliefs().get(i));
			line.append(",");
		}
		//if sentence length != belief length add blanks up to belief length
		for (i = sent.getBeliefs().size(); i < bel.getBeliefs().size(); i++)
		{
			line.append("2"); //? " " cant be an emtpy value
			line.append(",");
		}
		//goals
		for (i = 0; i < goal.getBeliefs().size(); i++)
		{
			line.append(goal.getBeliefs().get(i));
			line.append(",");
		}
		line.append("\n");
		
		return line.toString();
	}

}
