package com.wicketized.dojo;

import com.wicketized.extension.model.LazyModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IModelComparator;
import org.apache.wicket.request.cycle.RequestCycle;

public abstract class AbstractDojoValidationTextBox<D extends AbstractDojoValidationTextBox<D>> extends AbstractDojoTextBox<D> {

  private static final IModelComparator MODEL_COMPARATOR = new IModelComparator() {

    @Override
    public boolean compare (Component component, Object newObject) {

      return ModelObjectComparator.isEqual(((AbstractDojoValidationTextBox)component).getCurrentValidationState(), ((AbstractDojoValidationTextBox)component).getPriorValidationState()) && ModelObjectComparator.isEqual(component.getDefaultModelObject(), newObject);
    }
  };

  private final LazyModel<String> constraintsModel = new LazyModel<String>();
  private final LazyModel<String> regExpModel = new LazyModel<String>();
  private final LazyModel<String> invalidMessageModel = new LazyModel<String>();

  private ValidationState validationState;
  private ValidationState priorValidationState;

  public AbstractDojoValidationTextBox (String id) {

    this(id, null, null);
  }

  public AbstractDojoValidationTextBox (String id, ValidationState validationState) {

    this(id, null, validationState);
  }

  public AbstractDojoValidationTextBox (String id, IModel<String> model) {

    this(id, model, null);
  }

  public AbstractDojoValidationTextBox (String id, IModel<String> model, ValidationState validationState) {

    super(id, model);

    this.validationState = validationState;
    this.priorValidationState = validationState;

    add(new AttributeModifier("constraints", constraintsModel));
    add(new AttributeModifier("regExp", regExpModel));
    add(new AttributeModifier("invalidMessage", invalidMessageModel));
  }

  @Override
  public String getDojoType () {

    return "dijit.form.ValidationTextBox";
  }

  @Override
  public String getCallbackPreScript () {

    return super.getCallbackPreScript() + "dijit.byId('" + getMarkupId() + "').validate(true);";
  }

  @Override
  public String getCallbackPostScript () {

    return super.getCallbackPostScript() + "&" + getMarkupId() + ".state='+ dijit.byId('" + getMarkupId() + "').state + '";
  }

  @Override
  public void onUpdate (AjaxRequestTarget target) {

    validationState = ValidationState.fromCode(RequestCycle.get().getRequest().getQueryParameters().getParameterValue(getMarkupId() + ".state").toString());

    super.onUpdate(target);

    priorValidationState = validationState;
  }

  @Override
  public IModelComparator getModelComparator () {

    return MODEL_COMPARATOR;
  }

  @Override
  protected void onComponentTag (final ComponentTag tag) {

    if (AjaxRequestTarget.get() == null) {
      if (tag.getAttribute("constraints") != null) {
        constraintsModel.setObject(tag.getAttribute("constraints"));
      }
      if (tag.getAttribute("regExp") != null) {
        regExpModel.setObject(tag.getAttribute("regExp"));
      }
      if (tag.getAttribute("invalidMessage") != null) {
        invalidMessageModel.setObject(tag.getAttribute("invalidMessage"));
      }
    }

    super.onComponentTag(tag);
  }

  private ValidationState getCurrentValidationState () {

    return validationState;
  }

  private ValidationState getPriorValidationState () {

    return priorValidationState;
  }

  public synchronized String getRegExp () {

    return regExpModel.getObject();
  }

  public synchronized D setRegExp (String regExp) {

    regExpModel.setObject(regExp);

    return getDojoComponentClass().cast(this);
  }

  public synchronized D setRegExpModel (IModel<String> regExpModel) {

    this.regExpModel.setWrappedModel(regExpModel);

    return getDojoComponentClass().cast(this);
  }

  public synchronized String getConstraints () {

    return constraintsModel.getObject();
  }

  public synchronized D setConstraints (String constraints) {

    constraintsModel.setObject(constraints);

    return getDojoComponentClass().cast(this);
  }

  public synchronized D setConstraintsModel (IModel<String> constraintsModel) {

    this.constraintsModel.setWrappedModel(constraintsModel);

    return getDojoComponentClass().cast(this);
  }

  public synchronized String getInvalidMessage () {

    return invalidMessageModel.getObject();
  }

  public synchronized D setInvalidMessage (String invalidMessage) {

    invalidMessageModel.setObject(invalidMessage);

    return getDojoComponentClass().cast(this);
  }

  public synchronized D setInvalidMessageModel (IModel<String> invalidMessageModel) {

    this.invalidMessageModel.setWrappedModel(invalidMessageModel);

    return getDojoComponentClass().cast(this);
  }

  public synchronized ValidationState getValidationState () {

    return validationState;
  }

  public synchronized boolean isValid () {

    return ValidationState.NORMAL.equals(getValidationState());
  }
}
