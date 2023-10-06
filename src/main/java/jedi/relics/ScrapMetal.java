package jedi.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ScrapMetal
    extends AbstractJediRelic
{
    public static final String ID = "jedi:scrapmetal";
    public static final int shivCount = 1;

    public ScrapMetal() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK, RelicType.GREEN);
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
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone=true;
                    counter++;
                }
            });
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
