package jedi.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import jedi.interfaces.onGenerateCardMidcombatInterface;
import jedi.util.TextureLoader;

public class HeartOfTheCards
    extends AbstractJediRelic
    implements onGenerateCardMidcombatInterface
{

    public static final String ID = "jedi:HeartOfTheCards";
    private boolean triggered;
    private boolean statusTrigger;

    public HeartOfTheCards()
    {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT);
    }
    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart()
    {
        triggered = false;
    }

    public void onCreateCard(AbstractCard card)
    {
        if (statusTrigger && card.type == AbstractCard.CardType.STATUS)
        {
            addToBot(new AbstractGameAction()
            {
                @Override
                public void update()
                {
                    CardGroup crdGroup = null;
                    if (AbstractDungeon.player.discardPile.contains(card))
                    {
                        crdGroup = AbstractDungeon.player.discardPile;
                    }
                    if (AbstractDungeon.player.drawPile.contains(card))
                    {
                        crdGroup = AbstractDungeon.player.drawPile;
                    }
                    if (AbstractDungeon.player.hand.contains(card))
                    {
                        crdGroup = AbstractDungeon.player.hand;
                    }
                    if (crdGroup != null)
                    {
                        addToTop(new ExhaustSpecificCardAction(card, crdGroup));
                    }
                    isDone = true;
                }
            });
        }

        if (triggered) return;

        switch (card.type)
        {
            case ATTACK:
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 2)));
                break;
            case SKILL:
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 2)));
                break;
            case POWER:
                addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 4));
                break;
            case STATUS:
                addToBot(new AbstractGameAction()
                {
                    @Override
                    public void update()
                    {
                        CardGroup crdGroup = null;
                        if (AbstractDungeon.player.discardPile.contains(card))
                        {
                            crdGroup = AbstractDungeon.player.discardPile;
                        }
                        if (AbstractDungeon.player.drawPile.contains(card))
                        {
                            crdGroup = AbstractDungeon.player.drawPile;
                        }
                        if (AbstractDungeon.player.hand.contains(card))
                        {
                            crdGroup = AbstractDungeon.player.hand;
                        }
                        if (crdGroup != null)
                        {
                            addToTop(new ExhaustSpecificCardAction(card, crdGroup));
                        }
                        isDone = true;
                    }
                });
                statusTrigger = true;
                break;
            case CURSE:
                addToBot(new AbstractGameAction()
                {
                    @Override
                    public void update()
                    {
                        AbstractDungeon.player.increaseMaxHp(2, true);
                        isDone = true;
                    }
                });
        }
        triggered = true;
    }

    @Override
    public void atTurnStartPostDraw()
    {
        statusTrigger = false;
    }
}
