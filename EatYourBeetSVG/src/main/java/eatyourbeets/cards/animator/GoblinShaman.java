package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.relics.MedicalKit;
import eatyourbeets.GameActionsHelper;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.AnimatorCard_Status;
import eatyourbeets.cards.Synergies;

public class GoblinShaman extends AnimatorCard_Status
{
    public static final String ID = CreateFullID(GoblinShaman.class.getSimpleName());

    public GoblinShaman()
    {
        super(ID, 1, CardRarity.COMMON, CardTarget.NONE);

        Initialize(0,0);

        this.exhaust = true;
        SetSynergy(Synergies.GoblinSlayer);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        if (!this.dontTriggerOnUseCard)
        {
            if (p.hasRelic(MedicalKit.ID))
            {
                this.useMedicalKit(p);
            }
        }
        else
        {
            GameActionsHelper.ApplyPower(p, p, new FrailPower(p,1, false),1);
        }
    }
}