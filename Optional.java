package br.com.memora.factura.util.ztream;

import java.util.NoSuchElementException;

public class Optional<T> {
    private final T value;

    private Optional(T value){
        this.value=value;
    }

    public static <T> Optional<T> of(T value){
        return new Optional<T>(value);
    }

    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    public boolean isPresent() {
        return value != null;
    }

    public T orElse(T other) {
        return value != null ? value : other;
    }

    public <X extends Throwable> T orElseThrow(X exception) throws X {
        if (value != null) {
            return value;
        } else {
            throw exception;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Optional)) {
            return false;
        }

        Optional<?> other = (Optional<?>) obj;
        return value == other.value;
    }

    @Override
    public String toString() {
        return value != null
                ? String.format("Optional[%s]", value)
                : "Optional.empty";
    }
}
