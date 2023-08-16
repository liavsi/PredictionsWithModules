package engine.world.design.definition.environment.api;

import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.execution.environment.api.ActiveEnvironment;

import java.util.Collection;

public interface EnvVariablesManager {
    void addEnvironmentVariable(PropertyDefinition propertyDefinition);
    ActiveEnvironment createActiveEnvironment();
    Collection<PropertyDefinition> getEnvVariables();
}
