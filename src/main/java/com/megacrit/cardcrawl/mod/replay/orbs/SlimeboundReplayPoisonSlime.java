package slimebound.orbs;

import slimebound.vfx.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.dungeons.*;
import slimebound.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import slimebound.actions.*;
import slimebound.orbs.SpawnedSlime;

import com.megacrit.cardcrawl.orbs.*;

public class SlimeboundReplayPoisonSlime extends SpawnedSlime
{
    public static final String ID = "Slimebound:PoisonSlime";
    public static final String atlasString = "images/monsters/theBottom/slimeS/skeleton.atlas";
    public static final String skeletonString = "images/monsters/theBottom/slimeS/skeleton.json";
    
    public SlimeboundReplayPoisonSlime() {
        super("Slimebound:PoisonSlime", new Color(0.5f, 1.0f, 0.5f, 100.0f), SlimeboundReplayPoisonSlime.atlasString, "images/monsters/theBottom/slimeAltS/skeleton.json", false, true, 2, 0, true, new Color(0.6f, 0.47f, 0.59f, 1.0f), SlimeFlareEffect.OrbFlareColor.SLIMING, new Texture("slimeboundResources/SlimeboundImages/orbs/debuff2.png"));
        this.extraFontColor = new Color(0.5f, 1.0f, 0.5f, 1.0f);
        this.debuffAmount = 2;
        this.spawnVFX();
    }
    
    @Override
    public void postSpawnEffects() {
        super.postSpawnEffects();
    }
    
    public void updateDescription() {
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1] + this.debuffAmount + this.descriptions[2];
    }
    
    @Override
    public void activateEffectUnique() {
        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.BLUNT_LIGHT, this, true, false, false, this.debuffAmount, false, 0, false));
    }
    
    public AbstractOrb makeCopy() {
        return new SlimeboundReplayPoisonSlime();
    }
}
