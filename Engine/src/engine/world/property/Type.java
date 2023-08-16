package engine.world.property;

 import java.util.Random;
public enum Type {
    DECIMAL {
        @Override
        public String toString() {
            return "decimal";
        }

        @Override
        public Object randomValue(Restriction restriction) {
            Random r = new Random();
            int from = (int)restriction.getFrom();
            int to = (int)restriction.getTo();
            return r.nextInt(to - from + 1) + from;
        }
    },
    FLOAT {
        @Override
        public String toString() {
            return "float";
        }

        @Override
        public Object randomValue(Restriction restriction) {
            Random r = new Random();
            float from = restriction.getFrom();
            float to = restriction.getTo();
            return from + r.nextFloat() * (to - from);
        }
    },
    BOOLEAN {
        @Override
        public String toString() {
            return "boolean";
        }

        @Override
        public Object randomValue(Restriction restriction) {
            Random r = new Random();
            return r.nextBoolean();
        }
    },
    STRING {
        @Override
        public String toString() {
            return "string";
        }

        @Override
        public Object randomValue(Restriction restriction) {
            Random r = new Random();
            int len = r.nextInt(50 - 1 + 1) + 1;
            StringBuilder res = new StringBuilder(len);
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!?,_-.() ";
            for (int i = 0; i < len; i++) {
                int ind = r.nextInt(chars.length());
                char randomChar = chars.charAt(ind);
                res.append(randomChar);
            }
            return res;
        }
    };

    public abstract String toString();
    public abstract Object randomValue(Restriction restriction);

}
