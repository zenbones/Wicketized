package com.wicketized.dojo;

import com.wicketized.extension.model.LazyModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

public class DojoContentPane extends DojoPane<DojoContentPane> {

  private final LazyModel<Boolean> selectedModel = new LazyModel<Boolean>(false);

  public DojoContentPane (String id) {

    super(id);

    add(new AttributeModifier("selected", selectedModel));
  }

  @Override
  public String getDojoType () {

    return "dijit.layout.ContentPane";
  }

  @Override
  public Class<DojoContentPane> getDojoComponentClass () {

    return DojoContentPane.class;
  }

  @Override
  protected void onComponentTag (final ComponentTag tag) {

    if (RequestCycle.get().find(AjaxRequestTarget.class) == null) {
      if (tag.getAttribute("selected") != null) {
        selectedModel.setObject(Boolean.parseBoolean(tag.getAttribute("selected")));
      }
    }

    super.onComponentTag(tag);
  }

  public boolean isSelected () {

    return selectedModel.getObject();
  }

  public DojoContentPane setSelected (boolean selected) {

    selectedModel.setObject(selected);

    return this;
  }

  public DojoContentPane setSelectedModel (IModel<Boolean> selectedModel) {

    this.selectedModel.setWrappedModel(selectedModel);

    return this;
  }
}
