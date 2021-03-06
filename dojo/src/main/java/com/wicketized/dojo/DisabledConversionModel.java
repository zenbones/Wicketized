package com.wicketized.dojo;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

public class DisabledConversionModel extends AbstractReadOnlyModel<String> {

  private IModel<Boolean> booleanModel;

  public DisabledConversionModel (IModel<Boolean> booleanModel) {

    this.booleanModel = booleanModel;
  }

  public synchronized void setBooleanModel (IModel<Boolean> booleanModel) {

    this.booleanModel = booleanModel;
  }

  @Override
  public synchronized String getObject () {

    return booleanModel.getObject() ? "disabled" : null;
  }
}
