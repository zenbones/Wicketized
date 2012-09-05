package com.wicketized.dojo;

import java.util.concurrent.atomic.AtomicReference;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;

public abstract class DojoLazyLoadPane extends DojoMarkup<DojoLazyLoadPane> implements RenderBodyOnly {

  public static final String LAZY_LOAD_COMPONENT_ID = "content";

  private static enum State {PRE_RENDER, LOADING, COMPLETED}

  private AtomicReference<State> stateRef = new AtomicReference<State>(State.PRE_RENDER);

  public DojoLazyLoadPane (final String id) {

    this(id, null);
  }

  public DojoLazyLoadPane (final String id, final IModel<?> model) {

    super(id, model);

    add(new AbstractDefaultAjaxBehavior() {

      @Override
      protected void respond (final AjaxRequestTarget target) {

        if (stateRef.compareAndSet(State.PRE_RENDER, State.COMPLETED) | stateRef.compareAndSet(State.LOADING, State.COMPLETED)) {
          DojoLazyLoadPane.this.getPage().dirty();
          DojoLazyLoadPane.this.replace(getLazyLoadComponent(LAZY_LOAD_COMPONENT_ID));
        }

        target.add(DojoLazyLoadPane.this);
      }

      @Override
      public void renderHead (final Component component, final IHeaderResponse response) {

        super.renderHead(component, response);

        if (!stateRef.equals(State.COMPLETED)) {
          handleCallbackScript(response, getCallbackScript().toString());
        }
      }
    });
  }

  public abstract Component getLazyLoadComponent (String markupId);

  protected void handleCallbackScript (final IHeaderResponse response, final String callbackScript) {

    response.renderOnDomReadyJavaScript(callbackScript);
  }

  @Override
  public String getDojoType () {

    return "dijit.layout.ContentPane";
  }

  @Override
  public Class<DojoLazyLoadPane> getDojoComponentClass () {

    return DojoLazyLoadPane.class;
  }

  @Override
  public void applyIntermediateChangeBehavior () {

  }

  @Override
  public void removeIntermediateChangeBehavior () {

  }

  @Override
  protected void onBeforeRender () {

    if (stateRef.compareAndSet(State.PRE_RENDER, State.LOADING)) {
      getPage().dirty();
      add(getLoadingComponent(LAZY_LOAD_COMPONENT_ID));
    }

    super.onBeforeRender();
  }

  @Override
  protected void onComponentTag (final ComponentTag tag) {

    checkComponentTag(tag, "div");

    super.onComponentTag(tag);
  }

  public Component getLoadingComponent (final String markupId) {

    IRequestHandler handler = new ResourceReferenceRequestHandler(AbstractDefaultAjaxBehavior.INDICATOR);

    return new Label(markupId, "<img alt=\"Loading...\" src=\"" + RequestCycle.get().urlFor(handler) + "\"/>").setEscapeModelStrings(false);
  }
}
