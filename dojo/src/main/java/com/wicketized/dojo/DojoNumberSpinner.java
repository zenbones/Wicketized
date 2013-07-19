package com.wicketized.dojo;

import com.wicketized.extension.model.LazyModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

public class DojoNumberSpinner extends AbstractDojoValidationTextBox<DojoNumberSpinner> {

  private final LazyModel<Integer> smallDeltaModel = new LazyModel<Integer>(1);
  private final LazyModel<Integer> largeDeltaModel = new LazyModel<Integer>(10);

  public DojoNumberSpinner (String id) {

    this(id, null, null);
  }

  public DojoNumberSpinner (String id, ValidationState validationState) {

    this(id, null, validationState);
  }

  public DojoNumberSpinner (String id, IModel<String> model) {

    this(id, model, null);
  }

  public DojoNumberSpinner (String id, IModel<String> model, ValidationState validationState) {

    super(id, model, validationState);

    add(new AttributeModifier("smallDelta", smallDeltaModel));
    add(new AttributeModifier("largeDelta", largeDeltaModel));
  }

  @Override
  public String getDojoType () {

    return "dijit.form.NumberSpinner";
  }

  @Override
  public Class<DojoNumberSpinner> getDojoComponentClass () {

    return DojoNumberSpinner.class;
  }

  public synchronized int getSmallDela () {

    return smallDeltaModel.getObject();
  }

  @Override
  protected void onComponentTag (final ComponentTag tag) {

    if (RequestCycle.get().find(AjaxRequestTarget.class) == null) {
      if (tag.getAttribute("smallDelta") != null) {
        smallDeltaModel.setObject(Integer.parseInt(tag.getAttribute("smallDelta")));
      }
      if (tag.getAttribute("largeDelta") != null) {
        largeDeltaModel.setObject(Integer.parseInt(tag.getAttribute("largeDelta")));
      }
    }

    super.onComponentTag(tag);
  }

  public synchronized DojoNumberSpinner setSmallDelta (int smallDelta) {

    smallDeltaModel.setObject(smallDelta);

    return this;
  }

  public synchronized DojoNumberSpinner setSmallDeltaModel (IModel<Integer> smallDeltaModel) {

    this.smallDeltaModel.setWrappedModel(smallDeltaModel);

    return this;
  }

  public synchronized int getLargeDela () {

    return largeDeltaModel.getObject();
  }

  public synchronized DojoNumberSpinner setLargeDelta (int largeDelta) {

    largeDeltaModel.setObject(largeDelta);

    return this;
  }

  public synchronized DojoNumberSpinner setLargeDeltaModel (IModel<Integer> largeDeltaModel) {

    this.largeDeltaModel.setWrappedModel(largeDeltaModel);

    return this;
  }

  @Override
  public boolean isValid () {

    if (super.isValid()) {
      try {
        Integer.parseInt(getValue());

        return true;
      }
      catch (NumberFormatException numberFormatException) {

        return false;
      }
    }

    return false;
  }
}
