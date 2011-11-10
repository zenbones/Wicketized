package com.wicketized.extension.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.resource.IResourceStream;

public abstract class AjaxDownloadBehavior extends AbstractAjaxBehavior {

  public void initiate (AjaxRequestTarget target) {

    target.appendJavaScript(("window.location.href='" + getCallbackUrl() + "'"));
  }

  protected abstract IResourceStream getResourceStream ();

  public void onRequest () {

    getComponent().getRequestCycle().scheduleRequestHandlerAfterCurrent(new ResourceStreamRequestHandler(getResourceStream(), getFileName()));
  }

  protected String getFileName () {

    return null;
  }
}
