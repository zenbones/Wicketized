package com.wicketized.dojo;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.joda.time.LocalTime;

public class DojoTimeTextBox extends DojoFormMarkup<DojoTimeTextBox> {

  private final TimeConversionModel timeConversionModel;

  public DojoTimeTextBox (String id) {

    this(id, null);
  }

  public DojoTimeTextBox (String id, IModel<LocalTime> model) {

    super(id, (model == null) ? new Model<LocalTime>() : model);

    add(new AttributeModifier("value", timeConversionModel = new TimeConversionModel(new DojoDefaultModel<LocalTime>(this, LocalTime.class))));
  }

  @Override
  public String getDojoType () {

    return "dijit.form.TimeTextBox";
  }

  @Override
  public Class<DojoTimeTextBox> getDojoComponentClass () {

    return DojoTimeTextBox.class;
  }

  @Override
  public String getCallbackPreScript () {

    return "";
  }

  @Override
  public String getCallbackPostScript () {

    return "&" + getMarkupId() + ".value='+ dijit.byId('" + getMarkupId() + "').value.getTime() + '";
  }

  @Override
  public void onUpdate (AjaxRequestTarget target) {

    setValue(TimeConversionModel.parseMilliseconds(RequestCycle.get().getRequest().getQueryParameters().getParameterValue(getMarkupId() + ".value").toString()));
  }

  @Override
  protected void onComponentTag (final ComponentTag tag) {

    checkComponentTag(tag, "input");
    checkComponentTagAttribute(tag, "type", "text");

    if (RequestCycle.get().find(AjaxRequestTarget.class) == null) {
      if (tag.getAttribute("value") != null) {
        setValue(TimeConversionModel.parseTime(tag.getAttribute("value")));
      }
    }

    super.onComponentTag(tag);
  }

  @Override
  public MarkupContainer setDefaultModel (IModel<?> model) {

    return super.setDefaultModel(model);
  }

  public synchronized LocalTime getValue () {

    return (LocalTime)getDefaultModelObject();
  }

  public synchronized DojoTimeTextBox setValue (LocalTime value) {

    setDefaultModelObject(value);

    return this;
  }

  public void setValue (LocalTime value, AjaxRequestTarget target) {

    setValue(value);
    target.appendJavaScript("dijit.byId('" + getMarkupId() + "').set('value','" + TimeConversionModel.print(value) + "')");
  }

  public synchronized void clear () {

    setValue(null);
  }

  public synchronized boolean isEmpty () {

    return getValue() == null;
  }
}
