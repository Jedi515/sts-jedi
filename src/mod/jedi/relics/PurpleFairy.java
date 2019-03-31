package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnSmithRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import mod.jedi.util.TextureLoader;

public class PurpleFairy
    extends CustomRelic
    implements BetterOnSmithRelic
{
    public static final String ID = "jedi:purplefairy";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    AbstractPlayer p = AbstractDungeon.player;

    public PurpleFairy()
    {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
        this.description = DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(DESCRIPTIONS[1], DESCRIPTIONS[2]));
        this.initializeTips();
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    @Override
    public void betterOnSmith(AbstractCard abstractCard)
    {
        switch (abstractCard.rarity)
        {
            case BASIC:
                p.heal(p.maxHealth);
                break;
            case SPECIAL:
            case COMMON:
                p.heal(10);
                break;
            case UNCOMMON:
                p.heal(5);
                break;
            case RARE:
                p.heal(1);
                break;
            case CURSE:
                p.increaseMaxHp(5,true);
                p.heal(p.maxHealth);
                break;
            default:
                break;
        }
    }
}
