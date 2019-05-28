package replayTheSpire.replayxover.bard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.bard.notes.AbstractNote;
import com.megacrit.cardcrawl.core.Settings;

import replayTheSpire.ReplayTheSpireMod;

public class OrbNote extends AbstractNote
{
    private static OrbNote singleton;
    
    public static OrbNote get() {
        if (OrbNote.singleton == null) {
            OrbNote.singleton = new OrbNote();
        }
        return OrbNote.singleton;
    }
    
    private OrbNote() {
        super(Color.valueOf("7648c4"));//f3f931
    }
    
    @Override
    public String name() {
        return "Orb";
    }
    
    @Override
    public String ascii() {
        return "O";
    }
    
    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ReplayTheSpireMod.replayxover_note.findRegion("noteOrb");
    }
    
    @Override
    public void render(final SpriteBatch sb, final float x, final float y) {
        super.render(sb, x, y + 16.0f * Settings.scale);
    }
}
