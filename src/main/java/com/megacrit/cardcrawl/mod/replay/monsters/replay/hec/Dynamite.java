package com.megacrit.cardcrawl.mod.replay.monsters.replay.hec;

import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.ReplayExplosivePower;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class Dynamite extends AbstractMonster
{
    public static final String ID = "Replay:ConductorBomb";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP = 30;
    private static final int A_HP = 30;
    private int turnCount;
    private int attackDmg;
    
    public Dynamite(final float x, final float y, final int dmg) {
        super(Dynamite.NAME, ID, 30, -8.0f, -10.0f, 150.0f, 150.0f, null, x, y + 10.0f);
        this.turnCount = 0;
        this.loadAnimation("images/monsters/theForest/exploder/skeleton.atlas", "images/monsters/theForest/exploder/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(A_HP);
        }
        else {
            this.setHp(HP);
        }
        this.attackDmg = dmg;
        this.damage.add(new DamageInfo(this, this.attackDmg));
    }
    
    @Override
    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ReplayExplosivePower(this, 3, this.attackDmg), this.attackDmg));
    }
    
    @Override
    public void takeTurn() {
        ++this.turnCount;
        if (!this.hasPower(ReplayExplosivePower.POWER_ID)) {
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ReplayExplosivePower(this, 3, this.attackDmg), this.attackDmg));
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    @Override
    protected void getMove(final int num) {
    	if (!this.hasPower(ReplayExplosivePower.POWER_ID)) {
    		this.setMove((byte)3, Intent.BUFF);
    		return;
    	}
        if (this.getPower(ReplayExplosivePower.POWER_ID).amount > 1) {
            this.setMove((byte)1, Intent.SLEEP);
        }
        else {
            this.setMove((byte)2, Intent.UNKNOWN);
        }
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = Dynamite.monsterStrings.NAME;
        MOVES = Dynamite.monsterStrings.MOVES;
        DIALOG = Dynamite.monsterStrings.DIALOG;
    }
}
