package replayTheSpire.panelUI;

import basemod.*;
import com.megacrit.cardcrawl.helpers.*;
import java.util.function.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.core.*;

public class ImageButton implements IUIElement
{
    private Texture image;
    private int x;
    private int y;
    private int w;
    private int h;
    private Hitbox hitbox;
    public Consumer<ImageButton> click;
    
    public ImageButton(final String url, final int x, final int y, final int width, final int height, final Consumer<ImageButton> click) {
        this.image = new Texture(url);
        this.x = (int)(Settings.scale * x);
        this.y = (int)(Settings.scale * y);
        this.w = (int)(Settings.scale * width);
        this.h = (int)(Settings.scale * height);
        this.hitbox = new Hitbox((float)this.x, (float)this.y, (float)this.w, (float)this.h);
        this.click = click;
    }
    
    public void render(final SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.image, (float)this.x, (float)this.y, (float)this.w, (float)this.h);
        this.hitbox.render(sb);
    }
    
    public void update() {
        this.hitbox.update();
        if (this.hitbox.hovered && InputHelper.justClickedLeft) {
            CardCrawlGame.sound.play("UI_CLICK_1");
            this.click.accept(this);
        }
    }
    
    public int renderLayer() {
        return 1;
    }
    
    public int updateOrder() {
        return 1;
    }
}
