package com.wicketized.dojo;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;

public class DojoUpdateOnRenderAjaxBehavior extends AbstractDojoAjaxBehavior {

  private String purpose;

  public DojoUpdateOnRenderAjaxBehavior (String purpose) {

    this.purpose = purpose;
  }

  @Override
  public boolean getStatelessHint (Component component) {

    return false;
  }

  @Override
  public void renderHead (Component component, IHeaderResponse response) {

    super.renderHead(component, response);

    if (AjaxRequestTarget.get() == null) {

      StringBuilder javascriptBuilder;
      StringBuilder idBuilder;

      javascriptBuilder = new StringBuilder("dojo.addOnLoad(function() {").append(getCallbackScript()).append("})");
      idBuilder = new StringBuilder(DojoUpdateOnRenderAjaxBehavior.class.getName()).append(".ajax.").append(component.getMarkupId()).append('.').append(purpose);

      response.renderJavaScript(javascriptBuilder.toString(), idBuilder.toString());
    }
  }

  @Override
  public void beforeRender (Component component) {

    AjaxRequestTarget target;

    super.beforeRender(component);

    if ((target = AjaxRequestTarget.get()) != null) {
      target.appendJavaScript(getCallbackScript());
    }
  }

  @Override
  protected void respond (AjaxRequestTarget target) {

    ((DojoAjaxUpdating)getComponent()).onUpdate(target);
  }
}
