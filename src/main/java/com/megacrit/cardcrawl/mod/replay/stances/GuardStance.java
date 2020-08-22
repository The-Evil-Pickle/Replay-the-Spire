package com.megacrit.cardcrawl.mod.replay.stances;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.cards.*;
import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.stance.*;

public class GuardStance extends AbstractStance
{
    public static final String STANCE_ID = "Replay:Guard";
    private static final StanceStrings stanceString;
    private static long sfxId;
    
    public GuardStance() {
        this.ID = STANCE_ID;
        this.name = GuardStance.stanceString.NAME;
        this.updateDescription();
    }
    
    @Override
    public float atDamageGive(final float damage, final DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * 0.1f;
        }
        return damage;
    }
    
    @Override
    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0f) {
                this.particleTimer = 0.05f;
                //AbstractDungeon.effectsQueue.add(new WrathParticleEffect());
                AbstractDungeon.effectsQueue.add(new ShineSparkleEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-AbstractDungeon.player.hb.width / 2.0f - 30.0f * Settings.scale, AbstractDungeon.player.hb.width / 2.0f + 30.0f * Settings.scale), AbstractDungeon.player.hb.cY + MathUtils.random(-AbstractDungeon.player.hb.height / 2.0f - -10.0f * Settings.scale, AbstractDungeon.player.hb.height / 2.0f - 10.0f * Settings.scale)));
            }
        }
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0f) {
            this.particleTimer2 = MathUtils.random(0.3f, 0.4f);
            AbstractDungeon.effectsQueue.add(new ReplayStanceAuraEffect(STANCE_ID));
        }
    }
    
    @Override
    public void updateDescription() {
        this.description = GuardStance.stanceString.DESCRIPTION[0];
    }
    
    @Override
    public void onEnterStance() {
        if (GuardStance.sfxId != -1L) {
            this.stopIdleSfx();
        }
        CardCrawlGame.sound.play("POWER_DEXTERITY");
        GuardStance.sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_CALM");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.LIME, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Wrath"));
    }
    
    @Override
    public void onExitStance() {
        this.stopIdleSfx();
    }
    
    @Override
    public void stopIdleSfx() {
        if (GuardStance.sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_CALM", GuardStance.sfxId);
            GuardStance.sfxId = -1L;
        }
    }
    
    static {
        stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
        GuardStance.sfxId = -1L;
    }
}
