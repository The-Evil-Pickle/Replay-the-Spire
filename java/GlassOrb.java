package com.megacrit.cardcrawl.orbs;

import com.megacrit.cardcrawl.localization.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.helpers.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.core.*;

public class GlassOrb extends AbstractOrb
{
    public static final String ORB_ID = "Glass";
    //private static final OrbStrings orbString;
    //public static final String[] DESC;
    private static Texture img1;
    private static Texture img2;
	private AbstractOrb showingEvoke;
    
    public GlassOrb() {
        if (GlassOrb.img1 == null) {
            GlassOrb.img1 = ImageMaster.loadImage("images/orbs/replay/glass1.png");
            GlassOrb.img2 = ImageMaster.loadImage("images/orbs/empty2.png");
        }
        this.baseEvokeAmount = 3;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 0;
        this.passiveAmount = this.basePassiveAmount;
        this.angle = MathUtils.random(360.0f);
		this.ID = "Glass";
        this.name = "Glass";//EmptyOrbSlot.orbString.NAME;
        this.cX = AbstractDungeon.player.drawX + AbstractDungeon.player.hb_x;
        this.cY = AbstractDungeon.player.drawY + AbstractDungeon.player.hb_y + AbstractDungeon.player.hb_h / 2.0f;
        this.updateDescription();
		this.showingEvoke = null;
    }
    
    @Override
    public void updateDescription() {
		this.applyFocus();
        this.description = "#yPassive: No effect. NL #yEvoke: If you have more than #b" + this.passiveAmount + " orb slots, consumes your leftmost orb slot and #yEvokes any orb occupying it.";//EmptyOrbSlot.DESC[0];
    }
    
    @Override
    public void onEvoke() {
		if (AbstractDungeon.player.maxOrbs > this.evokeAmount && AbstractDungeon.player.maxOrbs > 1) {
			//AbstractDungeon.player.evokeNewestOrb();
			if (!AbstractDungeon.player.orbs.isEmpty() && !(AbstractDungeon.player.orbs.get(AbstractDungeon.player.orbs.size() - 1) instanceof EmptyOrbSlot)) {
				AbstractDungeon.player.orbs.get(AbstractDungeon.player.orbs.size() - 1).onEvoke();
				final AbstractOrb orbSlot = new EmptyOrbSlot();
				/*for (int i = 1; i < this.orbs.size(); ++i) {
					Collections.swap(this.orbs, i, i - 1);
				}*/
				AbstractDungeon.player.orbs.set(AbstractDungeon.player.orbs.size() - 1, orbSlot);
				
				/*for (int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
					AbstractDungeon.player.orbs.get(i).setSlot(i, AbstractDungeon.player.maxOrbs);
				}*/
			}
			AbstractDungeon.player.decreaseMaxOrbSlots(1);
			//AbstractDungeon.actionManager.addToTop(new DecreaseMaxOrbAction(1));
		}
		if (this.showingEvoke != null) {
			this.showingEvoke.hideEvokeValues();
			this.showingEvoke = null;
		}
    }
    
    @Override
    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 10.0f;
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        sb.setColor(this.c);
        sb.draw(GlassOrb.img2, this.cX - 48.0f - this.bobEffect.y / 8.0f, this.cY - 48.0f + this.bobEffect.y / 8.0f, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, 0.0f, 0, 0, 96, 96, false, false);
        sb.draw(GlassOrb.img1, this.cX - 48.0f + this.bobEffect.y / 8.0f, this.cY - 48.0f - this.bobEffect.y / 8.0f, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, 0.0f, 0, 0, 96, 96, false, false);
        //this.renderText(sb);
        this.hb.render(sb);
    }
    
	@Override
    public void showEvokeValue() {
        this.showEvokeValue = true;
        this.fontScale = 1.5f;
		if (AbstractDungeon.player.maxOrbs > this.evokeAmount && AbstractDungeon.player.maxOrbs > 1 && !AbstractDungeon.player.orbs.isEmpty()) {
			AbstractOrb leftorb = AbstractDungeon.player.orbs.get(AbstractDungeon.player.orbs.size() - 1);
			if (leftorb != null && !(leftorb instanceof EmptyOrbSlot) && !(leftorb instanceof GlassOrb)) {
				leftorb.showEvokeValue();
				this.showingEvoke = leftorb;
			}
		}
    }
    
	@Override
    public void hideEvokeValues() {
        this.showEvokeValue = false;
		if (this.showingEvoke != null) {
			this.showingEvoke.hideEvokeValues();
			this.showingEvoke = null;
		}
    }
	
    @Override
    public AbstractOrb makeCopy() {
        return new GlassOrb();
    }
    
    @Override
    public void playChannelSFX() {
    }
}
