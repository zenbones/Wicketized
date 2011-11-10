package com.wicketized.dojo;

import java.util.LinkedList;
import java.util.List;
import com.wicketized.extension.model.LazyModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class DojoOptionList<T> extends DojoTransparentPanel implements DojoSelectOptions {

  private final LazyModel<Boolean> defaultOnNullModel = new LazyModel<Boolean>(false);

  private IModel<? extends List<? extends T>> choices;
  private IChoiceRenderer<? super T> renderer;

  public DojoOptionList (String id, IModel<T> model, List<? extends T> choices, IChoiceRenderer<? super T> renderer) {

    this(id, model, new Model<LinkedList<? extends T>>(new LinkedList<T>(choices)), renderer);
  }

  public DojoOptionList (String id, IModel<T> model, IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer) {

    super(id, model);

    this.choices = choices;
    this.renderer = renderer;

    add(new ListView<T>("option", new ChoicesWrapperModel()) {

      @Override
      protected void populateItem (ListItem<T> listItem) {

        listItem.add(new AttributeModifier("value", new RenderedIdModel(listItem.getModelObject(), listItem.getIndex())));
        listItem.add(new AttributeModifier("selected", new SelectedModel(listItem.getModelObject(), listItem.getIndex())));
        listItem.add(new Label("displayValue", new RenderedValueModel(listItem.getModelObject())));
      }
    });

    setRenderBodyOnly(true);
  }

  public synchronized boolean isDefaultOnNullModel () {

    return defaultOnNullModel.getObject();
  }

  public synchronized DojoOptionList setDefaultOnNull (boolean defaultOnNull) {

    defaultOnNullModel.setObject(defaultOnNull);

    return this;
  }

  public synchronized DojoOptionList setDefaultOnNullModel (IModel<Boolean> defaultOnNullModel) {

    this.defaultOnNullModel.setWrappedModel(defaultOnNullModel);

    return this;
  }

  @Override
  public void setValue (String value) {

    setDefaultModelObject(convertStringToOptionType(value));
  }

  private T convertStringToOptionType (String value) {

    if ((value == null) || (value.length() == 0)) {

      return null;
    }

    int index = 0;

    for (T choice : choices.getObject()) {
      if (renderer.getIdValue(choice, index++).equals(value)) {

        return choice;
      }
    }

    return null;
  }

  @Override
  protected void onComponentTag (final ComponentTag tag) {

    checkComponentTag(tag, "option");

    super.onComponentTag(tag);
  }

  private class RenderedValueModel extends AbstractReadOnlyModel<String> {

    private T choice;

    public RenderedValueModel (T choice) {

      this.choice = choice;
    }

    @Override
    public String getObject () {

      return (choice == null) ? "Choose One" : renderer.getDisplayValue(choice).toString();
    }
  }

  private class RenderedIdModel extends AbstractReadOnlyModel<String> {

    private T choice;
    private int index;

    public RenderedIdModel (T choice, int index) {

      this.choice = choice;
      this.index = index;
    }

    @Override
    public String getObject () {

      return (choice == null) ? "" : renderer.getIdValue(choice, index);
    }
  }

  private class SelectedModel extends AbstractReadOnlyModel<String> {

    private T choice;
    private int index;

    public SelectedModel (T choice, int index) {

      this.choice = choice;
      this.index = index;
    }

    @Override
    public String getObject () {

      return (getDefaultModelObject() == null) ? (index == 0) ? "selected" : null : getDefaultModelObject().equals(choice) ? "selected" : null;
    }
  }

  private class ChoicesWrapperModel extends AbstractReadOnlyModel<List<? extends T>> {

    @Override
    public List<? extends T> getObject () {

      if (((getDefaultModelObject() == null) && (!isDefaultOnNullModel())) || (choices.getObject() == null) || choices.getObject().isEmpty()) {

        LinkedList<T> nullAddedList = new LinkedList<T>();

        nullAddedList.add(null);
        if (choices.getObject() != null) {
          nullAddedList.addAll(choices.getObject());
        }

        return nullAddedList;
      }

      return choices.getObject();
    }
  }
}
