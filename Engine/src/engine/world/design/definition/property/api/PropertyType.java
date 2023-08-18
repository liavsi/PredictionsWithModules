package engine.world.design.definition.property.api;

public enum PropertyType {
    DECIMAL {
        public Integer convert(Object value) {
         try{
             double res = Double.parseDouble(value.toString());
             return (int) res;
         }catch (NumberFormatException e){
             throw new IllegalArgumentException("Can't convert this value to int");
         }
        }
    }, BOOLEAN {
        @Override
        public Boolean convert(Object value) {
            try{
                boolean res = Boolean.parseBoolean(value.toString());
                return res;
            }catch (NumberFormatException e){
                throw new IllegalArgumentException("Can't convert this value to boolean");
            }
        }
    }, FLOAT {
        @Override
        public Float convert(Object value) {
            try{
                float res = Float.parseFloat(value.toString());
                return res;
            }catch (NumberFormatException e){
                throw new IllegalArgumentException("Can't convert this value to float");
            }
        }
    }, STRING {
        @Override
        public String convert(Object value) {
            String res = value.toString();
            return res;
        }
    };

    public abstract <T> T convert(Object value);
}