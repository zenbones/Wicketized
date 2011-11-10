package com.wicketized.dojo;

import org.apache.wicket.model.AbstractReadOnlyModel;

public class DojoTypeModel extends AbstractReadOnlyModel<String> {

  private DojoTyped dojoTyped;

  public DojoTypeModel (DojoTyped dojoTyped) {

    this.dojoTyped = dojoTyped;
  }

  @Override
  public String getObject () {

    return dojoTyped.getDojoType();
  }
}
