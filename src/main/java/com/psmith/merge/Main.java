package com.psmith.merge;

import com.psmith.merge.exception.MergeException;

public class Main {

	public static void main(String[] args) {
		for (String arg : args) {
			System.out.println(arg);
		}
		if (args.length < 2) {
			printHelp();
			System.exit(1);
		}

		Merger merger = new MegaMerge();
		try {
			merger.mergeWrite(args[0], args[1]);
			System.out.println("File writen to " + args[1]);
			System.exit(0);
		} catch (MergeException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			printHelp();
		}

	}
	
	private static void printHelp() {
		System.out.println("Usage: mvn exec:java <path_to_input_folder> <path_to_output_file>");
		System.out.println("eg. mvn exec:java -Dexec.args=\"'/tmp/input' '/tmp/output/output.csv'\"");
	}
}