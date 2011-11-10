package com.wicketized.dojo;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.joda.time.DateMidnight;

public class DojoDateTextBox extends DojoFormMarkup<DojoDateTextBox> {

  private final DateConversionModel dateConversionModel;

  public DojoDateTextBox (String id) {

    this(id, null);
  }

  public DojoDateTextBox (String id, IModel<DateMidnight> model) {

    super(id, (model == null) ? new Model<DateMidnight>() : model);

    add(new AttributeModifier("value", dateConversionModel = new DateConversionModel(new DojoDefaultModel<DateMidnight>(this, DateMidnight.class))));
  }

  @Override
  public String getDojoType () {

    return "dijit.form.DateTextBox";
  }

  @Override
  public Class<DojoDateTextBox> getDojoComponentClass () {

    return DojoDateTextBox.class;
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

    setValue(DateConversionModel.parseMilliseconds(RequestCycle.get().getRequest().getQueryParameters().getParameterValue(getMarkupId() + ".value").toString()));
  }

  @Override
  protected void onComponentTag (final ComponentTag tag) {

    checkComponentTag(tag, "input");
    checkComponentTagAttribute(tag, "type", "text");

    if (AjaxRequestTarget.get() == null) {
      if (tag.getAttribute("value") != null) {
        setValue(DateConversionModel.parseDate(tag.getAttribute("value")));
      }
    }

    super.onComponentTag(tag);
  }

  public DojoDateTextBox setDefaultModel (IModel<DateMidnight> model) {

    return (DojoDateTextBox)super.setDefaultModel(model);
  }

  public synchronized DateMidnight getValue () {

    return (DateMidnight)getDefaultModelObject();
  }

  public synchronized DojoDateTextBox setValue (DateMidnight value) {

    setDefaultModelObject(value);

    return this;
  }

  public void setValue (DateMidnight value, AjaxRequestTarget target) {

    setValue(value);
    target.appendJavaScript("dijit.byId('" + getMarkupId() + "').set('value','" + DateConversionModel.print(value) + "')");
  }

  public synchronized boolean isEmpty () {

    return getValue() == null;
  }
}
