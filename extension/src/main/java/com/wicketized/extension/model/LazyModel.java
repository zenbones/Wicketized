package com.wicketized.extension.model;

import java.io.Serializable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;
import org.apache.wicket.model.Model;

public class LazyModel<T extends Serializable> implements IWrapModel<T> {

  private IModel<T> wrappedModel;

  public LazyModel () {

  }

  public LazyModel (T object) {

    if (object != null) {
      setObject(object);
    }
  }

  @Override
  public synchronized IModel<?> getWrappedModel () {

    return wrappedModel;
  }

  public synchronized IModel<T> setWrappedModel (IModel<T> wrappedModel) {

    this.wrappedModel = wrappedModel;

    return wrappedModel;
  }

  @Override
  public synchronized T getObject () {

    return (wrappedModel == null) ? null : wrappedModel.getObject();
  }

  @Override
  public synchronized void setObject (T object) {

    if (wrappedModel == null) {
      wrappedModel = new Model<T>();
    }

    wrappedModel.setObject(object);
  }

  @Override
  public synchronized void detach () {

    if (wrappedModel != null) {
      wrappedModel.detach();
    }
  }
}
