package eatyourbeets.cards.animator;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Gilgamesh extends AnimatorCard
{
    public static final String ID = CreateFullID(Gilgamesh.class.getSimpleName());
    public static final int GOLD_REWARD = 25;

    public Gilgamesh()
    {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);

        Initialize(3,0, 3);

        //AddTooltip(new TooltipInfo("Gate of Babylon", "Whenever you obtain a relic upgrade this card and gain #b"+GOLD_REWARD+" gold. Does not work when buying relics."));

        String[] info = this.cardStrings.EXTENDED_DESCRIPTION;
        AddTooltip(new TooltipInfo(info[0], info[1] + GOLD_REWARD + info[2]));
        SetSynergy(Synergies.Fate);
    }

    @Override
    public boolean canUpgrade()
    {
        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        for (int i = 0; i < this.magicNumber; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    @Override
    public void upgrade()
    {
        this.timesUpgraded += 1;
        this.upgradeDamage(1);
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }
}