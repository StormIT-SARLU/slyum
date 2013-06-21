package graphic.factory;

import graphic.GraphicComponent;
import graphic.GraphicView;
import graphic.entity.EntityView;
import graphic.relations.AggregationView;
import graphic.relations.CompositionView;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import swing.SPanelDiagramComponent;

import classDiagram.relationships.Composition;
import classDiagram.relationships.Association.NavigateDirection;

/**
 * CompositionFactpry allows to create a new composition view associated with a
 * new association UML. Give this factory at the graphic view using the method
 * initNewComponent() for initialize a new factory. Next, graphic view will use
 * the factory to allow creation of a new component, according to the
 * specificity of the factory.
 * 
 * @author David Miserez
 * @version 1.0 - 25.07.2011
 */
public class CompositionFactory extends RelationFactory
{
	/**
	 * Create a new factory allowing the creation of a composition.
	 * 
	 * @param parent
	 *            the graphic view
	 * @param classDiagram
	 *            the class diagram
	 */
	public CompositionFactory(GraphicView parent)
	{
		super(parent);
    
    GraphicView.setButtonFactory(
        SPanelDiagramComponent.getInstance().getBtnComposition());
	}

	@Override
	public GraphicComponent create()
	{
		if (componentMousePressed instanceof EntityView && componentMouseReleased instanceof EntityView)
		{
			final EntityView source = (EntityView) componentMousePressed;
			final EntityView target = (EntityView) componentMouseReleased;

			final Composition composition = new Composition(source.getComponent(), target.getComponent(), NavigateDirection.BIDIRECTIONAL);
			final CompositionView c = new CompositionView(parent, source, target, composition, mousePressed, mouseReleased, true);

			parent.addLineView(c);
			classDiagram.addComposition(composition);

			parent.unselectAll();
			c.setSelected(true);

			return c;
		}

		repaint();
		return null;
	}

	@Override
	protected void drawExtremity(Graphics2D g2) {
	  Point p = points.size() < 2 ? mouseLocation : points.get(1);
		AggregationView.paintExtremity(g2, p, points.get(0), Color.BLACK, Color.DARK_GRAY);
	}
}
