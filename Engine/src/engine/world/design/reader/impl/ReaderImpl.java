package engine.world.design.reader.impl;

import engine.world.design.action.api.Action;
import engine.world.design.action.api.ActionType;
import engine.world.design.action.condition.*;
import engine.world.design.action.impl.DecreaseAction;
import engine.world.design.action.impl.IncreaseAction;
import engine.world.design.action.impl.KillAction;
import engine.world.design.execution.entity.manager.EntityInstanceManager;
import engine.world.design.rule.Rule;
import engine.world.design.rule.RuleImpl;
import engine.world.design.termination.api.Termination;
import engine.world.design.termination.impl.TerminationImpl;
import engine.world.design.termination.second.Second;
import engine.world.design.termination.second.SecondImpl;
import engine.world.design.termination.tick.api.Tick;
import engine.world.design.termination.tick.impl.TickImpl;
import engine.world.design.world.api.World;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.entity.impl.EntityDefinitionImpl;
import engine.world.design.definition.environment.api.EnvVariablesManager;
import engine.world.design.definition.environment.impl.EnvVariablesManagerImpl;
import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.definition.property.impl.BooleanPropertyDefinition;
import engine.world.design.definition.property.impl.FloatPropertyDefinition;
import engine.world.design.definition.property.impl.IntegerPropertyDefinition;
import engine.world.design.definition.property.impl.StringPropertyDefinition;
import engine.world.design.definition.value.generator.api.ValueGeneratorFactory;
import engine.world.design.world.impl.WorldImpl;
import engine.world.design.reader.api.Reader;
import schema.generated.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReaderImpl implements Reader {

    World createdWorld;
    PRDWorld prdWorld;


    @Override
    // after this method The Instance has its own copy of World build from the XML
    public void readWorldFromXml(String XML_PATH, String JAXB_XML_PACKAGE_NAME) {
        createdWorld = new WorldImpl();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(XML_PATH);
            if (inputStream != null) {
                prdWorld = deserializedFrom(JAXB_XML_PACKAGE_NAME, inputStream);
                readPRDWorld();
            } else {
                // Handle the case when the resource is not found
                System.err.println("XML resource not found: " + XML_PATH);
            }
        } catch (JAXBException e) {
            // Handle JAXB exception
            e.printStackTrace();
        }
    }

//    @Override
//    // after this method The Instance has its own copy of World build from the XML
//    public void readWorldFromXml(String XML_PATH, String JAXB_XML_PACKAGE_NAME) {
//        createdWorld = new WorldImpl();
//        try {
//            InputStream inputStream = new FileInputStream(new File(XML_PATH));
//            prdWorld = deserializedFrom( JAXB_XML_PACKAGE_NAME, inputStream);
//            readPRDWorld();
//        }catch(JAXBException | FileNotFoundException e){
//            // TODO: 11/08/2023 deal with this problem - file not found or not an XML file message
//            e.printStackTrace();
//        }
//    }

    private static PRDWorld deserializedFrom(String JAXB_XML_PACKAGE_NAME, InputStream in)throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (PRDWorld) u.unmarshal(in);
    }

    private void readPRDWorld() {
        buildEntitiesFromPRD(prdWorld.getPRDEntities());
        buildEnvironmentFromPRD(prdWorld.getPRDEvironment());
        buildRulesFromPRD(prdWorld.getPRDRules());
        buildTerminationFromPRD(prdWorld.getPRDTermination());
    }

    private void buildTerminationFromPRD(PRDTermination prdTermination) {
        TerminationImpl termination = new TerminationImpl();
        for (Object prdTicksOrSeconds :prdTermination.getPRDByTicksOrPRDBySecond()) {
            if(prdTicksOrSeconds instanceof PRDByTicks) {
                Tick tick = new TickImpl(((PRDByTicks) prdTicksOrSeconds).getCount());
                termination.setTicks(tick);
            }
            if(prdTicksOrSeconds instanceof PRDBySecond) {
                Second second = new SecondImpl(((PRDBySecond) prdTicksOrSeconds).getCount());
                termination.setSecondsToPast(second);
            }
            else {
                throw new RuntimeException(prdTicksOrSeconds.toString() + "is of unexpected Class");
            }
        }
        createdWorld.setTermination(termination);
    }

    private void buildRulesFromPRD(PRDRules prdRules) {
        List<Rule> ruleList = new ArrayList<>();
        Action action;
        for (PRDRule prdRule: prdRules.getPRDRule()) {
            Rule currRule = new RuleImpl(prdRule.getName());
            for (PRDAction prdAction : prdRule.getPRDActions().getPRDAction()) {
                currRule.addAction(buildActionFromPRD(prdAction));
            }
            ruleList.add(currRule);
        }
        createdWorld.setRules(ruleList);

    }
    private Action buildActionFromPRD(PRDAction prdAction) {
        Action res;
        switch (prdAction.getType()) {
            case ("increase"):
                res = createIncreaseOrDecreaseAction(prdAction, ActionType.INCREASE);
                break;
            case ("decrease"):
                res = createIncreaseOrDecreaseAction(prdAction, ActionType.DECREASE);
                break;
            case ("calculation"):
                res = createcalCulationAction(prdAction);
                break;
            case ("condition"):
                res = null; // TODO: 16/08/2023  
                //   res = createConditionAction(prdAction); 
                break;
            case ("set"):
                res = createSetAction(prdAction);
                break;
            case ("kill"):
                res = createKillAction(prdAction);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + prdAction.getType());
        }
        return res;
    }

    private Action createSetAction(PRDAction prdAction) {
        // TODO: 11/08/2023 implement method and class
        return null;
    }


