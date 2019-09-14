package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mod.jedi.interfaces.ModifyDamageRelic;
import mod.jedi.util.TextureLoader;

public class StrikeManual
    extends CustomRelic
{
    public static final String ID = "jedi:strikemanual";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    public static final int efficiency = 4;

    public StrikeManual()
    {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.COMMON, LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + efficiency + this.DESCRIPTIONS[1];
    }

    public float atDamageModify(float damage, AbstractCard c) {
        if (c.hasTag(AbstractCard.CardTags.STRIKE))
        {
            return damage + efficiency;
        }
        return damage;
    }
}
