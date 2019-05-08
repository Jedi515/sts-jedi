package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mod.jedi.interfaces.ModifyDamageRelic;
import mod.jedi.util.TextureLoader;

public class StrengthTestRelic
    extends CustomRelic
    implements ModifyDamageRelic
{
    public static final String ID = "jedi:strengthtestrelic";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);

    public StrengthTestRelic()
    {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public int calculateCardDamageRelic(AbstractCard card, AbstractMonster target, int damage)
    {
        return 10 + damage;
    }

    public int applyPowersRelic(AbstractCard card, int damage)
    {
        return 10 + damage;
    }
}
