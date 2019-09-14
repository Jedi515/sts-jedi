package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.jedi.util.TextureLoader;

public class ScrapMetal
    extends CustomRelic
{
    public static final String ID = "jedi:scrapmetal";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    public static final int shivCount = 1;

    public ScrapMetal() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
        this.counter = -1;
    }

    public void atTurnStart()
    {
        this.counter = 0;
    }

    public void onVictory()
    {
        this.counter = -1;
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m)
    {
        if (c instanceof Shiv)
        {
            this.counter++;
        }
    }

    public float atDamageModify(float damage, AbstractCard c)
    {
        if (c.cardID.equals(Shiv.ID))
        {
            return damage + this.counter;
        }
        return damage;
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + shivCount + this.DESCRIPTIONS[1];
    }
}
