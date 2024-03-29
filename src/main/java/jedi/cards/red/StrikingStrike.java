package jedi.cards.red;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.cards.CustomJediCard;

import java.util.ArrayList;

import static jedi.jedi.returnTrulyRandomStrike;

//If you're curious on how does it double increase perfected strike - go patches/strikingstrikepatch.java
public class StrikingStrike
    extends CustomJediCard
{
    public static final String ID = "jedi:strikingstrike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final int COST = 1;
    private TooltipInfo tip = new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1]);

    public StrikingStrike()
    {
        super(ID, NAME, COST, DESCRIPTION, CardType.ATTACK, AbstractCard.CardColor.RED, AbstractCard.CardRarity.RARE, CardTarget.ENEMY);
        damage = 6;
        baseDamage = damage;
        tags.add(CardTags.STRIKE);
        exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        AbstractCard card = returnTrulyRandomStrike().makeCopy();
        if (upgraded)
        {
            card.upgrade();
        }
        card.freeToPlayOnce = true;
        card.applyPowers();
        card.calculateCardDamage(m);
        card.purgeOnUse = true;

        cardsToPreview = card.makeStatEquivalentCopy();

        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(card, m));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void applyPowers()
    {
        tags.remove(CardTags.STRIKE);
        super.applyPowers();
        int strikelessDmg = damage;
        tags.add(CardTags.STRIKE);
        super.applyPowers();
        damage += (damage - strikelessDmg);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo)
    {
        tags.remove(CardTags.STRIKE);
        super.calculateCardDamage(mo);
        int strikelessDmg = damage;
        tags.add(CardTags.STRIKE);
        super.calculateCardDamage(mo);
        damage += (damage - strikelessDmg);
    }

    @Override
    public ArrayList<TooltipInfo> getCustomTooltips()
    {
        ArrayList<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(tip);
        return retVal;
    }

    public AbstractCard makeCopy()
    {
        return new StrikingStrike();
    }
}
