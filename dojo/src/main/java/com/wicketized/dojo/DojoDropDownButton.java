package com.wicketized.dojo;

import com.wicketized.extension.model.LazyModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;

public class DojoDropDownButton extends DojoBorder<DojoDropDownButton> implements RenderBodyOnly, DojoAjaxUpdating, DojoDropDownContainer {

  private final LazyModel<String> iconClassModel = new LazyModel<String>();

  public DojoDropDownButton (String id) {

    this(id, null);
  }

  public DojoDropDownButton (String id, IModel<?> model) {

    super(id, model, false);

    add(new AttributeModifier("iconClass", iconClassModel));
  }

  @Override
  public String getDojoType () {

    return "dijit.form.DropDownButton";
  }

  @Override
  public Class<DojoDropDownButton> getDojoComponentClass () {

    return DojoDropDownButton.class;
  }

  @Override
  public String getCallbackPreScript () {

    return "";
  }

  @Override
  public String getCallbackPostScript () {

    return "";
  }

  @Override
  public void onUpdate (AjaxRequestTarget target) {

  }

  @Override
  public void applyIntermediateChangeBehavior () {

  }

  @Override
  public void removeIntermediateChangeBehavior () {

  }

  @Override
  protected void onComponentTag (final ComponentTag tag) {

    checkComponentTag(tag, "button");

    if (AjaxRequestTarget.get() == null) {
      if (tag.getAttribute("iconClass") != null) {
        iconClassModel.setObject(tag.getAttribute("iconClass"));
      }
    }

    super.onComponentTag(tag);
  }

  public String getIconClass () {

    return iconClassModel.getObject();
  }

  public DojoDropDownButton setIconClass (String iconClass) {

    iconClassModel.setObject(iconClass);

    return this;
  }

  public DojoDropDownButton setIconClassModel (IModel<String> iconClassModel) {

    this.iconClassModel.setWrappedModel(iconClassModel);

    return this;
  }

  public synchronized void setText (String text) {

    setDefaultModelObject(text);
  }
}
