package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.jedi.interfaces.ModifyBlockRelicInterface;
import mod.jedi.interfaces.ModifyDamageRelic;
import mod.jedi.util.TextureLoader;

public class Equalizer
    extends CustomRelic
    implements ModifyBlockRelicInterface, ModifyDamageRelic
{
    public static final String ID = "jedi:equalizer";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    public static final float modifier = 0.25F;

    public Equalizer()
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
        if (card.rarity == AbstractCard.CardRarity.BASIC || card.rarity == AbstractCard.CardRarity.COMMON)
        {
            return (float)Math.ceil(block * (1F + modifier));
        }
        else
        {
            return (float)Math.floor(block * (1F - modifier));
        }
    }

    public int calculateCardDamageFinalRelic(AbstractCard card, AbstractMonster target, int damage)
    {
        if (card.rarity == AbstractCard.CardRarity.BASIC || card.rarity == AbstractCard.CardRarity.COMMON)
        {
            return (int)Math.ceil(damage * (1F + modifier));
        }
        else
        {
            return (int)Math.floor(damage * (1F - modifier));
        }
    }

    public int applyPowersFinalRelic(AbstractCard card, int damage)
    {
        if (card.rarity == AbstractCard.CardRarity.BASIC || card.rarity == AbstractCard.CardRarity.COMMON)
        {
            return (int)Math.ceil(damage * (1F + modifier));
        }
        else
        {
            return (int)Math.floor(damage * (1F - modifier));
        }
    }
}
