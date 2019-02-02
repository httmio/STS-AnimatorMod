package eatyourbeets.cards.animator;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

import java.util.ArrayList;

public class Emonzaemon extends AnimatorCard
{
    public static final String ID = CreateFullID(Emonzaemon.class.getSimpleName());

    public Emonzaemon()
    {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

        Initialize(7,0, 1);

        String[] info = this.cardStrings.EXTENDED_DESCRIPTION;
        AddTooltip(new TooltipInfo(info[0], info[1]));

        SetSynergy(Synergies.Katanagatari);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.FIRE));

        ArrayList<AbstractCard> cardsPlayed = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        int size = cardsPlayed.size();
        if (size >= 3)
        {
            boolean threeInARow = true;
            for (int i = 1; i <= 3; i++)
            {
                if (cardsPlayed.get(size - i).type != CardType.ATTACK)
                {
                    threeInARow = false;
                }
            }

            if (threeInARow)
            {
                EntouJyuu toAdd = new EntouJyuu();
                if (upgraded)
                {
                    toAdd.upgrade();
                }
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(toAdd));
                this.purgeOnUse = true;
            }
        }
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {
            upgradeDamage(2);
        }
    }
}