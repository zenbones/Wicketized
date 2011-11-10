package com.wicketized.dojo;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.MarkupElement;
import org.apache.wicket.markup.MarkupException;
import org.apache.wicket.markup.MarkupFragment;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.TagUtils;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.BorderMarkupSourcingStrategy;
import org.apache.wicket.markup.html.panel.IMarkupSourcingStrategy;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.markup.parser.filter.WicketTagIdentifier;
import org.apache.wicket.markup.resolver.IComponentResolver;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Args;

public abstract class ModelAwareDojoBorder extends WebMarkupContainer implements IComponentResolver {

  private static final long serialVersionUID = 1L;

  public static final String BODY = "body";
  public static final String BORDER = "border";

  private boolean bodyModel;

  static {
    // register "wicket:body" and "wicket:border"
    WicketTagIdentifier.registerWellKnownTagName(BORDER);
    WicketTagIdentifier.registerWellKnownTagName(BODY);
  }

  /**
   * The body component associated with <wicket:body>
   */
  private final BorderBodyContainer body;

  /**
   * @see org.apache.wicket.Component#Component(String)
   */
  protected ModelAwareDojoBorder (final String id, boolean bodyModel) {

    this(id, null, bodyModel);

    this.bodyModel = bodyModel;
  }

  /**
   * @see org.apache.wicket.Component#Component(String, org.apache.wicket.model.IModel)
   */
  protected ModelAwareDojoBorder (final String id, final IModel<?> model, boolean bodyModel) {

    super(id, model);

    this.bodyModel = bodyModel;

    body = new BorderBodyContainer(id + "_" + BODY);
    addToBorder(body);
  }

  /**
   * @return The border body container
   */
  public final BorderBodyContainer getBodyContainer () {

    return body;
  }

  /**
   * This is for all components which have been added to the markup like this:
   * <p/>
   * <pre>
   * 	&lt;span wicket:id="myBorder"&gt;
   * 		&lt;input wicket:id="text1" .. /&gt;
   * 		...
   * 	&lt;/span&gt;
   *
   * </pre>
   * <p/>
   * Whereas {@link #addToBorder(org.apache.wicket.Component...)} will add a component associated with the following
   * markup:
   * <p/>
   * <pre>
   * 	&lt;wicket:border&gt;
   * 		&lt;form wicket:id="myForm" .. &gt;
   * 			&lt;wicket:body/&gt;
   * 		&lt;/form&gt;
   * 	&lt;/wicket:border&gt;
   *
   * </pre>
   *
   * @see org.apache.wicket.MarkupContainer#add(org.apache.wicket.Component[])
   */
  @Override
  public ModelAwareDojoBorder add (final Component... children) {

    getBodyContainer().add(children);
    return this;
  }

  @Override
  public ModelAwareDojoBorder addOrReplace (final Component... children) {

    getBodyContainer().addOrReplace(children);
    return this;
  }

  @Override
  public ModelAwareDojoBorder remove (final Component component) {

    getBodyContainer().remove(component);
    return this;
  }

  @Override
  public ModelAwareDojoBorder remove (final String id) {

    getBodyContainer().remove(id);
    return this;
  }

  @Override
  public ModelAwareDojoBorder removeAll () {

    getBodyContainer().removeAll();
    return this;
  }

  @Override
  public ModelAwareDojoBorder replace (final Component replacement) {

    getBodyContainer().replace(replacement);
    return this;
  }

  /**
   * Adds children components to the Border itself
   *
   * @param children the children components to add
   * @return this
   */
  public ModelAwareDojoBorder addToBorder (final Component... children) {

    super.add(children);
    return this;
  }

  /**
   * Removes child from the Border itself
   *
   * @param child
   * @return {@code this}
   */
  public ModelAwareDojoBorder removeFromBorder (final Component child) {

    super.remove(child);
    return this;
  }

