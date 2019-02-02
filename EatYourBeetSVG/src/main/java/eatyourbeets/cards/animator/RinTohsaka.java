package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import eatyourbeets.actions.ModifyMagicNumberAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class RinTohsaka extends AnimatorCard
{
    public static final String ID = CreateFullID(RinTohsaka.class.getSimpleName());

    public RinTohsaka()
    {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);

        Initialize(0,0,1);

        SetSynergy(Synergies.Fate);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(AbstractOrb.getRandomOrb(true)));

        if (HasActiveSynergy())
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p, 1), 1));
        }
    }

    @Override
    public void upgrade() 
    {
        TryUpgrade();
    }
}