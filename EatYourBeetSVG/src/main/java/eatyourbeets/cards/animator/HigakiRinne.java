package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.cards.colorless.Shiv;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.random.Random;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class HigakiRinne extends AnimatorCard
{
    public static final String ID = CreateFullID(HigakiRinne.class.getSimpleName());

    public HigakiRinne()
    {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.ALL);

        Initialize(0,0,2);

        SetSynergy(Synergies.Katanagatari, true);
    }

    @Override
    public void triggerOnExhaust()
    {
        super.triggerOnExhaust();

        int n = AbstractDungeon.miscRng.random(6);
        if (n == 0)
        {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv()));
        }
        else if (n == 1)
        {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Madness()));
        }
        else if (n == 2)
        {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Slimed()));
        }
    }

    @Override
    public void triggerWhenDrawn()
    {
        super.triggerWhenDrawn();

        int n = AbstractDungeon.miscRng.random(100);
        if (n < 3)
        {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(1f));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(makeCopy()));
        }
        else if (n < 10)
        {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(1f));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv()));
        }
        else if (n < 40)
        {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(1f));
            AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(this, AbstractDungeon.player.hand));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        for (int i = 0; i < this.magicNumber; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5f));
            ExecuteRandomAction(p, AbstractDungeon.miscRng);
        }
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {
            upgradeMagicNumber(1);
        }
    }

    private void ExecuteRandomAction(AbstractPlayer p, Random rng)
    {
        int n = rng.random(90);
        if (n < 10) // 10%
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, 4));
        }
        else if (n < 20) // 10%
        {
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(p, 4), AbstractGameAction.AttackEffect.POISON));
        }
        else if (n < 25) // 5%
        {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
        }
        else if (n < 30) // 5%
        {
            AbstractDungeon.actionManager.addToBottom(new UpgradeRandomCardAction());
        }
        else if (n < 35) // 5%
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPoisonOnRandomMonsterAction(p, 4, false, AbstractGameAction.AttackEffect.POISON));
        }
        else if (n < 40) // 5%
        {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
        else if (n < 45) // 5%
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
        }
        else if (n < 50) // 5%
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
        }
        else if (n < 55) // 5%
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
        }
        else if (n < 60) // 5%
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, 1), 1));
        }
        else if (n < 65) // 5%
        {
            AbstractMonster m = AbstractDungeon.getRandomMonster();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, 2, false), 2));
        }
        else if (n < 70) // 5%
        {
            AbstractMonster m = AbstractDungeon.getRandomMonster();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 2, false), 2));
        }
        else if (n < 75) // 5%
        {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv()));
        }
        else if (n < 80) // 5%
        {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Madness()));
        }
        else if (n < 85) // 5%
        {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Slimed()));
        }
        else if (n < 90) // 5%
        {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(CardLibrary.getRandomColorSpecificCard(this.color, rng)));
        }
    }
}