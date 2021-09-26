# Mega Merge

This is a command line Java application that will merge two sets of catalog files in CSV format to produce a single consolidated catalog CSV file 

## Description

This application will read a catalog file and a barcodes file for each group of
suppliers. For each group the program will create a list of SKU (products) with their associated barcodes. These two lists will be merged on the basis that we take all items from list A then add any items from list B unless:
- the sku and description for an item are the same in both lists then take the item from the list A and discard the item from list B
- any barcode from an item in list A matches a barcode from an item in list B regardless of sku and description then take the item from the list A and discard the item from list B


## Requirements

- Implemented and tested using Java 16 in a Java 11 compatibility mode

- Tests require JUnit5

- Project dependencies and compiling managed by Maven

- Input files must be defined as barcodesA.csv or barcodesB.csv and catalogA.csv or catalogB.csv.

- There must be 4 files e.g. A and B files for both catalog and barcodes.

- Files must follow the format as per the example files under */src/test/resources/input*

## Compile, Test, Run and Packaging

- Compile: `mvn compile`

- Test: `mvn test` Jacoco coverage report in *target/site/jacoco* folder

- Run: `mvn exec:java -Dexec.args="'<path_to_input_folder>' '<path_to_output_file>'"`

- Packaging: `mvn package`, compiled jar in *target/* folder