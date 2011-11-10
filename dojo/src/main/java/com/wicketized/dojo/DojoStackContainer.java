package com.wicketized.dojo;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.cycle.RequestCycle;

public abstract class DojoStackContainer<D extends DojoStackContainer<D>> extends DojoPane<D> implements DojoLayoutContainer, DojoAjaxUpdating {

  private DojoContentPane selectedChild;
  private int selectedIndex;

  public DojoStackContainer (String id) {

    super(id);

    add(new DojoSubscribeOnRenderAjaxBehavior(getMarkupId() + "-selectChild"));
  }

  @Override
  public String getCallbackPreScript () {

    return "";
  }

  @Override
  public String getCallbackPostScript () {

    return "&" + getMarkupId() + ".selectedChildId=' + pane.id + '&" + getMarkupId() + ".selectedChildIndex=' + dijit.byId('" + getMarkupId() + "').getIndexOfChild(pane) + '";
  }

  @Override
  public synchronized void onUpdate (AjaxRequestTarget target) {

    if ((selectedIndex = Integer.parseInt(RequestCycle.get().getRequest().getQueryParameters().getParameterValue(getMarkupId() + ".selectedChildIndex").toString())) < 0) {
      selectedChild = null;
    }
    else {

      String divId = RequestCycle.get().getRequest().getQueryParameters().getParameterValue(getMarkupId() + ".selectedChildId").toString();
      boolean found = false;

      for (Component childPane : visitChildren(DojoContentPane.class)) {
        if (childPane.getMarkupId().equals(divId)) {
          selectedChild = (DojoContentPane)childPane;
          found = true;
          break;
        }
      }

      if (!found) {
        throw new WicketRuntimeException("Unable to locate ContentPane with inner div id=" + divId);
      }
    }
  }

  public synchronized int getSelectedIndex () {

    return selectedIndex;
  }

  public synchronized DojoContentPane getSelectedChild () {

    return selectedChild;
  }
}
