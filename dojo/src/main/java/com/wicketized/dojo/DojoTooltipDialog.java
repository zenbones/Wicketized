package com.wicketized.dojo;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.request.cycle.RequestCycle;

public class DojoTooltipDialog extends DojoMarkup<DojoTooltipDialog> implements DojoTyped {

  public DojoTooltipDialog (String id) {

    super(id);

    setOutputMarkupId(true);
  }

  @Override
  public String getDojoType () {

    return "dijit.TooltipDialog";
  }

  @Override
  public Class<DojoTooltipDialog> getDojoComponentClass () {

    return DojoTooltipDialog.class;
  }

  @Override
  public void applyIntermediateChangeBehavior () {

  }

  @Override
  public void removeIntermediateChangeBehavior () {

  }

  @Override
  protected void onComponentTag (final ComponentTag tag) {

    checkComponentTag(tag, "div");

    tag.put("dojoType", getDojoType());

    super.onComponentTag(tag);
  }

  @Override
  protected void onBeforeRender () {

    AjaxRequestTarget target;

    super.onBeforeRender();

    if ((this instanceof DojoLayoutContainer) && ((target = RequestCycle.get().find(AjaxRequestTarget.class)) != null)) {
      target.appendJavaScript("dijit.byId('" + getMarkupId() + "').startup();dijit.byId('" + getMarkupId() + "').resize()");
    }
  }

  public void hide (AjaxRequestTarget target) {

    target.prependJavaScript("dojo.byId('" + getMarkupId() + "').blur()");
  }
}
