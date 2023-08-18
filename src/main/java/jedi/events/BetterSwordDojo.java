package jedi.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import jedi.cardMods.ImperfectMod;
import jedi.cards.ignore.ImperfectModCard;
import jedi.relics.StrikeManual;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class BetterSwordDojo extends PhasedEvent {
    public static final String ID = "jedi:sworddojo";
    public static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String NAME = eventStrings.NAME;

    private static final int xOffset = Settings.WIDTH/4;
    private static final int yOffset = Settings.HEIGHT/4;
    public BetterSwordDojo() {
        super(ID, NAME, "jedi/images/events/SwordDojo.jpg");
        registerPhase("Start", new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase.OptionInfo(OPTIONS[0], new ImperfectModCard())
                                .enabledCondition(BetterSwordDojo::hasStrikes, OPTIONS[1]), i ->
                    {
                        getStrikes().forEach(c -> {
                            CardModifierManager.addModifier(c, new ImperfectMod(1, false));
                            int xpos = (int)(Math.random() * Settings.WIDTH/2 + xOffset);
                            int ypos = (int)(Math.random() * Settings.HEIGHT/2 + yOffset);
                            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), xpos, ypos));
                            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(xpos, ypos));
                        });
                        transitionKey("StrikeKnowledge");
                    })
                .addOption(new TextPhase.OptionInfo(OPTIONS[2], new StrikeManual()), i ->
                    {
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new StrikeManual()));
                        noCardsInRewards = true;
                        AbstractDungeon.combatRewardScreen.open();
                        noCardsInRewards = false;
                        transitionKey("Relic");
                    })
                .addOption(OPTIONS[3], i -> {
                        transitionKey("Nope");
                    })
        );
        registerPhase("StrikeKnowledge", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[3], (t) -> this.openMap()));
        registerPhase("Relic", new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[3], (t) -> this.openMap()));
        registerPhase("Nope", new TextPhase(DESCRIPTIONS[3]).addOption(OPTIONS[3], (t) -> this.openMap()));

        transitionKey("Start");
    }

    private static boolean hasStrikes()
    {
        return getStrikes().size() > 0;
    }

    private static ArrayList<AbstractCard> getStrikes()
    {
        return (AbstractDungeon.player.masterDeck.group.stream().filter(c -> c.rarity == AbstractCard.CardRarity.BASIC && c.hasTag(AbstractCard.CardTags.STRIKE)).collect(Collectors.toCollection(ArrayList::new)));
    }
}
