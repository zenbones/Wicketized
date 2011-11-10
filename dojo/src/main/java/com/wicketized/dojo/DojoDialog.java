package com.wicketized.dojo;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

public class DojoDialog extends DojoPane<DojoDialog> {

  public DojoDialog (String id) {

    super(id);
  }

  @Override
  public String getDojoType () {

    return "dijit.Dialog";
  }

  @Override
  public Class<DojoDialog> getDojoComponentClass () {

    return DojoDialog.class;
  }

  public synchronized DojoDialog setClosable (boolean closable) {

    return (DojoDialog)super.setClosable(closable);
  }

  public synchronized DojoDialog setClosableModel (IModel<Boolean> closableModel) {

    return (DojoDialog)super.setClosableModel(closableModel);
  }

  public DojoDialog setTitle (String title) {

    return (DojoDialog)super.setTitle(title);
  }

  public DojoDialog setTitleModel (IModel<String> titleModel) {

    return (DojoDialog)super.setTitleModel(titleModel);
  }

  public void show (AjaxRequestTarget target) {

    target.appendJavaScript("dijit.byId('" + getMarkupId() + "').show()");
  }

  public void hide (AjaxRequestTarget target) {

    target.prependJavaScript("dijit.byId('" + getMarkupId() + "').hide()");
  }
}
