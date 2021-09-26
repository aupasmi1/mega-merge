package com.psmith.merge;

import com.psmith.merge.exception.MergeException;

/**
 * 
 * Merger interface to be implemented by implementations of the
 * Merger.
 *
 */
public interface Merger {
	
	void mergeWrite(String input, String output) throws MergeException;
	
	String merge(String input) throws MergeException;

}
