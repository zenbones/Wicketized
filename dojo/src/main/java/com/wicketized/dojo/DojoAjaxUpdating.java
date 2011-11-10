package com.wicketized.dojo;

import org.apache.wicket.ajax.AjaxRequestTarget;

public interface DojoAjaxUpdating {

  public abstract String getCallbackPreScript ();

  public abstract String getCallbackPostScript ();

  public abstract void onUpdate (AjaxRequestTarget target);
}
