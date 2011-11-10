package com.wicketized.dojo;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;

public abstract class AbstractDojoAjaxBehavior extends AbstractDefaultAjaxBehavior {

  @Override
  protected CharSequence getCallbackScript () {

    String prescript;

    return (((prescript = ((DojoAjaxUpdating)getComponent()).getCallbackPreScript()) == null) ? "" : prescript) + super.getCallbackScript().toString();
  }

  @Override
  public CharSequence getCallbackUrl () {

    String postscript;

    return super.getCallbackUrl() + (((postscript = ((DojoAjaxUpdating)getComponent()).getCallbackPostScript()) == null) ? "" : postscript);
  }

  @Override
  public void onBind () {

    if (!(getComponent() instanceof DojoAjaxUpdating)) {
      throw new WicketRuntimeException("Component[id=" + getComponent().getId() + "] should be marked " + DojoAjaxUpdating.class.getSimpleName());
    }

    super.onBind();
  }
}
