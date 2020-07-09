package mod.jedi.cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Anger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.jedi.cards.CustomJediCard;

public class Suffering
        extends CustomJediCard
{
    public static final String ID = "jedi:suffering";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 2;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta_attack.png";


    public Suffering()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.magicNumber = this.baseMagicNumber = 2;
        this.baseDamage = this.damage = 0;
        this.isMultiDamage = true;
        this.exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m)
    {
        this.damage = this.baseDamage;
        if (this.damage > 0) {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    public void applyPowers() {
        super.applyPowers();
        this.baseDamage = 0;
        this.damage = 0;
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group)
        {
            if (c.type == CardType.ATTACK)
            {
                this.baseDamage += this.magicNumber;
            }
        }
        this.damage = this.baseDamage;
        if (this.baseDamage > 0) {
            this.rawDescription = DESCRIPTION + UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }


    }

    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group)
        {
            if (c.type == CardType.ATTACK)
            {
                this.baseDamage += this.magicNumber;
            }
        }
        this.damage = this.baseDamage;
        if (this.baseDamage > 0) {
            this.rawDescription = DESCRIPTION + UPGRADE_DESCRIPTION;
        }

        this.initializeDescription();
    }
    public CustomCard makeCopy()
    {
        return new Suffering();
    }
}
