package com.wicketized.dojo;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

public interface DojoComponent<D extends DojoComponent<D>> extends DojoTyped {

  public abstract Class<D> getDojoComponentClass ();

  public abstract String[] getDojoRegistrations ();

  public abstract String getMarkupId ();

  public abstract String getDijitContainerMarkupId ();

  public abstract void applyIntermediateChangeBehavior ();

  public abstract void removeIntermediateChangeBehavior ();

  public abstract boolean isIntermediateChanges ();

  public abstract D setIntermediateChanges (boolean intermediateChanges);

  public abstract D setIntermediateChangesModel (IModel<Boolean> intermediateChangesModel);

  public abstract boolean isDisabled ();

  public abstract D setDisabled (boolean disabled);

  public abstract D setDisabledModel (IModel<Boolean> disabledModel);

  public abstract void setDisabled (boolean disabled, AjaxRequestTarget target);

}
