package com.psmith.merge.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation class for the imported products
 *
 */
public class Sku {

	private String id;
	private String description;
	private String supplierGroup;
	private List<String> barcodes;
	
	public Sku(String id, String description, String supplierGroup) {
		this.id = id;
		this.description = description;
		this.supplierGroup = supplierGroup;
		this.barcodes = new LinkedList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getBarcodes() {
		return barcodes;
	}

	public void setBarcodes(List<String> barcodes) {
		this.barcodes = barcodes;
	}

	public String getSupplierGroup() {
		return supplierGroup;
	}

	public void setSupplierGroup(String supplierGroup) {
		this.supplierGroup = supplierGroup;
	}

	@Override
	public int hashCode() {
		return Objects.hash(barcodes, description, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sku other = (Sku) obj;
		return ((Objects.equals(description, other.description) && Objects.equals(id, other.id))
				|| (barcodes != null && other.barcodes != null 
				&& barcodes.stream().anyMatch(bc -> other.barcodes.contains(bc))));
				 
	}

	


	
}
