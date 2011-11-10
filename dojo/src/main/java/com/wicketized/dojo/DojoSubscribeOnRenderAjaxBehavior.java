package com.wicketized.dojo;

import java.util.LinkedList;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;

public class DojoSubscribeOnRenderAjaxBehavior extends AbstractDojoAjaxBehavior {

  private LinkedList<DojoAjaxUpdatingBehavior> proxyBehaviorList;
  private String topic;

  public DojoSubscribeOnRenderAjaxBehavior (String topic) {

    this.topic = topic;

    proxyBehaviorList = new LinkedList<DojoAjaxUpdatingBehavior>();
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

      javascriptBuilder = new StringBuilder("dojo.addOnLoad(function() {dojo.subscribe('").append(topic).append("', function(pane){").append(getCallbackScript()).append("})})");
      idBuilder = new StringBuilder(DojoSubscribeOnRenderAjaxBehavior.class.getName()).append(".listener.").append(component.getMarkupId()).append('.').append(topic);

      response.renderJavaScript(javascriptBuilder.toString(), idBuilder.toString());
    }
  }

  public synchronized void addProxyBehavior (DojoAjaxUpdatingBehavior proxyBehavior) {

    proxyBehaviorList.add(proxyBehavior);
  }

  public synchronized void removeProxyBehavior (DojoAjaxUpdatingBehavior proxyBehavior) {

    proxyBehaviorList.remove(proxyBehavior);
  }

  @Override
  protected synchronized void respond (AjaxRequestTarget target) {

    ((DojoAjaxUpdating)getComponent()).onUpdate(target);

    for (DojoAjaxUpdatingBehavior proxyBehavior : proxyBehaviorList) {
      proxyBehavior.onUpdate(target);
    }
  }
}
