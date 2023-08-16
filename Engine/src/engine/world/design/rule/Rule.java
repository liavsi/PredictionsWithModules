package engine.world.design.rule;

import engine.world.design.action.api.Action;
import engine.world.design.rule.activation.api.Activation;

import java.util.List;

public interface Rule {
    String getName();
    Activation getActivation();
    List<Action> getActionToPreform();
    void addAction(Action action);
}
