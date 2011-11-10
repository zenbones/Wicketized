package com.wicketized.dojo;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

// Marker interface for parent containers which hold 2 children, one of which is a drop down widget (allows more complete type checking)
public interface DojoDropDownContainer {

  public abstract IModel<?> getDefaultModel ();

  public abstract Component setDefaultModel (final IModel<?> model);

  public abstract Object getDefaultModelObject ();

  public abstract String getDefaultModelObjectAsString ();
}
