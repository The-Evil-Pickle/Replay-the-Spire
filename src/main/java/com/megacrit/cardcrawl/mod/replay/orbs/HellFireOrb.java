package com.megacrit.cardcrawl.mod.replay.orbs;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.core.*;

public class HellFireOrb extends AbstractOrb
{
    public static final String ORB_ID = "Hellfire";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESC = orbString.DESCRIPTION;//{"#yPassive: At the start of turn, gain #b", " #yStrength until end of turn. NL #yEvoke: Apply #yVulnerable to a random enemy #b", " time(s)."};
    private static final float ORB_BORDER_SCALE = 1.2f;
    private float vfxTimer;
    private static final float VFX_INTERVAL_TIME = 0.25f;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;
	public static Texture ORB_HELLFIRE = ImageMaster.loadImage("images/orbs/replay/fire.png");
    
    public HellFireOrb() {
        this.vfxTimer = 0.5f;
        this.ID = "Hellfire";
        this.img = HellFireOrb.ORB_HELLFIRE;
        this.name = orbString.NAME;
        this.baseEvokeAmount = 1;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 2;
        this.passiveAmount = this.basePassiveAmount;
        this.updateDescription();
        this.angle = MathUtils.random(360.0f);
        this.channelAnimTimer = 0.5f;
    }
    
    @Override
    public void updateDescription() {
        this.applyFocus();
        this.description = HellFireOrb.DESC[0] + this.passiveAmount + HellFireOrb.DESC[1] + this.evokeAmount + HellFireOrb.DESC[2];
    }
    
    @Override
    public void onEvoke() {
        //AbstractDungeon.actionManager.addToTop(new GainEnergyAction(this.evokeAmount));
		for (int i=0; i<this.evokeAmount; i++) {
			final AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(true);
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(randomMonster, AbstractDungeon.player, new VulnerablePower(randomMonster, 1, false), 1, true, AbstractGameAction.AttackEffect.NONE));
		}
		//AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.evokeAmount), this.evokeAmount));
        //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, this.evokeAmount), this.evokeAmount));
    }
    
    @Override
    public void onStartOfTurn() {
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.passiveAmount), this.passiveAmount));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, this.passiveAmount), this.passiveAmount));
        //AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.passiveAmount));
    }
    
    @Override
    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 120.0f;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(this.cX, this.cY));
            this.vfxTimer = 0.25f;
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.c.a / 2.0f));
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale + MathUtils.sin(this.angle / 12.566371f) * 0.04f * Settings.scale, this.scale, this.angle, 0, 0, 96, 96, false, false);
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.c.a / 2.0f));
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale + MathUtils.sin(this.angle / 12.566371f) * 0.04f * Settings.scale, -this.angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        this.renderText(sb);
        this.hb.render(sb);
    }
    
    @Override
    public AbstractOrb makeCopy() {
        return new HellFireOrb();
    }
    
    @Override
    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("CARD_BURN", 0.1f);
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(this.cX, this.cY));
    }
	
    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ATTACK_FIRE", 0.1f);
    }
	/*
    static {
        orbString = CardCrawlGame.languagePack.getOrbString("Hellfire");
        DESC = HellFireOrb.orbString.DESCRIPTION;
    }
	*/
}
