package com.wicketized.dojo;

import com.wicketized.extension.model.LazyModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;

public class DojoButton extends DojoBorder<DojoButton> implements RenderBodyOnly, DojoAjaxUpdating {

  private final LazyModel<String> iconClassModel = new LazyModel<String>();

  public DojoButton (String id) {

    this(id, null);
  }

  public DojoButton (String id, IModel<String> model) {

    super(id, model, true);

    add(new AttributeModifier("iconClass", iconClassModel));
  }

  @Override
  public String getDojoType () {

    return "dijit.form.Button";
  }

  @Override
  public Class<DojoButton> getDojoComponentClass () {

    return DojoButton.class;
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

  public DojoButton setIconClass (String iconClass) {

    iconClassModel.setObject(iconClass);

    return this;
  }

  public DojoButton setIconClassModel (IModel<String> iconClassModel) {

    this.iconClassModel.setWrappedModel(iconClassModel);

    return this;
  }

  public synchronized DojoButton setText (String text) {

    setDefaultModelObject(text);

    return this;
  }
}
