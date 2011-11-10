package com.wicketized.dojo;

import com.wicketized.extension.model.LazyModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;

public class DojoCheckBox extends DojoFormMarkup<DojoCheckBox> {

  private final LazyModel<Boolean> checkedModel = new LazyModel<Boolean>(false);
  private final CheckedConversionModel checkedConversionModel;

  public DojoCheckBox (String id) {

    this(id, null);
  }

  public DojoCheckBox (String id, IModel<String> model) {

    super(id, (model == null) ? new Model<String>("true") : model);

    add(new AttributeModifier("checked", checkedConversionModel = new CheckedConversionModel(checkedModel)));
    add(new AttributeModifier("value", new DojoDefaultModel<String>(this, String.class)));
  }

  @Override
  public String getDojoType () {

    return "dijit.form.CheckBox";
  }

  @Override
  public Class<DojoCheckBox> getDojoComponentClass () {

    return DojoCheckBox.class;
  }

  @Override
  public String getCallbackPreScript () {

    return "";
  }

  @Override
  public String getCallbackPostScript () {

    return "&" + getMarkupId() + ".value='+ dijit.byId('" + getMarkupId() + "').get('value') + '";
  }

  @Override
  public void onUpdate (AjaxRequestTarget target) {

    setValue(RequestCycle.get().getRequest().getQueryParameters().getParameterValue(getMarkupId() + ".value").toString());
  }

  @Override
  protected void onComponentTag (final ComponentTag tag) {

    checkComponentTag(tag, "input");
    checkComponentTagAttribute(tag, "type", "checkbox");

    if (AjaxRequestTarget.get() == null) {
      if (tag.getAttribute("checked") != null) {
        checkedModel.setObject(true);
      }
      if (tag.getAttribute("value") != null) {
        setValue(tag.getAttribute("value"));
      }
    }

    super.onComponentTag(tag);
  }

  public synchronized boolean isChecked () {

    return checkedModel.getObject();
  }

  public synchronized DojoCheckBox setChecked (boolean checked) {

    checkedModel.setObject(checked);

    return this;
  }

  public synchronized DojoCheckBox setCheckedModel (IModel<Boolean> checkedModel) {

    checkedConversionModel.setBooleanModel(this.checkedModel.setWrappedModel(checkedModel));

    return this;
  }

  public void setChecked (boolean checked, AjaxRequestTarget target) {

    checkedModel.setObject(checked);
    target.appendJavaScript("dijit.byId('" + getMarkupId() + "').set('checked','" + (checked ? "true" : "false") + "')");
  }

  public synchronized String getValue () {

    Object object;

    return (!isChecked()) ? "false" : ((object = getDefaultModelObject()) == null) ? null : object.toString();
  }

  public synchronized DojoCheckBox setValue (String value) {

    if ("false".equals(value)) {
      setChecked(false);
    }
    else {
      setChecked(true);
      setDefaultModelObject(value);
    }

    return this;
  }
}