  /**
   * Replaces component in the Border itself
   *
   * @param component
   * @return {@code this}
   */
  public ModelAwareDojoBorder replaceInBorder (final Component component) {

    super.replace(component);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  public Component resolve (final MarkupContainer container, final MarkupStream markupStream,
                            final ComponentTag tag) {
    // make sure nested borders are resolved properly
    if (body.rendering == false) {
      // We are only interested in border body tags. The tag ID actually is irrelevant since
      // always preset with the same default
      if (TagUtils.isWicketBodyTag(tag)) {
        return body;
      }
    }

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IMarkupSourcingStrategy newMarkupSourcingStrategy () {

    return new BorderMarkupSourcingStrategy();
  }

  /**
   * Search for the child markup in the file associated with the Border. The child markup must in
   * between the &lt;wicket:border&gt; tags.
   */
  @Override
  public IMarkupFragment getMarkup (final Component child) {
    // Border require an associated markup resource file
    IMarkupFragment markup = getAssociatedMarkup();
    if (markup == null) {
      throw new MarkupException("Unable to find associated markup file for Border: " +
        this.toString());
    }

    // Find <wicket:border>
    IMarkupFragment borderMarkup = null;
    for (int i = 0; i < markup.size(); i++) {
      MarkupElement elem = markup.get(i);
      if (TagUtils.isWicketBorderTag(elem)) {
        borderMarkup = new MarkupFragment(markup, i);
        break;
      }
    }

    if (borderMarkup == null) {
      throw new MarkupException(markup.getMarkupResourceStream(),
        "Unable to find <wicket:border> tag in associated markup file for Border: " +
          this.toString());
    }

    // If child == null, return the markup fragment starting with the <wicket:border> tag
    if (child == null) {
      return borderMarkup;
    }

    // Is child == BorderBody?
    if (child == body) {
      // Get the <wicket:body> markup
      return body.getMarkup();
    }

    // Find the markup for the child component
    IMarkupFragment childMarkup = borderMarkup.find(child.getId());
    if (childMarkup != null) {
      return childMarkup;
    }

    return ((BorderMarkupSourcingStrategy)getMarkupSourcingStrategy()).findMarkupInAssociatedFileHeader(
      this, child);
  }

  /**
   * The container to be associated with the &lt;wicket:body&gt; tag
   */
  public class BorderBodyContainer extends TransparentWebMarkupContainer {

    private static final long serialVersionUID = 1L;

    /**
     * The markup
     */
    private transient IMarkupFragment markup;

    // properly resolve borders added to borders
    protected boolean rendering;

    /**
     * Constructor
     *
     * @param id
     */
    public BorderBodyContainer (final String id) {

      super(id);
    }

    @Override
    protected void onComponentTag (final ComponentTag tag) {
      // Convert open-close to open-body-close
      if (tag.isOpenClose()) {
        tag.setType(XmlTag.TagType.OPEN);
        tag.setModified(true);
      }

      super.onComponentTag(tag);
    }

    @Override
    public void onComponentTagBody (final MarkupStream markupStream, final ComponentTag openTag) {
      // skip the <wicket:body> body
      if (markupStream.getPreviousTag().isOpen()) {
        // Only RawMarkup is allowed within the preview region,
        // which gets stripped from output
        markupStream.skipRawMarkup();
      }

      // Get the <span wicket:id="myBorder"> markup and render that instead
      IMarkupFragment markup = ModelAwareDojoBorder.this.getMarkup();
      MarkupStream stream = new MarkupStream(markup);
      ComponentTag tag = stream.getTag();
      stream.next();

      if (!bodyModel) {
        super.onComponentTagBody(stream, tag);
      }
      else {
        if (ModelAwareDojoBorder.this.getDefaultModel() != null) {
          replaceComponentTagBody(stream, tag, ModelAwareDojoBorder.this.getDefaultModelObjectAsString());
        }
        else {
          ModelAwareDojoBorder.this.setDefaultModel(new Model<String>(stream.atCloseTag() ? "" : stream.get().toString()));
          super.onComponentTagBody(stream, tag);
        }
      }
    }

    @Override
    protected void onRender () {

      rendering = true;

      try {
        super.onRender();
      }
      finally {
        rendering = false;
      }
    }

    /**
     * Get the &lt;wicket:body&gt; markup from the body's parent container
     */
    @Override
    public IMarkupFragment getMarkup () {

      if (markup == null) {
        markup = findByName(getParent().getMarkup(null), BODY);
      }
      return markup;
    }

    /**
     * Search for &lt;wicket:'name' ...&gt; on the same level, but ignoring other "transparent"
     * tags such as &lt;wicket:enclosure&gt; etc.
     *
     * @param markup
     * @param name
     * @return null, if not found
     */
    private final IMarkupFragment findByName (final IMarkupFragment markup, final String name) {

      Args.notEmpty(name, "name");

      MarkupStream stream = new MarkupStream(markup);

      // Skip any raw markup
      stream.skipUntil(ComponentTag.class);

      // Skip <wicket:border>
      stream.next();

      while (stream.skipUntil(ComponentTag.class)) {
        ComponentTag tag = stream.getTag();
        if (tag.isOpen() || tag.isOpenClose()) {
          if (TagUtils.isWicketBodyTag(tag)) {
            return stream.getMarkupFragment();
          }
        }

        stream.next();
      }

      return null;
    }

    /**
     * Get the child markup which must be in between the &lt;span wicktet:id="myBorder"&gt; tags
     */
    @Override
    public IMarkupFragment getMarkup (final Component child) {

      IMarkupFragment markup = ModelAwareDojoBorder.this.getMarkup();
      if (markup == null) {
        return null;
      }

      if (child == null) {
        return markup;
      }

      return markup.find(child.getId());
    }
  }
}
