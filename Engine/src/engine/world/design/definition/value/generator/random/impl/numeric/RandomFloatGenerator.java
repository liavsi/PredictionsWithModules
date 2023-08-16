package engine.world.design.definition.value.generator.random.impl.numeric;

public class RandomFloatGenerator extends AbstractNumericRandomGenerator<Float> {

    public RandomFloatGenerator(Float from, Float to) {
        super(from, to);
    }
    @Override
    public Float generateValue() {
        return random.nextFloat() * (from - to) + from;
    }
}