//    private Action createConditionAction(PRDAction prdAction) {
//        Action res = null;
//        Condition condition = null;
//        EntityDefinition mainEntity = createdWorld.getEntityDefinitionByName(prdAction.getEntity());
//        String singularity = prdAction.getPRDCondition().getSingularity();
//        switch (singularity) {
//            case "single":
//                EntityDefinition entity =  createdWorld.getEntityDefinitionByName(prdAction.getPRDCondition().getEntity());
//                String property = prdAction.getPRDCondition().getProperty();
//                String value = prdAction.getPRDCondition().getValue();
//                String operator = prdAction.getPRDCondition().getOperator();
//                condition = new SingleCondition(entity, property,value,operator);
//                res = new ConditionAction(mainEntity,condition);
//                break;
//            case "multiple":
//                String logical = prdAction.getPRDCondition().getLogical();
//                if(logical != "or" && logical != "and"){
//                    throw new RuntimeException("invalid logical value");
//                }
//                MultipleCondition multipleCondition = new MultipleCondition(mainEntity,logical);
//                for (PRDCondition prdCondition: prdAction.getPRDCondition().getPRDCondition()){
//                    multipleCondition.addCondition(createConditionAction(prdCondition));
//                }
//                break;
//            default:
//                throw new IllegalArgumentException(singularity + "is not a valid Condition Singularity");
//        }
//        for (PRDAction prdAction1: prdAction.getPRDThen().getPRDAction()){
//            res.getThanActions().add(buildActionFromPRD(prdAction1));
//        }
//        for (PRDAction prdAction1: prdAction.getPRDElse().getPRDAction()){
//            res.getElseActions().add(buildActionFromPRD(prdAction1));
//        }
//        List<PRDCondition> prdCondition = prdAction.getPRDCondition().getPRDCondition();
//
//        return res;
//    }
//
//    private Action createConditionAction(PRDCondition prdCondition) {
//        AbstractCondition res = null;
//        //EntityDefinition mainEntity = createdWorld.getEntityDefinitionByName(prdAction.getEntity());
//        String singularity = prdCondition.getSingularity();
//        switch (singularity) {
//            case "single":
//                EntityDefinition entity =  createdWorld.getEntityDefinitionByName(prdCondition.getEntity());
//                String property = prdCondition.getProperty();
//                String value = prdCondition.getValue();
//                String operator = prdCondition.getOperator();
//                res = new SingleCondition(mainEntity, entity, property,value,operator);
//                break;
//            case "multiple":
//                String logical = prdAction.getPRDCondition().getLogical();
//                if(logical != "or" && logical != "and"){
//                    throw new RuntimeException("invalid logical value");
//                }
//                MultipleCondition multipleCondition = new MultipleCondition(mainEntity,logical);
//                for (PRDCondition prdCondition: prdAction.getPRDCondition().getPRDCondition()){
//                    multipleCondition.addCondition(createConditionAction(prdCondition));
//                }
//                break;
//            default:
//                throw new IllegalArgumentException(singularity + "is not a valid Condition Singularity");
//        }
//        for (PRDAction prdAction1: prdAction.getPRDThen().getPRDAction()){
//            res.getThanActions().add(buildActionFromPRD(prdAction1));
//        }
//        for (PRDAction prdAction1: prdAction.getPRDElse().getPRDAction()){
//            res.getElseActions().add(buildActionFromPRD(prdAction1));
//        }
//        List<PRDCondition> prdCondition = prdAction.getPRDCondition().getPRDCondition();
//
//        return res;
//    }


    private Action createcalCulationAction(PRDAction prdAction) {
        // TODO: 11/08/2023 implement method  and class
        return null;
    }

    private Action createKillAction(PRDAction prdAction) {
        EntityDefinition mainEntity = createdWorld.getEntityDefinitionByName(prdAction.getEntity());
        Action res = new KillAction(mainEntity);
        return res;
    }


    private Action createIncreaseOrDecreaseAction(PRDAction prdAction, ActionType type) {
        Action res = null ;
        EntityDefinition mainEntity = createdWorld.getEntityDefinitionByName(prdAction.getEntity());
        String propertyName = prdAction.getProperty();
        String byExpression = prdAction.getBy();
        if(type == ActionType.INCREASE) {
            res = new IncreaseAction(mainEntity,propertyName,byExpression);
        }
        else if (type == ActionType.DECREASE) {
            res = new DecreaseAction(mainEntity,propertyName,byExpression);
        }
        return res;
    }


    @Override
    public World getWorld() {
        return createdWorld;
    }

    /**
     * this code is responsible for creating Property definition from the correct Type
     */
    private void buildEnvironmentFromPRD(PRDEvironment prdEvironment) {
        EnvVariablesManager envVariablesManager = new EnvVariablesManagerImpl();
        for(PRDEnvProperty prdEnvProperty: prdEvironment.getPRDEnvProperty()) {
            switch (prdEnvProperty.getType()) {
                case "decimal":
                    envVariablesManager.addEnvironmentVariable(createDecimalPropertyDefinition(prdEnvProperty));
                    break;
                case "float":
                    envVariablesManager.addEnvironmentVariable(createFloatPropertyDefinition(prdEnvProperty));
                    break;
                case "boolean":
                    envVariablesManager.addEnvironmentVariable(createBooleanPropertyDefinition(prdEnvProperty));
                    break;
                case "string":
                    envVariablesManager.addEnvironmentVariable(createStringPropertyDefinition(prdEnvProperty));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + prdEnvProperty.getType());
            }
        }
        createdWorld.setEnvVariablesManager(envVariablesManager);
    }
    private PropertyDefinition createStringPropertyDefinition(Object i_prdProperty) {
        PropertyDefinition res = null;
        if( i_prdProperty instanceof PRDEnvProperty) {
            PRDEnvProperty prdEnvProperty = (PRDEnvProperty) i_prdProperty;
            String name = prdEnvProperty.getPRDName();
            res = new StringPropertyDefinition(name, ValueGeneratorFactory.createRandomString());
        }
        else {
            if (i_prdProperty instanceof PRDProperty) {
                PRDProperty prdProperty = (PRDProperty) i_prdProperty;
                PRDValue prdValue = prdProperty.getPRDValue();
                String name = prdProperty.getPRDName();
                if(prdValue.isRandomInitialize()) {
                    res = new StringPropertyDefinition(name, ValueGeneratorFactory.createRandomString());
                }
                else {
                    res = new StringPropertyDefinition(name, ValueGeneratorFactory.createFixed(prdValue.getInit()));
                }
            }
        }
        if(res == null) {
            throw new IllegalArgumentException(i_prdProperty.toString() + "is not expected type");
        }
        return res;

    }
    private PropertyDefinition createBooleanPropertyDefinition(Object i_prdProperty) {
        PropertyDefinition res = null;
        if(i_prdProperty instanceof PRDEnvProperty) {
            PRDEnvProperty prdEnvProperty = (PRDEnvProperty) i_prdProperty;
            String name = prdEnvProperty.getPRDName();
            res = new BooleanPropertyDefinition(name, ValueGeneratorFactory.createRandomBoolean());
        }
        else if( i_prdProperty instanceof PRDProperty) {
            PRDProperty prdProperty = (PRDProperty) i_prdProperty;
            PRDValue prdValue = prdProperty.getPRDValue();
            String name = prdProperty.getPRDName();
            if(prdValue.isRandomInitialize()) {
                res = new BooleanPropertyDefinition(name, ValueGeneratorFactory.createRandomBoolean());
            }
            else {
                res = new BooleanPropertyDefinition(name, ValueGeneratorFactory.createFixed(PropertyType.BOOLEAN.convert(prdValue.getInit())));
            }
        }
        if(res == null) {
            throw new IllegalArgumentException(i_prdProperty.toString() + "is not expected type");
        }
        return res;
    }


    private PropertyDefinition createFloatPropertyDefinition(Object i_prdProperty) {
        PropertyDefinition res = null;
        if(i_prdProperty instanceof PRDEnvProperty) {
            PRDEnvProperty prdEnvProperty = (PRDEnvProperty) i_prdProperty;
            Float from = PropertyType.FLOAT.convert(prdEnvProperty.getPRDRange().getFrom());
            Float to = PropertyType.FLOAT.convert(prdEnvProperty.getPRDRange().getTo());
            String name = prdEnvProperty.getPRDName();
            res = new FloatPropertyDefinition(name, ValueGeneratorFactory.createRandomFloat(from, to));
        }
        else if( i_prdProperty instanceof PRDProperty) {
            PRDProperty prdProperty = (PRDProperty) i_prdProperty;
            PRDValue prdValue = prdProperty.getPRDValue();
            String name = prdProperty.getPRDName();
            Float from = PropertyType.FLOAT.convert(prdProperty.getPRDRange().getFrom());
            Float to = PropertyType.FLOAT.convert(prdProperty.getPRDRange().getTo());
            if(prdValue.isRandomInitialize()) {
                res = new FloatPropertyDefinition(name, ValueGeneratorFactory.createRandomFloat(from,to));
            }
            else {
                res = new FloatPropertyDefinition(name, ValueGeneratorFactory.createFixed(PropertyType.FLOAT.convert(prdValue.getInit())));
            }
        }
        if(res == null) {
            throw new IllegalArgumentException(i_prdProperty.toString() + "is not expected type");
        }
        return res;
    }
    private PropertyDefinition createDecimalPropertyDefinition(Object i_prdProperty) {
        PropertyDefinition res = null;
        if(i_prdProperty instanceof PRDEnvProperty) {
            PRDEnvProperty prdEnvProperty = (PRDEnvProperty) i_prdProperty;
            Integer from = PropertyType.DECIMAL.convert(prdEnvProperty.getPRDRange().getFrom());
            Integer to = PropertyType.DECIMAL.convert(prdEnvProperty.getPRDRange().getTo());
            String name = prdEnvProperty.getPRDName();
            res = new IntegerPropertyDefinition(name, ValueGeneratorFactory.createRandomInteger(from, to));
        }
        else if( i_prdProperty instanceof PRDProperty) {
            PRDProperty prdProperty = (PRDProperty) i_prdProperty;
            PRDValue prdValue = prdProperty.getPRDValue();
            String name = prdProperty.getPRDName();
            Integer from = PropertyType.DECIMAL.convert(prdProperty.getPRDRange().getFrom());
            Integer to = PropertyType.DECIMAL.convert(prdProperty.getPRDRange().getTo());
            if(prdValue.isRandomInitialize()) {
                res = new IntegerPropertyDefinition(name, ValueGeneratorFactory.createRandomInteger(from,to));
            }
            else {
                res = new IntegerPropertyDefinition(name, ValueGeneratorFactory.createFixed(PropertyType.DECIMAL.convert(prdValue.getInit())));
            }
        }
        if(res == null) {
            throw new IllegalArgumentException(i_prdProperty.toString() + "is not expected type");
        }
        return res;
    }

    /**
     * this code is responsible for creating the Entities from the PRD files
     */
    private void buildEntitiesFromPRD(PRDEntities prdEntities) {
        Map<String, EntityDefinition> entities = new HashMap<>();
        for (PRDEntity prdEntity: prdEntities.getPRDEntity()){
            EntityDefinition currEntity = new EntityDefinitionImpl(prdEntity.getName(), prdEntity.getPRDPopulation());
            for (PRDProperty prdProperty : prdEntity.getPRDProperties().getPRDProperty()) {
                switch (prdProperty.getType()) {
                    case "decimal":
                        currEntity.getProps().add(createDecimalPropertyDefinition(prdProperty));
                        break;
                    case "float":
                        currEntity.getProps().add(createFloatPropertyDefinition(prdProperty));
                        break;
                    case "boolean":
                        currEntity.getProps().add(createBooleanPropertyDefinition(prdProperty));
                        break;
                    case "string":
                        currEntity.getProps().add(createStringPropertyDefinition(prdProperty));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + prdProperty.getType());
                }
            }
            String entityName = prdEntity.getName();
            entities.put(entityName, currEntity);
        }
        createdWorld.setEntities(entities);
    }


}
