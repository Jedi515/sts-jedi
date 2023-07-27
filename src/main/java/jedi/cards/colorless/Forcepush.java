package jedi.cards.colorless;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import jedi.cards.CustomJediCard;


public class Forcepush
extends CustomJediCard
{

    public static final String ID = "jedi:forcepush";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public Forcepush()
    {
        super(ID, NAME, 0, DESCRIPTION, CardType.SKILL, AbstractCard.CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = 1;
        magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (upgraded)
        {
            addToBot(new ApplyPowerAction(m, p, new StrengthPower(p, -magicNumber)));
            if (!m.hasPower(ArtifactPower.POWER_ID))
                addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, magicNumber)));
        }
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false)));
        addToBot(new DrawCardAction(p, 1));
    }

    public void upp()
    {
    }
}
