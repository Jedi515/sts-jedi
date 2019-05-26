package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.jedi.interfaces.ModifyBlockRelicInterface;
import mod.jedi.interfaces.ModifyDamageRelic;
import mod.jedi.util.TextureLoader;

public class ScalesOfToshan
    extends CustomRelic
    implements ModifyBlockRelicInterface, ModifyDamageRelic
{
    public static final String ID = "jedi:scalesoftoshan";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    public static final float modifier = 0.25F;

    public ScalesOfToshan()
    {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
        this.description = DESCRIPTIONS[0];
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public float modifyBlock(AbstractCard card, float block)
    {
        if (!card.upgraded)
        {
            return (float)Math.ceil(block * (1F + modifier));
        }
        else
        {
            return (float)Math.ceil(block * (1F - modifier));
        }
    }

    public float calculateCardDamageFinalRelic(AbstractCard card, AbstractMonster target, float damage)
    {
        if (!card.upgraded)
        {
            return damage * (1F + modifier);
        }
        else
        {
            return damage * (1F - modifier);
        }
    }

    public float applyPowersFinalRelic(AbstractCard card, float damage)
    {
        if (!card.upgraded)
        {
            return damage * (1F + modifier);
        }
        else
        {
            return damage * (1F - modifier);
        }
    }
}
