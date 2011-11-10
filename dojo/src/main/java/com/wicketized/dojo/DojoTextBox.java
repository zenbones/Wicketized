package com.wicketized.dojo;

import org.apache.wicket.model.IModel;

public class DojoTextBox extends AbstractDojoTextBox<DojoTextBox> {

  public DojoTextBox (String id) {

    super(id);
  }

  public DojoTextBox (String id, IModel<String> model) {

    super(id, model);
  }

  @Override
  public Class<DojoTextBox> getDojoComponentClass () {

    return DojoTextBox.class;
  }
}
