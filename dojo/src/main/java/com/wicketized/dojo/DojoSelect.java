package com.wicketized.dojo;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.request.cycle.RequestCycle;

public class DojoSelect extends DojoFormBorder<DojoSelect> implements RenderBodyOnly {

  private String value;

  public DojoSelect (String id) {

    super(id, false);

    add(new DojoUpdateOnRenderAjaxBehavior("value"));
  }

  @Override
  public String getDojoType () {

    return "dijit.form.Select";
  }

  @Override
  public Class<DojoSelect> getDojoComponentClass () {

    return DojoSelect.class;
  }

  @Override
  public String getCallbackPreScript () {

    return "";
  }

  @Override
  public String getCallbackPostScript () {

    return "&" + getMarkupId() + ".value='+ dijit.byId('" + getMarkupId() + "').value + '";
  }

  @Override
  public void onUpdate (AjaxRequestTarget target) {

    setValue(RequestCycle.get().getRequest().getQueryParameters().getParameterValue(getMarkupId() + ".value").toString());
  }

  @Override
  protected void onComponentTag (final ComponentTag tag) {

    checkComponentTag(tag, "select");
    tag.setName("div");

    super.onComponentTag(tag);
  }

  private synchronized void setValue (String value) {

    this.value = value;

    for (Component child : visitChildren(DojoSelectOptions.class)) {
      ((DojoSelectOptions)child).setValue(value);
    }
  }

  public synchronized String getValue () {

    return value;
  }
}
