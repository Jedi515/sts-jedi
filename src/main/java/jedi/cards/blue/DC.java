package jedi.cards.blue;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import jedi.cards.CustomJediCard;
import jedi.jedi;
import jedi.util.Wiz;

public class DC
    extends CustomJediCard
{
    public static final String ID = jedi.makeID(DC.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public DC() {
        super(ID, NAME, 1, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        selfRetain = true;
        exhaust = true;
    }

    @Override
    public void upp() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Lightning orb = new Lightning();
        addToBot(new ChannelAction(orb));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (!Wiz.adp().orbs.contains(orb)) return;
                if (upgraded) orb.onEndOfTurn();
                orb.onEvoke();
            }
        });
    }
}
