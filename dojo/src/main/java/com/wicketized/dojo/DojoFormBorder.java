package com.wicketized.dojo;

import org.apache.wicket.model.IModel;

public abstract class DojoFormBorder<D extends DojoBorder<D>> extends DojoBorder<D> implements IntermediateChangesAttribute, DojoAjaxUpdating {

  public DojoFormBorder (String id, boolean bodyModel) {

    super(id, bodyModel);
  }

  public DojoFormBorder (String id, IModel<?> model, boolean bodyModel) {

    super(id, model, bodyModel);
  }

  @Override
  public synchronized void applyIntermediateChangeBehavior () {

    boolean found = false;

    for (DojoOnEventAjaxBehavior behavior : getBehaviors(DojoOnEventAjaxBehavior.class)) {
      if ("onchange".equalsIgnoreCase(behavior.getEvent())) {
        found = true;
        break;
      }
    }

    if (!found) {
      add(new DojoOnEventAjaxBehavior("onchange"));
    }
  }

  @Override
  public synchronized void removeIntermediateChangeBehavior () {

    for (DojoOnEventAjaxBehavior behavior : getBehaviors(DojoOnEventAjaxBehavior.class)) {
      if (behavior.getClass().equals(DojoOnEventAjaxBehavior.class) && "onchange".equalsIgnoreCase(behavior.getEvent())) {
        remove(behavior);
      }
    }
  }
}
