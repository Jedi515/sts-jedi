package mod.jedi.relics;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mod.jedi.patches.RetainUseCardPatch;
import mod.jedi.patches.JediBottleFields;
import mod.jedi.util.TextureLoader;

import java.util.function.Predicate;

public class BottledFury
    extends CustomRelic
    implements CustomBottleRelic,
        CustomSavable<Integer>
{
    public static final String ID = "jedi:bottledfury";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    private boolean cardSelected = true;
    public AbstractCard card = null;

    public BottledFury()
    {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return JediBottleFields.inBottledFury::get;
    }

    @Override
    public void onEquip() {
        cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup group = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck);
        group.group.removeIf(c -> c.hasTag(AbstractCard.CardTags.HEALING));
        AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[1] + name + ".", false, false, false, false);
    }

    @Override
    public void onMonsterDeath(AbstractMonster m)
    {
        if (m.currentHealth == 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead() && !AbstractDungeon.player.hand.contains(card))
        {
            this.flash();
            AbstractCard crd = null;
            for (AbstractGameAction GA : AbstractDungeon.actionManager.actions)
            {
                if (GA instanceof UseCardAction)
                {
                    crd = (AbstractCard) ReflectionHacks.getPrivate(GA, UseCardAction.class, "targetCard");
                    break;
                }
            }
            if (crd != null)
            {
                if (JediBottleFields.inBottledFury.get(crd))
                {
                    RetainUseCardPatch.RetainCard = true;
                }
            }

            for (AbstractCard c : AbstractDungeon.player.discardPile.group)
            {
                if (JediBottleFields.inBottledFury.get(c))
                {
                    AbstractDungeon.actionManager.addToBottom(new FetchAction(AbstractDungeon.player.discardPile, card -> c == card));
                }
            }
            for (AbstractCard c : AbstractDungeon.player.drawPile.group)
            {
                if (JediBottleFields.inBottledFury.get(c))
                {
                    AbstractDungeon.actionManager.addToBottom(new FetchAction(AbstractDungeon.player.drawPile, card -> c == card));
                }
            }
            for (AbstractCard c : AbstractDungeon.player.exhaustPile.group)
            {
                if (JediBottleFields.inBottledFury.get(c))
                {
                    AbstractDungeon.actionManager.addToBottom(new FetchAction(AbstractDungeon.player.exhaustPile, card -> c == card));
                }
            }
        }
    }

    public void update() {
        super.update();
        if (!this.cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            this.cardSelected = true;
            this.card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            JediBottleFields.inBottledFury.set(this.card, true);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.setDescriptionAfterLoading();
        }
    }

    @Override
    public void onUnequip() {
        if (card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);
            if (cardInDeck != null) {
                JediBottleFields.inBottledFury.set(cardInDeck, false);
            }
        }
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public Integer onSave() {
        if (this.card != null) {
            return AbstractDungeon.player.masterDeck.group.indexOf(this.card);
        } else {
            return -1;
        }
    }

    public void setDescriptionAfterLoading() {
        this.description = DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + DESCRIPTIONS[3];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void onLoad(Integer index) {
        if (index != null && index >= 0 && index < AbstractDungeon.player.masterDeck.group.size()) {
            this.card = AbstractDungeon.player.masterDeck.group.get(index);
            JediBottleFields.inBottledFury.set(this.card, true);
            this.setDescriptionAfterLoading();
        }
    }

    @Override
    public Class<Integer> savedType() {
        return Integer.class;
    }
}
