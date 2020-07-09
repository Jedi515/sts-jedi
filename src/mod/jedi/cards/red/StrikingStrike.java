package mod.jedi.cards.red;

import basemod.abstracts.CustomCard;
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
import mod.jedi.cards.CustomJediCard;

import static mod.jedi.jedi.returnTrulyRandomStrike;

//If you're curious on how does it double increase perfected strike - go patches/strikingstrikepatch.java
public class StrikingStrike
    extends CustomJediCard
{
    public static final String ID = "jedi:strikingstrike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 1;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta_attack.png";

    public StrikingStrike()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCard.CardColor.RED, AbstractCard.CardRarity.RARE, CardTarget.ENEMY);
        this.damage = 6;
        this.baseDamage = this.damage;
        this.tags.add(CardTags.STRIKE);
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        AbstractCard card = returnTrulyRandomStrike().makeCopy();
        if (this.upgraded)
        {
            card.upgrade();
        }
        card.freeToPlayOnce = true;
        card.applyPowers();
        card.calculateCardDamage(m);
        card.purgeOnUse = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(card, m));
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public AbstractCard makeCopy()
    {
        return new StrikingStrike();
    }
}
