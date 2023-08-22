package jedi.cardMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.jedi;
import jedi.util.Wiz;

public class SufferingMod
    extends AbstractCardModifier
{
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(jedi.makeID(SufferingMod.class.getSimpleName())).TEXT;
    private int potency;

    public void setPotency(int newPotency) {potency = newPotency;}

    public SufferingMod(int amt)
    {
        potency = amt;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + Wiz.adp().exhaustPile.group.stream().filter(c -> card.upgraded || c.type == AbstractCard.CardType.ATTACK).count() * potency;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SufferingMod(potency);
    }
}
