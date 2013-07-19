package com.wicketized.dojo;

import com.wicketized.extension.model.LazyModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

public class DojoTextArea extends DojoFormBorder<DojoTextArea> implements RenderBodyOnly {

  private final LazyModel<Boolean> trimModel = new LazyModel<Boolean>(false);

  public DojoTextArea (String id) {

    this(id, null);
  }

  public DojoTextArea (String id, IModel<?> model) {

    super(id, model, true);

    add(new AttributeModifier("trim", trimModel));
  }

  @Override
  public String getDojoType () {

    return "dijit.form.Textarea";
  }

  @Override
  public Class<DojoTextArea> getDojoComponentClass () {

    return DojoTextArea.class;
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

    checkComponentTag(tag, "textarea");

    if (RequestCycle.get().find(AjaxRequestTarget.class) == null) {
      if (tag.getAttribute("trim") != null) {
        trimModel.setObject(Boolean.parseBoolean(tag.getAttribute("trim")));
      }
    }

    super.onComponentTag(tag);
  }

  public synchronized boolean isTrim () {

    return trimModel.getObject();
  }

  public synchronized DojoTextArea setTrim (boolean trim) {

    trimModel.setObject(trim);

    return this;
  }

  public synchronized DojoTextArea setTrimModel (IModel<Boolean> trimModel) {

    this.trimModel.setWrappedModel(trimModel);

    return this;
  }

  public synchronized String getValue () {

    Object object;

    return ((object = getDefaultModelObject()) == null) ? null : object.toString();
  }

  public synchronized void setValue (String value) {

    setDefaultModelObject(value);
  }

  public synchronized void clear () {

    setValue(null);
  }

  public synchronized boolean isEmpty () {

    return (getValue() == null) || (getValue().length() == 0);
  }
}
