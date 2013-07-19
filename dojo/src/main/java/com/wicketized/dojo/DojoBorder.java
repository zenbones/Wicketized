package com.wicketized.dojo;

import com.wicketized.extension.model.LazyModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

public abstract class DojoBorder<D extends DojoBorder<D>> extends ModelAwareDojoBorder implements DojoComponent<D> {

  private final LazyModel<Boolean> intermediateChangesModel = new LazyModel<Boolean>(false);
  private final LazyModel<Boolean> disabledModel = new LazyModel<Boolean>(false);
  private final DisabledConversionModel disabledConversionModel;

  public DojoBorder (String id, boolean bodyModel) {

    this(id, null, bodyModel);
  }

  public DojoBorder (String id, IModel<?> model, boolean bodyModel) {

    super(id, model, bodyModel);

    setOutputMarkupId(true);

    add(new DojoContainerBehavior());

    add(new AttributeModifier("dojoType", new DojoTypeModel(this)));
    add(new AttributeModifier("disabled", disabledConversionModel = new DisabledConversionModel(disabledModel)));

    if (this instanceof IntermediateChangesAttribute) {
      add(new AttributeModifier("intermediateChanges", intermediateChangesModel));
    }
    if (this instanceof RenderBodyOnly) {
      getBodyContainer().setRenderBodyOnly(true);
    }
  }

  @Override
  public String[] getDojoRegistrations () {

    return null;
  }

  @Override
  public String getDijitContainerMarkupId () {

    return getMarkupId() + ".dijitContainer";
  }

  @Override
  public void renderHead (IHeaderResponse response) {

    super.renderHead(response);

    response.render(StringHeaderItem.forString(DojoHeaderAccumulator.getHeaderContribution()));
  }

  @Override
  public synchronized Component add (Behavior... behaviors) {

    removeIntermediateChangeBehavior();

    return super.add(behaviors);
  }

  @Override
  protected void onComponentTag (final ComponentTag tag) {

    if (RequestCycle.get().find(AjaxRequestTarget.class) == null) {
      if (tag.getAttribute("intermediateChanges") != null) {
        intermediateChangesModel.setObject(Boolean.parseBoolean(tag.getAttribute("intermediateChanges")));
      }
      if (tag.getAttribute("disabled") != null) {
        disabledModel.setObject(true);
      }
    }

    super.onComponentTag(tag);
  }

  @Override
  protected void onBeforeRender () {

    AjaxRequestTarget target;

    if (intermediateChangesModel.getObject()) {
      applyIntermediateChangeBehavior();
    }
    else {
      removeIntermediateChangeBehavior();
    }

    if ((target = RequestCycle.get().find(AjaxRequestTarget.class)) != null) {
      DojoConstructionManager.mark(this, target);
    }
    else {
      DojoHeaderAccumulator.register(this);
      DojoHeaderAccumulator.register(getDojoRegistrations());
    }

    super.onBeforeRender();

    if (target != null) {
      DojoConstructionManager.release(this);
    }
  }

  public synchronized boolean isIntermediateChanges () {

    return intermediateChangesModel.getObject();
  }

  public synchronized D setIntermediateChanges (boolean intermediateChanges) {

    intermediateChangesModel.setObject(intermediateChanges);

    return getDojoComponentClass().cast(this);
  }

  public synchronized D setIntermediateChangesModel (IModel<Boolean> intermediateChangesModel) {

    this.intermediateChangesModel.setWrappedModel(intermediateChangesModel);

    return getDojoComponentClass().cast(this);
  }

  public synchronized boolean isDisabled () {

    return disabledModel.getObject();
  }

  public synchronized D setDisabled (boolean disabled) {

    disabledModel.setObject(disabled);

    return getDojoComponentClass().cast(this);
  }

  public synchronized D setDisabledModel (IModel<Boolean> disabledModel) {

    disabledConversionModel.setBooleanModel(this.disabledModel.setWrappedModel(disabledModel));

    return getDojoComponentClass().cast(this);
  }

  public void setDisabled (boolean disabled, AjaxRequestTarget target) {

    disabledModel.setObject(disabled);
    target.appendJavaScript("dijit.byId('" + getMarkupId() + "').set('disabled','" + (disabled ? "disabled" : "") + "')");
  }
}