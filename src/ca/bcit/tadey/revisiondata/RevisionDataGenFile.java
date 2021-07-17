/**
 * 
 */
package ca.bcit.tadey.revisiondata;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ca.bcit.tadey.revision.state.BeliefState;

/**
 * @author sam_t
 *
 */
public class RevisionDataGenFile {

	File outputfile;
	String filepath;
	
	/**
	 * 
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
			line.append(" ");
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
