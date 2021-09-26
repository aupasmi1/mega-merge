package com.psmith.merge;

import com.psmith.merge.exception.MergeException;

public interface Merger {
	
	public void mergeWrite(String input, String output) throws MergeException;
	
	public String merge(String input) throws MergeException;

}
