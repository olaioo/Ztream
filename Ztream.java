package br.com.memora.factura.util.ztream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Ztream<T> {
    private final Collection<T> coll;

    private Ztream(Collection<T> coll) {
        this.coll = Collections.unmodifiableCollection(coll);
    }

    public static <T> Ztream<T> of(Collection<T> coll) {
        return new Ztream<T>(coll);
    }

    public Ztream<T> sorted(Comparator<? super T> comparator) {
        List<T> list = new ArrayList<T>(coll);
        Collections.sort(list, comparator);
        return new Ztream<T>(list);
    }

    public Ztream<T> limit(long maxSize) {
        List<T> list = new ArrayList<T>();
        Iterator<T> i = coll.iterator();
        while (i.hasNext() && list.size() < maxSize){
            list.add(i.next());
        }
        return new Ztream<T>(list);
    }

    public Optional<T> findFirst() {
        Iterator<T> i = coll.iterator();
        return Optional.of(i.hasNext() ? i.next() : null);
    }

    public Ztream<T> filter(final Predicate<? super T> predicate) {
        List<T> list = new ArrayList<T>(coll);
        Iterator<T> i = list.iterator();
        while (i.hasNext()) {
            if (!predicate.test(i.next())) {
                i.remove();
            }
        }
        return new Ztream<T>(list);
    }

    public Ztream<T> peek(final Consumer<? super T> consumer) {
        for (T t : coll) {
            consumer.accept(t);
        }
        return this;
    }

    public void forEach(final Consumer<? super T> consumer) {
        peek(consumer);
    }

    public <O> Ztream<O> flatMap(final Function<? super T, ? extends Collection<? extends O>> function) {
        List<O> list = new ArrayList<O>();
        for (T t : coll) {
            list.addAll(function.apply(t));
        }
        return new Ztream<O>(list);
    }

    public <O> Ztream<O> map(final Function<? super T, ? extends O> function) {
        List<O> list = new ArrayList<O>();
        for (T t : coll) {
            list.add(function.apply(t));
        }
        return new Ztream<O>(list);
    }

    public <S extends Collection<T>> S collect(S accumulator) {
        if (accumulator != null) {
            accumulator.addAll(this.coll);
        }
        return accumulator;
    }

}
