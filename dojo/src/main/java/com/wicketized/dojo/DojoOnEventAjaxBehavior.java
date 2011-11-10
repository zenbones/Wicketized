package com.wicketized.dojo;

import org.apache.wicket.ajax.AjaxRequestTarget;

public class DojoOnEventAjaxBehavior extends AbstractDojoOnEventAjaxBehavior {

  public DojoOnEventAjaxBehavior (String event) {

    super(event);
  }

  @Override
  protected void onEvent (AjaxRequestTarget target) {

    ((DojoAjaxUpdating)getComponent()).onUpdate(target);
  }
}
