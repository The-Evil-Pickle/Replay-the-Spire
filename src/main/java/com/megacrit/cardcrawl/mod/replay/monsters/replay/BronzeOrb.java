package com.megacrit.cardcrawl.mod.replay.monsters.replay;

import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.*;

public class BronzeOrb extends AbstractMonster
{
    public static final String ID = "Replay:BronzeOrb";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 52;
    private static final int HP_MAX = 58;
    private static final int A_2_HP_MIN = 54;
    private static final int A_2_HP_MAX = 60;
    private static final int BEAM_DMG = 8;
    private static final int BLOCK_AMT = 12;
    private static final byte BEAM = 1;
    private static final byte SUPPORT_BEAM = 2;
    private static final byte STASIS = 3;
    private int usedStasis;
    private int count;
    private int stasisChance;
    
    public BronzeOrb(final float x, final float y, final int count) {
        super(BronzeOrb.NAME, "Replay:BronzeOrb", AbstractDungeon.monsterHpRng.random(52, 58), 0.0f, 0.0f, 160.0f, 160.0f, "images/monsters/theCity/automaton/orb.png", x, y);
        this.usedStasis = Math.max(1, 1 + (AbstractDungeon.player.masterDeck.size() - 5) / 12);
        this.stasisChance = 25;
        this.setHp(52, 58);
        this.count = count;
        this.damage.add(new DamageInfo(this, 8));
    }
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1: {
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5f));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BorderFlashEffect(Color.SKY)));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.hb.cX, this.hb.cY), 0.3f));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
                break;
            }
            case 2: {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 12));
                break;
            }
            case 3: {
                AbstractDungeon.actionManager.addToBottom(new ApplyStasisAction(this));
                break;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    @Override
    public void update() {
        super.update();
        if (this.count % 2 == 0) {
            this.animY = MathUtils.cosDeg(System.currentTimeMillis() / 6L % 360L) * 6.0f * Settings.scale;
        }
        else {
            this.animY = -MathUtils.cosDeg(System.currentTimeMillis() / 6L % 360L) * 6.0f * Settings.scale;
        }
    }
    
    @Override
    protected void getMove(final int num) {
        if (this.usedStasis > 0 && num >= this.stasisChance && !this.lastMove(STASIS)) {
            this.setMove(STASIS, Intent.STRONG_DEBUFF);
            this.usedStasis--;
            this.stasisChance = 75;
            return;
        }
        this.setMove((byte)2, Intent.DEFEND);
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("BronzeOrb");
        NAME = BronzeOrb.monsterStrings.NAME;
        MOVES = BronzeOrb.monsterStrings.MOVES;
        DIALOG = BronzeOrb.monsterStrings.DIALOG;
    }
}
