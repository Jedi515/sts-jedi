package mod.jedi.relics;

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
import mod.jedi.interfaces.onGenerateCardMidcombatInterface;
import mod.jedi.util.TextureLoader;

public class HeartOfTheCards
    extends AbstractJediRelic
    implements onGenerateCardMidcombatInterface
{

    public static final String ID = "jedi:HeartOfTheCards";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    private boolean triggered;

    public HeartOfTheCards()
    {
        super(ID, RelicTier.COMMON, LandingSound.FLAT);
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
            default:
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
        triggered = true;
    }
}
