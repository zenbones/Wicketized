package com.wicketized.dojo;

import com.wicketized.extension.model.LazyModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

public class DojoVerticalSlider extends DojoFormBorder<DojoVerticalSlider> implements RenderBodyOnly {

  private static final String[] DOJO_REGISTRATIONS = new String[] {"dijit.form.Slider", "dijit.form.VerticalRule", "dijit.form.VerticalRuleLabels"};

  private final LazyModel<Boolean> showButtonsModel = new LazyModel<Boolean>(false);
  private final LazyModel<Double> valueModel = new LazyModel<Double>(0D);
  private final LazyModel<Integer> minimumModel = new LazyModel<Integer>(0);
  private final LazyModel<Integer> maximumModel = new LazyModel<Integer>(0);
  private final LazyModel<Integer> discreteValuesModel = new LazyModel<Integer>();
  private final LazyModel<Integer> countModel = new LazyModel<Integer>(0);

  public DojoVerticalSlider (String id) {

    super(id, false);

    WebMarkupContainer verticalRule;

    add(new AttributeModifier("showButtons", showButtonsModel));
    add(new AttributeModifier("value", valueModel));
    add(new AttributeModifier("minimum", minimumModel));
    add(new AttributeModifier("maximum", maximumModel));
    add(new AttributeModifier("discreteValues", discreteValuesModel));

    addToBorder(verticalRule = new WebMarkupContainer("verticalRule"));
    verticalRule.add(new AttributeModifier("count", countModel));
  }

  @Override
  public String getDojoType () {

    return "dijit.form.VerticalSlider";
  }

  @Override
  public String[] getDojoRegistrations () {

    return DOJO_REGISTRATIONS;
  }

  @Override
  public Class<DojoVerticalSlider> getDojoComponentClass () {

    return DojoVerticalSlider.class;
  }

  @Override
  public String getCallbackPreScript () {

    return "";
  }

  @Override
  public String getCallbackPostScript () {

    return "&" + getMarkupId() + ".value=' + dijit.byId('" + getMarkupId() + "').value + '";
  }

  @Override
  public void onUpdate (AjaxRequestTarget target) {

    setValue(RequestCycle.get().getRequest().getQueryParameters().getParameterValue(getMarkupId() + ".value").toDouble());
  }

  @Override
  protected void onComponentTag (ComponentTag tag) {

    checkComponentTag(tag, "ol");

    if (RequestCycle.get().find(AjaxRequestTarget.class) == null) {
      if (tag.getAttribute("showButtons") != null) {
        showButtonsModel.setObject(Boolean.parseBoolean(tag.getAttribute("showButtons")));
      }
      if (tag.getAttribute("value") != null) {
        valueModel.setObject(Double.parseDouble(tag.getAttribute("value")));
      }
      if (tag.getAttribute("minimum") != null) {
        minimumModel.setObject(Integer.parseInt(tag.getAttribute("minimum")));
      }
      if (tag.getAttribute("maximum") != null) {
        maximumModel.setObject(Integer.parseInt(tag.getAttribute("maximum")));
      }
      if (tag.getAttribute("discreteValues") != null) {
        discreteValuesModel.setObject(Integer.parseInt(tag.getAttribute("discreteValues")));
      }
    }

    tag.setName("div");

    super.onComponentTag(tag);
  }

  public synchronized boolean isShowButtons () {

    return showButtonsModel.getObject();
  }

  public synchronized DojoVerticalSlider setShowButtons (boolean showButtons) {

    showButtonsModel.setObject(showButtons);

    return this;
  }

  public synchronized DojoVerticalSlider setShowButtonsModel (IModel<Boolean> showButtonsModel) {

    this.showButtonsModel.setWrappedModel(showButtonsModel);

    return this;
  }

  public synchronized int getMinimum () {

    return minimumModel.getObject();
  }

  public synchronized DojoVerticalSlider setMinimum (int minimum) {

    minimumModel.setObject(minimum);

    return this;
  }

  public synchronized DojoVerticalSlider setMinimumModel (IModel<Integer> minimumModel) {

    this.minimumModel.setWrappedModel(minimumModel);

    return this;
  }

  public synchronized int getMaximum () {

    return maximumModel.getObject();
  }

  public synchronized DojoVerticalSlider setMaximum (int maximum) {

    maximumModel.setObject(maximum);

    return this;
  }

  public synchronized DojoVerticalSlider setMaximumModel (IModel<Integer> maximumModel) {

    this.maximumModel.setWrappedModel(maximumModel);

    return this;
  }

  public synchronized Integer getDiscreteValues () {

    return discreteValuesModel.getObject();
  }

  public synchronized DojoVerticalSlider setDiscreteValues (int discreteValues) {

    discreteValuesModel.setObject(discreteValues);

    return this;
  }

  public synchronized DojoVerticalSlider setDiscreteValuesModel (IModel<Integer> discreteValuesModel) {

    this.discreteValuesModel.setWrappedModel(discreteValuesModel);

    return this;
  }

  public synchronized double getValue () {

    return valueModel.getObject();
  }

  public synchronized DojoVerticalSlider setValue (double value) {

    valueModel.setObject(value);

    return this;
  }

  public synchronized DojoVerticalSlider setValueModel (IModel<Double> valueModel) {

    this.valueModel.setWrappedModel(valueModel);

    return this;
  }

  public synchronized int getCount () {

    return countModel.getObject();
  }

  public synchronized DojoVerticalSlider setCount (int count) {

    countModel.setObject(count);

    return this;
  }

  public synchronized DojoVerticalSlider setCountModel (IModel<Integer> countModel) {

    this.countModel.setWrappedModel(countModel);

    return this;
  }
}
