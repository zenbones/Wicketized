package com.wicketized.dojo;

import org.apache.wicket.ajax.AjaxRequestTarget;

public class DojoConstructionManager {

  private static final ThreadLocal<String> MARKUP_ID_THREAD_LOCAL = new ThreadLocal<String>();

  public static <D extends DojoComponent> void mark (D dojoComponent, AjaxRequestTarget target) {

    if (MARKUP_ID_THREAD_LOCAL.get() == null) {
      MARKUP_ID_THREAD_LOCAL.set(dojoComponent.getMarkupId());

      StringBuilder prependBuilder;
      StringBuilder appendBuilder;

      prependBuilder = new StringBuilder("var outerDijit = dijit.byId('").append(dojoComponent.getMarkupId()).append("');")
        .append("if (outerDijit) {outerDijit.destroyDescendants();outerDijit.destroyRecursive();")
        .append("dojo.byId('").append(dojoComponent.getDijitContainerMarkupId()).append("').id='").append(dojoComponent.getMarkupId()).append("'}");

      appendBuilder = new StringBuilder("dojo.parser.parse(dojo.byId('").append(dojoComponent.getDijitContainerMarkupId()).append("'));");

      target.prependJavaScript(prependBuilder);
      target.appendJavaScript(appendBuilder);
    }
  }

  public static <D extends DojoComponent> void release (D dojoComponent) {

    if (dojoComponent.getMarkupId().equals(MARKUP_ID_THREAD_LOCAL.get())) {
      MARKUP_ID_THREAD_LOCAL.remove();
    }
  }
}
