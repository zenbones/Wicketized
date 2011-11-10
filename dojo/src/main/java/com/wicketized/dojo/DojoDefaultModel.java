package com.wicketized.dojo;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

public class DojoDefaultModel<T> implements IModel<T> {

  private Component component;
  private Class<T> modelClass;

  public DojoDefaultModel (Component component, Class<T> modelClass) {

    this.component = component;
    this.modelClass = modelClass;
  }

  @Override
  public T getObject () {

    return modelClass.cast(component.getDefaultModelObject());
  }

  @Override
  public void setObject (T object) {

    component.setDefaultModelObject(object);
  }

  @Override
  public void detach () {

    component.getDefaultModel().detach();
  }
}
