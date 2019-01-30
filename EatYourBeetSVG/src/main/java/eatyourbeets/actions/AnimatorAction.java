package eatyourbeets.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import eatyourbeets.cards.AnimatorCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public abstract class AnimatorAction extends AbstractGameAction
{
    protected static final Logger logger = LogManager.getLogger(AnimatorAction.class.getName());
}
