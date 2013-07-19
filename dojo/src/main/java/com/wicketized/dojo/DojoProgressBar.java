package com.wicketized.dojo;

import com.wicketized.extension.model.LazyModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

public class DojoProgressBar extends DojoFormMarkup<DojoProgressBar> {

  public static final String INFINITY = "Infinity";

  private final LazyModel<Integer> placesModel = new LazyModel<Integer>(0);
  private final LazyModel<Integer> maximumModel = new LazyModel<Integer>(0);
  private final LazyModel<String> valueModel = new LazyModel<String>();

  public DojoProgressBar (String id) {

    super(id);

    add(new AttributeModifier("places", placesModel));
    add(new AttributeModifier("maximum", maximumModel));
    add(new AttributeModifier("value", valueModel));
  }

  @Override
  public String getDojoType () {

    return "dijit.ProgressBar";
  }

  @Override
  public Class<DojoProgressBar> getDojoComponentClass () {

    return DojoProgressBar.class;
  }

  @Override
  public String getCallbackPreScript () {

    return "";
  }

  @Override
  public String getCallbackPostScript () {

    return "&" + getMarkupId() + ".value='+ dijit.byId('" + getMarkupId() + "').value + '";
  }

  @Override
  public void onUpdate (AjaxRequestTarget target) {

    setValue(RequestCycle.get().getRequest().getQueryParameters().getParameterValue(getMarkupId() + ".value").toString());
  }

  @Override
  protected void onComponentTag (final ComponentTag tag) {

    checkComponentTag(tag, "div");

    if (RequestCycle.get().find(AjaxRequestTarget.class) == null) {
      if (tag.getAttribute("places") != null) {
        placesModel.setObject(Integer.parseInt(tag.getAttribute("places")));
      }
      if (tag.getAttribute("maximum") != null) {
        maximumModel.setObject(Integer.parseInt(tag.getAttribute("maximum")));
      }
    }

    super.onComponentTag(tag);
  }

  public synchronized int getPlaces () {

    return placesModel.getObject();
  }

  public synchronized DojoProgressBar setPlaces (int places) {

    placesModel.setObject(places);

    return this;
  }

  public synchronized DojoProgressBar setPlacesModel (IModel<Integer> placesModel) {

    this.placesModel.setWrappedModel(placesModel);

    return this;
  }

  public synchronized int getMaximum () {

    return maximumModel.getObject();
  }

  public synchronized DojoProgressBar setMaximum (int maximum) {

    maximumModel.setObject(maximum);

    return this;
  }

  public synchronized DojoProgressBar setMaximumModel (IModel<Integer> maximumModel) {

    this.maximumModel.setWrappedModel(maximumModel);

    return this;
  }

  public void setMaximum (int maximum, AjaxRequestTarget target) {

    maximumModel.setObject(maximum);
    target.appendJavaScript("dijit.byId('" + getMarkupId() + "').set('maximum','" + maximum + "')");
  }

  public synchronized String getValue () {

    return valueModel.getObject();
  }

  public synchronized DojoProgressBar setValue (String value) {

    valueModel.setObject(value);

    return this;
  }

  public synchronized DojoProgressBar setValueModel (IModel<String> valueModel) {

    this.valueModel.setWrappedModel(valueModel);

    return this;
  }

  public void setValue (String value, AjaxRequestTarget target) {

    valueModel.setObject(value);
    target.appendJavaScript("dijit.byId('" + getMarkupId() + "').set('value','" + value + "')");
  }
}
