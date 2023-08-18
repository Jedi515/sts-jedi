package jedi.cardMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.PerfectedStrike;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ImperfectMod extends AbstractCardModifier {

    private int damageAmp;
    private boolean isInherent;
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(jedi.jedi.makeID(ImperfectMod.class.getSimpleName())).TEXT;
    public ImperfectMod(int amt, boolean inherent)
    {
        damageAmp = amt;
        isInherent = inherent;
    }

    @Override
    public String modifyName(String cardName, AbstractCard card) {
        return String.format(TEXT[0], cardName);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return String.format(TEXT[1], rawDescription, damageAmp);
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + PerfectedStrike.countCards()*damageAmp;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return isInherent;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ImperfectMod(damageAmp, isInherent);
    }
}
