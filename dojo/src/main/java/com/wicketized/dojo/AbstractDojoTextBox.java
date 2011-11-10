package com.wicketized.dojo;

import com.wicketized.extension.model.LazyModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;

public abstract class AbstractDojoTextBox<D extends AbstractDojoTextBox<D>> extends DojoFormMarkup<D> {

  private final LazyModel<Boolean> trimModel = new LazyModel<Boolean>(false);

  public AbstractDojoTextBox (String id) {

    this(id, null);
  }

  public AbstractDojoTextBox (String id, IModel<String> model) {

    super(id, (model == null) ? new Model<String>() : model);

    add(new AttributeModifier("trim", trimModel));
    add(new AttributeModifier("value", new DojoDefaultModel<String>(this, String.class)));
  }

  @Override
  public String getDojoType () {

    return "dijit.form.TextBox";
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

    checkComponentTag(tag, "input");
    checkComponentTagAttribute(tag, "type", "text");

    if (AjaxRequestTarget.get() == null) {
      if (tag.getAttribute("trim") != null) {
        trimModel.setObject(Boolean.parseBoolean(tag.getAttribute("trim")));
      }
      if (tag.getAttribute("value") != null) {
        setValue(tag.getAttribute("value"));
      }
    }

    super.onComponentTag(tag);
  }

  public synchronized boolean isTrim () {

    return trimModel.getObject();
  }

  public synchronized D setTrim (boolean trim) {

    trimModel.setObject(trim);

    return getDojoComponentClass().cast(this);
  }

  public synchronized D setTrimModel (IModel<Boolean> trimModel) {

    this.trimModel.setWrappedModel(trimModel);

    return getDojoComponentClass().cast(this);
  }

  public D setDefaultModel (IModel<String> model) {

    return getDojoComponentClass().cast(super.setDefaultModel(model));
  }

  public synchronized String getValue () {

    Object object;

    return ((object = getDefaultModelObject()) == null) ? null : object.toString();
  }

  public synchronized D setValue (String value) {

    setDefaultModelObject(value);

    return getDojoComponentClass().cast(this);
  }

  public void setValue (String value, AjaxRequestTarget target) {

    setValue(value);
    target.appendJavaScript("dijit.byId('" + getMarkupId() + "').set('value','" + value + "')");
  }

  public synchronized void clear () {

    setValue(null);
  }

  public synchronized boolean isEmpty () {

    return (getValue() == null) || (getValue().length() == 0);
  }
}
