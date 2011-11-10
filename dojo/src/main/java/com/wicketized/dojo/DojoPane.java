package com.wicketized.dojo;

import com.wicketized.extension.model.LazyModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;

public abstract class DojoPane<D extends DojoPane<D>> extends DojoBorder<D> implements RenderBodyOnly {

  private final LazyModel<Boolean> closableModel = new LazyModel<Boolean>(false);
  private final LazyModel<String> titleModel = new LazyModel<String>();

  public DojoPane (String id) {

    super(id, false);

    add(new AttributeModifier("closable", closableModel));
    add(new AttributeModifier("title", titleModel));
  }

  @Override
  public void removeIntermediateChangeBehavior () {

  }

  @Override
  public void applyIntermediateChangeBehavior () {

  }

  @Override
  protected void onComponentTag (final ComponentTag tag) {

    checkComponentTag(tag, "div");

    if (AjaxRequestTarget.get() == null) {
      if (tag.getAttribute("closable") != null) {
        closableModel.setObject(Boolean.parseBoolean(tag.getAttribute("closable")));
      }
      if (tag.getAttribute("title") != null) {
        titleModel.setObject(tag.getAttribute("title"));
      }
    }

    super.onComponentTag(tag);
  }

  @Override
  protected void onBeforeRender () {

    AjaxRequestTarget target;

    super.onBeforeRender();

    if ((this instanceof DojoLayoutContainer) && ((target = AjaxRequestTarget.get()) != null)) {
      target.appendJavaScript("dijit.byId('" + getMarkupId() + "').startup();dijit.byId('" + getMarkupId() + "').resize()");
    }
  }

  public synchronized boolean isClosable () {

    return closableModel.getObject();
  }

  public synchronized D setClosable (boolean closable) {

    closableModel.setObject(closable);

    return getDojoComponentClass().cast(this);
  }

  public synchronized D setClosableModel (IModel<Boolean> closableModel) {

    this.closableModel.setWrappedModel(closableModel);

    return getDojoComponentClass().cast(this);
  }

  public String getTitle () {

    return titleModel.getObject();
  }

  public D setTitle (String title) {

    titleModel.setObject(title);

    return getDojoComponentClass().cast(this);
  }

  public D setTitleModel (IModel<String> titleModel) {

    this.titleModel.setWrappedModel(titleModel);

    return getDojoComponentClass().cast(this);
  }

  public void setTitle (String title, AjaxRequestTarget target) {

    setTitle(title);
    target.appendJavaScript("dijit.byId('" + getMarkupId() + "').set('title','" + title + "')");
  }
}
