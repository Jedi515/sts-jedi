/* Warning: if you're reading this, you're either curious on how i did it or something went wrong.
* wild guess - something went horribly wrong.
* well here's a thing - i have almost fully copied stuff from Conspire - Bottled Yo-Yo.
* You can check it here https://github.com/twanvl/sts-conspire/blob/master/src/main/java/conspire/relics/BottledYoYo.java
* ...somebody help my soul.
* */

package mod.jedi.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import mod.jedi.patches.JediBottleFields;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mod.jedi.util.TextureLoader;

import java.util.function.Predicate;

public class LeadLinedBottle
    extends CustomRelic
    implements CustomBottleRelic,
        CustomSavable<Integer>
{
    public static final String ID = "jedi:leadlinedbottle";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    private boolean cardSelected = true;
    public AbstractCard card = null;


    public LeadLinedBottle() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON,LandingSound.CLINK);
    }

    public void atPreBattle() {
        if (AbstractDungeon.player.drawPile.contains(card) && card != null)
        {
            this.flash();
            AbstractCard cardToMove = null;

            for (AbstractCard c : AbstractDungeon.player.drawPile.group)
            {
                if (JediBottleFields.inLeadLinedBottle.get(c))
                {
                    cardToMove = c;
                    break;
                }
            }
            AbstractDungeon.player.drawPile.removeCard(cardToMove);
            AbstractDungeon.player.drawPile.addToBottom(cardToMove);

    //Don't ask me why i have to remove the card first and then add it back if it's "move" and not "add", i don't know how it works either. but it does. mostly.
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.player.hand.refreshHandLayout();
        }
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
        AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[1] + name + ".", false, false, false, false);
    }

    @Override
    public void onUnequip() {
        if (card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);
            if (cardInDeck != null) {
                JediBottleFields.inLeadLinedBottle.set(cardInDeck, false);
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

    @Override
    public void onLoad(Integer index) {
        if (index != null && index >= 0 && index < AbstractDungeon.player.masterDeck.group.size()) {
            this.card = AbstractDungeon.player.masterDeck.group.get(index);
            JediBottleFields.inLeadLinedBottle.set(this.card, true);
            this.setDescriptionAfterLoading();
        }
    }

    @Override
    public Class<Integer> savedType() {
        return Integer.class;
    }

    public void update() {
        super.update();
        if (!this.cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            this.cardSelected = true;
            this.card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            JediBottleFields.inLeadLinedBottle.set(this.card, true);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.setDescriptionAfterLoading();
        }
    }

    public void setDescriptionAfterLoading() {
        this.description = DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + DESCRIPTIONS[3];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LeadLinedBottle();
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return JediBottleFields.inLeadLinedBottle::get;
    }
}
