package replayTheSpire.minions.grembo;

import kobting.friendlyminions.monsters.*;
import com.megacrit.cardcrawl.monsters.*;
import kobting.friendlyminions.actions.*;
import basemod.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.badlogic.gdx.math.*;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;

import java.util.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;

public class GremboWizard extends AbstractFriendlyMonster
{
	private static final MonsterStrings monsterStrings;
    public static String NAME;
    public static String ID;
    private ArrayList<ChooseActionInfo> moveInfo;
    private boolean hasAttacked;
    private AbstractMonster target;
	public int currentCharge;
	private int baseDamage; 
    
    public GremboWizard(boolean makeMini) {
        super(GremboWizard.NAME, GremboWizard.ID, 20, (ArrayList)null, -8.0f, 10.0f, 230.0f, 240.0f, "images/monsters/exord/cook.png", -700.0f, 0.0f);
        this.hasAttacked = false;
        this.img = null;
        if (makeMini) {
            this.currentCharge = 2;
        	this.baseDamage = 20;
        } else {
            this.currentCharge = 1;
        	this.baseDamage = 25;
        }
    	this.damage.add(new DamageInfo(this, this.baseDamage));
        this.loadAnimation("images/monsters/theBottom/wizardGremlin/skeleton.atlas", "images/monsters/theBottom/wizardGremlin/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.flipHorizontal = true;
    }
    
    public void takeTurn() {
    	switch (this.nextMove) {
    	case 2: {
            ++this.currentCharge;
            AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, DIALOG[1]));
            if (this.escapeNext) {
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                break;
            }
            if (this.currentCharge == 3) {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2], 1.5f, 3.0f));
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, MOVES[1], (byte)1, Intent.ATTACK, this.damage.get(0).base));
                break;
            }
            this.setMove(MOVES[0], (byte)2, Intent.UNKNOWN);
            break;
        }
        case 1: {
            this.currentCharge = 0;
            final ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
            final float[] tmp2 = new float[m.size()];
            for (int i = 0; i < tmp2.length; ++i) {
                tmp2[i] = 25;
            }
            for (int i = 0; i < tmp2.length; ++i) {
                for (final AbstractPower p2 : this.powers) {
                    tmp2[i] = p2.atDamageGive(tmp2[i], DamageType.NORMAL);
                }
            }
            for (int i = 0; i < tmp2.length; ++i) {
                for (final AbstractPower p2 : this.powers) {
                    tmp2[i] = p2.atDamageFinalGive(tmp2[i], DamageType.NORMAL);
                }
            }
            for (int i = 0; i < tmp2.length; ++i) {
                if (tmp2[i] < 0.0f) {
                    tmp2[i] = 0.0f;
                }
            }
            int[] multiDamage = new int[tmp2.length];
            for (int i = 0; i < tmp2.length; ++i) {
                multiDamage[i] = MathUtils.floor(tmp2[i]);
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this, multiDamage, null, AbstractGameAction.AttackEffect.FIRE));
            if (this.escapeNext) {
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                break;
            }
            this.setMove(MOVES[0], (byte)2, Intent.UNKNOWN);
            break;
        }
	        case 99: {
	        	AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.5f, DIALOG[1], false));
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
	        }
    	}
    }
    
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        this.hasAttacked = false;
    }
    
    private static float applyPowersProxy(final AbstractMonster m, final float input, final AbstractMonster minionInstance) {
        float retVal = input;
        for (final AbstractPower p : minionInstance.powers) {
            retVal = p.atDamageGive(retVal, DamageInfo.DamageType.NORMAL);
        }
        for (final AbstractPower p : m.powers) {
            retVal = p.atDamageReceive(retVal, DamageInfo.DamageType.NORMAL);
        }
        for (final AbstractPower p : minionInstance.powers) {
            retVal = p.atDamageFinalGive(retVal, DamageInfo.DamageType.NORMAL);
        }
        for (final AbstractPower p : m.powers) {
            retVal = p.atDamageFinalReceive(retVal, DamageInfo.DamageType.NORMAL);
        }
        return retVal;
    }

    @Override
    protected void getMove(final int num) {
    	if (this.escapeNext || this.hasPower(CowardicePower.POWER_ID) && this.getPower(CowardicePower.POWER_ID).amount <= 0) {
    		this.setMove((byte)99, Intent.ESCAPE);
    		return;
    	}
    	if (this.currentCharge >= 3) {
    		this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
    	} else {
    		this.setMove(MOVES[0], (byte)2, Intent.UNKNOWN);
    	}
    }
    
    static {
    	monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinWizard");
    	NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
