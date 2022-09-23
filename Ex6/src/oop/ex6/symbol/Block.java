package oop.ex6.symbol;

import java.util.ArrayList;

/**
 * A class represents a Block of code (= method or if\while)
 */
public class Block {

	// #################### //
	// #### ATTRIBUTES #### //
	// #################### //

	private int line;
	private final String name;
	private final ArrayList<Variable> parameters;

	// ###################### //
	// #### CONSTRUCTORS #### //
	// ###################### //

	/**
	 * constructs a new block from the given details.
	 * @param name block name (null in case of if\while block)
	 * @param parameters parameters of block (method arguments, or if\while conditions)
	 */
	public Block(String name, ArrayList<Variable> parameters) {
		this.name = name;
		this.parameters = parameters;
	}

	/**
	 * constructs a new block from the given details.
	 * @param parameters parameters of block (method arguments, or if\while conditions)
	 */
	public Block(ArrayList<Variable> parameters) {
		this.name = null;
		this.parameters = parameters;
	}

	// ################# //
	// #### METHODS #### //
	// ################# //

	/**
	 * sets the starting line of the block.
	 * @param line line number
	 */
	public void setStartLine(int line) {
		this.line = line;
	}

	/**
	 * gets line number
	 * @return line number
	 */
	public int lineNum() {
		return this.line;
	}

	/** gets method name */
	public String name() {
		return name;
	}

	/** gets block parameters */
	public ArrayList<Variable> parameters() {
		return parameters;
	}

	/** gets arguments number */
	public int argsNum(){
		return parameters.size();
	}

}
