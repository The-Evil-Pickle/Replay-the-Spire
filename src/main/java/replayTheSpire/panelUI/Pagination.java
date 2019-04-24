package replayTheSpire.panelUI;

import basemod.*;
import com.badlogic.gdx.graphics.g2d.*;
import java.util.*;

public class Pagination implements IUIElement
{
    private ImageButton next;
    private ImageButton prior;
    private int page;
    private int elementsPerPage;
    private List<RelicSettingsButton> elements;
    private int width;
    private int height;
    private int rows;
    private int columns;
    
    public Pagination(final ImageButton next, final ImageButton prior, final int rows, final int columns, final int width, final int height, final List<RelicSettingsButton> elements) {
        this.page = 0;
        next.click = (b -> ++this.page);
        prior.click = (b -> --this.page);
        this.next = next;
        this.prior = prior;
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.columns = columns;
        this.elements = new ArrayList<RelicSettingsButton>();
        this.elementsPerPage = rows * columns;
        for (int i = 0; i < elements.size(); ++i) {
            final RelicSettingsButton element = elements.get(i);
            if (element == null)
            	continue;
            final RelicSettingsButton newElement;
            if (element.relic != null) {
            	newElement = new RelicSettingsButton(element.relic, element.x + width * (i % columns), element.y - height * ((i % this.elementsPerPage - i % columns) / columns), width, height, element.elements);
            } else {
            	newElement = new RelicSettingsButton(element.image, element.outline, element.x + width * (i % columns), element.y - height * ((i % this.elementsPerPage - i % columns) / columns), width, height, element.elements);
            }
            newElement.settings = element.settings;
            this.elements.add(newElement);
        }
    }
    
    public void render(final SpriteBatch spriteBatch) {
        if (this.page != 0) {
            this.prior.render(spriteBatch);
        }
        if ((this.page + 1) * this.elementsPerPage < this.elements.size()) {
            this.next.render(spriteBatch);
        }
        for (final RelicSettingsButton element : this.elements.subList(this.page * this.elementsPerPage, Math.min((this.page + 1) * this.elementsPerPage, this.elements.size()))) {
            element.render(spriteBatch);
        }
    }
    
    public void update() {
        if (this.page != 0) {
            this.prior.update();
        }
        if ((this.page + 1) * this.elementsPerPage < this.elements.size()) {
            this.next.update();
        }
        for (final RelicSettingsButton element : this.elements.subList(this.page * this.elementsPerPage, Math.min((this.page + 1) * this.elementsPerPage, this.elements.size()))) {
            element.update();
        }
    }
    
    public int renderLayer() {
        return 1;
    }
    
    public int updateOrder() {
        return 1;
    }
    
    public void addElement(RelicSettingsButton element) {
        final RelicSettingsButton newElement;
        int i = this.elements.size();
    	if (element.relic != null) {
        	newElement = new RelicSettingsButton(element.relic, element.x + width * (i % columns), element.y - height * ((i % this.elementsPerPage - i % columns) / columns), width, height, element.elements);
        } else {
        	newElement = new RelicSettingsButton(element.image, element.outline, element.x + width * (i % columns), element.y - height * ((i % this.elementsPerPage - i % columns) / columns), width, height, element.elements);
        }
        newElement.settings = element.settings;
        this.elements.add(newElement);
    }
}
