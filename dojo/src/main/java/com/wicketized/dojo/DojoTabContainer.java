package com.wicketized.dojo;

public abstract class DojoTabContainer extends DojoStackContainer<DojoTabContainer> {

	public DojoTabContainer(String id) {
		
		super(id);
	}
	
	@Override
	public String getDojoType() {
		
		return "dijit.layout.TabContainer";
	}
	
	@Override
	public Class<DojoTabContainer> getDojoComponentClass() {
		
		return DojoTabContainer.class;
	}
}
