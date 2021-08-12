package br.com.memora.factura.util.ztream;

import java.util.Arrays;
import java.util.List;

class Holder {
    private String value;

    public Holder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Holder" + hashCode() + "{" +
                "value='" + value + '\'' +
                '}';
    }
}

public class MainTest {
    public static void main(String[] args) {
        List<Holder> b = Arrays.asList(
                new Holder(null),
                new Holder("b"),
                new Holder("c"),
                new Holder("d"));

        Ztream.of(b)
                .filter(new Predicate<Holder>() {
                    @Override
                    public boolean test(Holder holder) {
                        return Optional.of(holder.getValue()).orElse("aaa").length() > 0;
                    }
                })
                .limit(2)
                .map(new Function<Holder, Holder>() {
                    @Override
                    public Holder apply(Holder holder) {
                        return new Holder(holder.getValue() + "1");
                    }
                })
                .forEach(new Consumer<Holder>() {
                    @Override
                    public void accept(Holder holder) {
                        System.out.println(holder);
                    }
                });

        System.out.println(b);
    }
}
