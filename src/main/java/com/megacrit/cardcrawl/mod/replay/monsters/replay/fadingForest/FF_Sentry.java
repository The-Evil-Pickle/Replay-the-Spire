package com.megacrit.cardcrawl.mod.replay.monsters.replay.fadingForest;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.status.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import com.megacrit.cardcrawl.monsters.Intent;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.math.*;
import com.esotericsoftware.spine.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.core.*;

public class FF_Sentry extends AbstractMonster
{
    public static final String ID = "FF_Sentry";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String ENC_NAME = "Sentries";
    private static final int HP = 40;
    private static final int A_HP = 45;
    private static final byte BOLT = 3;
    private static final byte BEAM = 4;
    private int beamDmg;
    private static final int DAZED_AMT = 2;
    private boolean firstMove;
	private boolean isAlt;
    
    public FF_Sentry(final float x, final float y, boolean isAlt) {
        super(FF_Sentry.NAME, "FF_Sentry", 41, 0.0f, -5.0f, 180.0f, 310.0f, null, x, y);
        this.beamDmg = 8;
        this.firstMove = true;
		this.isAlt = isAlt;
        //this.type = EnemyType.ELITE;
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(A_HP);
        }
        else {
            this.setHp(HP);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.beamDmg = 8;
        }
        else {
            this.beamDmg = 7;
        }
        this.damage.add(new DamageInfo(this, this.beamDmg));
        this.loadAnimation("images/monsters/fadingForest/sentry/skeleton.atlas", "images/monsters/fadingForest/sentry/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTimeScale(2.0f);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("idle", "attack", 0.1f);
        this.stateData.setMix("idle", "spaz1", 0.1f);
        this.stateData.setMix("idle", "hit", 0.1f);
    }
    
    @Override
    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 1)));
    }
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 3: {
                AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.ROYAL, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.5f));
                AbstractDungeon.actionManager.addToBottom(new FastShakeAction(AbstractDungeon.player, 0.6f, 0.2f));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Dazed(), 2));
                break;
            }
            case 4: {
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5f));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BorderFlashEffect(Color.SKY)));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.hb.cX, this.hb.cY), 0.3f));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
                break;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    @Override
    public void damage(final DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "hit", false);
            this.state.addAnimation(0, "idle", true, 0.0f);
        }
    }
    
    @Override
    public void changeState(final String stateName) {
        switch (stateName) {
            case "ATTACK": {
                this.state.setAnimation(0, "attack", false);
                this.state.addAnimation(0, "idle", true, 0.0f);
                break;
            }
        }
    }
    
    @Override
    protected void getMove(final int num) {
        if (this.firstMove) {
            if (this.isAlt) {
                this.setMove((byte)3, Intent.DEBUFF);
            }
            else {
                this.setMove((byte)4, Intent.ATTACK, this.damage.get(0).base);
            }
            this.firstMove = false;
            return;
        }
        if (this.lastMove((byte)4)) {
            this.setMove((byte)3, Intent.DEBUFF);
        }
        else {
            this.setMove((byte)4, Intent.ATTACK, this.damage.get(0).base);
        }
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Sentry");
        NAME = "False " + FF_Sentry.monsterStrings.NAME;
        MOVES = FF_Sentry.monsterStrings.MOVES;
        DIALOG = FF_Sentry.monsterStrings.DIALOG;
    }
}
