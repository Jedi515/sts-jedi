package mod.jedi.cards.purple;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import mod.jedi.cards.CustomJediCard;

public class MengElixir extends CustomJediCard
{
        public static final String ID = makeCardId("mengelixir");
        private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        public static final String NAME = cardStrings.NAME;
        public static final String DESCRIPTION = cardStrings.DESCRIPTION;
        public static final int COST = 2;
        public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta.png";

        public MengElixir()
        {
            super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.RARE, CardTarget.ENEMY);
            this.exhaust = true;
            this.magicNumber = this.baseMagicNumber = 3;
            this.tags.add(CardTags.HEALING);
        }

        @Override
        public void use(AbstractPlayer p, AbstractMonster m)
        {
            if (!m.hasPower(MarkPower.POWER_ID)) return;

            addToBot(new HealAction(p, p, m.getPower(MarkPower.POWER_ID).amount / this.magicNumber));
            addToBot(new RemoveSpecificPowerAction(m, p, MarkPower.POWER_ID));
        }

        @Override
        public void upgrade() {
            this.upgradeName();
            this.upgradeBaseCost(this.cost - 1);
            this.upgradeMagicNumber(-1);
        }

}
