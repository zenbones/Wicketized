package com.wicketized.dojo;

import org.apache.wicket.ajax.AjaxRequestTarget;

public class DojoTooltipDialogBehavior extends DojoLazyLoadPaneBehavior {

	private DojoTooltipDialog dojoTooltipDialog;
	
	public DojoTooltipDialogBehavior(DojoTooltipDialog dojoTooltipDialog, DojoLazyLoadPane dojoLazyLoadPane, String event) {
		
		super(dojoLazyLoadPane, event);
		this.dojoTooltipDialog = dojoTooltipDialog;
		
	}
	
	@Override
	protected void onEvent(AjaxRequestTarget target) {} {
		
		super.onEvent(target);
		target.appendJavaScript("dijit.byId('" + dojoTooltipDialog.getMarkupId() + "').resize()");
		
		
	}
	
}
