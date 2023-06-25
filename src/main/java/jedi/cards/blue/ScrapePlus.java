package jedi.cards.blue;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ScrapeEffect;
import jedi.actions.DrawCallbackShuffleAction;

public class ScrapePlus
    extends AbstractCard
{
    public static final String ID = "Scrape";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public ScrapePlus()
    {
        super(ID, cardStrings.NAME, "blue/attack/scrape", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 7;
        baseMagicNumber = 2;
        magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (m != null)
        {
            addToBot(new VFXAction(new ScrapeEffect(m.hb.cX, m.hb.cY), 0.1F));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new DrawCallbackShuffleAction(magicNumber, c -> c.cost == 0));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeDamage(3);
            upgradeName();
            upgradeMagicNumber(1);
        }

    }

    public AbstractCard makeCopy() {
        return new ScrapePlus();
    }
}