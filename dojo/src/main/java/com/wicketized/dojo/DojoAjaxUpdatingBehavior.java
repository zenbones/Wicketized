package com.wicketized.dojo;

import java.util.UUID;
import org.apache.wicket.ajax.AjaxRequestTarget;

public abstract class DojoAjaxUpdatingBehavior extends DojoOnEventAjaxBehavior {

  private String uniqueId;

  public DojoAjaxUpdatingBehavior (String event) {

    super(event);

    uniqueId = UUID.randomUUID().toString();
  }

  public abstract void onUpdate (AjaxRequestTarget target);

  public String getUniqueId () {

    return uniqueId;
  }

  @Override
  protected final void onEvent (AjaxRequestTarget target) {

    super.onEvent(target);
    onUpdate(target);
  }

  @Override
  public int hashCode () {

    return uniqueId.hashCode();
  }

  @Override
  public boolean equals (Object obj) {

    return (obj instanceof DojoAjaxUpdatingBehavior) && ((DojoAjaxUpdatingBehavior)obj).getUniqueId().equals(uniqueId);
  }
}
