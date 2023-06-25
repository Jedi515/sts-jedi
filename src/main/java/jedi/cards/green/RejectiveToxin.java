package jedi.cards.green;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import jedi.cards.CustomJediCard;

public class RejectiveToxin
    extends CustomJediCard
{
    public static final String ID = makeCardId("rejectivetoxin");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final int COST = 2;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta.png";

    public RejectiveToxin()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.SKILL, CardColor.GREEN, CardRarity.RARE, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = 3;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        //AbstractDungeon.actionManager.addToBottom(new RejectiveToxinAction(m, this.magicNumber, this.upgraded));
        addToBot(new SelectCardsInHandAction(99, EXTENDED_DESCRIPTION[0], upgraded, upgraded, c -> true, list -> {
                                                                                                                        list.forEach(c ->
                                                                                                                        {
                                                                                                                            addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, magicNumber)));
                                                                                                                            AbstractDungeon.player.hand.moveToDiscardPile(c);
                                                                                                                            GameActionManager.incrementDiscard(false);
                                                                                                                            c.triggerOnManualDiscard();
                                                                                                                        });
                                                                                                                        list.clear();
        }));
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
