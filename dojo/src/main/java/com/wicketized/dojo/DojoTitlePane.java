package com.wicketized.dojo;

import org.apache.wicket.model.IModel;

public class DojoTitlePane extends DojoPane<DojoTitlePane> {

  public DojoTitlePane (String id) {

    super(id);
  }

  @Override
  public String getDojoType () {

    return "dijit.TitlePane";
  }

  @Override
  public Class<DojoTitlePane> getDojoComponentClass () {

    return DojoTitlePane.class;
  }

  public synchronized DojoTitlePane setClosable (boolean closable) {

    return (DojoTitlePane)super.setClosable(closable);
  }

  public synchronized DojoTitlePane setClosableModel (IModel<Boolean> closableModel) {

    return (DojoTitlePane)super.setClosableModel(closableModel);
  }

  public DojoTitlePane setTitle (String title) {

    return (DojoTitlePane)super.setTitle(title);
  }

  public DojoTitlePane setTitleModel (IModel<String> titleModel) {

    return (DojoTitlePane)super.setTitleModel(titleModel);
  }
}
