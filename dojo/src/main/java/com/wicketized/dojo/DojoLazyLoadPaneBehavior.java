package com.wicketized.dojo;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

public class DojoLazyLoadPaneBehavior extends DojoOnEventAjaxBehavior {

  private DojoLazyLoadPane dojoLazyLoadPane;
  private boolean completed = false;

  public DojoLazyLoadPaneBehavior (DojoLazyLoadPane dojoLazyLoadPane, String event) {

    super(event);

    this.dojoLazyLoadPane = dojoLazyLoadPane;
  }

  @Override
  protected void onEvent (AjaxRequestTarget target) {

    dojoLazyLoadPane.loadPanel(target);
    completed = true;
  }

  @Override
  public boolean isTemporary (Component component) {

    return completed;
  }
}
