package mod.jedi.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ScrapMetal
    extends AbstractJediRelic
{
    public static final String ID = "jedi:scrapmetal";
    public static final int shivCount = 1;

    public ScrapMetal() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK);
        cardToPreview = new Shiv();
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
