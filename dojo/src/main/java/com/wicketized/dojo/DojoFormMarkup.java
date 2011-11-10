package com.wicketized.dojo;

import org.apache.wicket.model.IModel;

public abstract class DojoFormMarkup<D extends DojoMarkup<D>> extends DojoMarkup<D> implements IntermediateChangesAttribute, DojoAjaxUpdating {

  public DojoFormMarkup (String id) {

    super(id);
  }

  public DojoFormMarkup (String id, IModel<?> model) {

    super(id, model);
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
