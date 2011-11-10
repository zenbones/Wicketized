package com.wicketized.dojo;

import org.apache.wicket.model.IModel;

public class DojoValidationTextBox extends AbstractDojoValidationTextBox<DojoValidationTextBox> {

  public DojoValidationTextBox (String id) {

    super(id);
  }

  public DojoValidationTextBox (String id, ValidationState validationState) {

    super(id, validationState);
  }

  public DojoValidationTextBox (String id, IModel<String> model) {

    super(id, model);
  }

  public DojoValidationTextBox (String id, IModel<String> model, ValidationState validationState) {

    super(id, model, validationState);
  }

  @Override
  public Class<DojoValidationTextBox> getDojoComponentClass () {

    return DojoValidationTextBox.class;
  }
}
