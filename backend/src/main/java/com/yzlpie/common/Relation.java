package com.yzlpie.common;

import org.springframework.hateoas.Link;

/**
 * This is our internal API for the view. Links are identified by relations. Of course not every entity has a
 * link for every relation listed here, it always depends on the circumstances.
 * 
 * @author tobias.flohre
 */
public enum Relation {
	
	CREATE("new"), 
	SELF(Link.REL_SELF), 
	DELETE("delete"),
	UPDATE("edit"), 
	RESEARCH("select"),
	ALL("all");
	
	private String name;

	private Relation(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
