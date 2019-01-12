package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnChannelRelic;
import com.megacrit.cardcrawl.actions.defect.ImpulseAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mod.jedi.util.TextureLoader;

public class Superconductor
    extends CustomRelic
    implements OnChannelRelic
{
    public static final String ID = "jedi:superconductor";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);

    public Superconductor() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON,LandingSound.FLAT);
        this.counter = 0;
    }

    public void onChannel(AbstractOrb orb)
    {
        ++this.counter;
        if (this.counter == 10) {
            this.counter = 0;
            this.flash();
            this.pulse = false;
            AbstractDungeon.actionManager.addToBottom(new ImpulseAction());
        }
        else if (this.counter == 9) {
            this.beginPulse();
            this.pulse = true;
        }
    }

    public void atBattleStart() {
        if (this.counter == 9) {
            this.beginPulse();
            this.pulse = true;
        }
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new Superconductor();
    }
}
