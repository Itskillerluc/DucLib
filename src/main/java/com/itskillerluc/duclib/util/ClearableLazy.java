package com.itskillerluc.duclib.util;

import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface ClearableLazy<T> extends Supplier<T> {
    void invalidate();

    void invalidateAndGet();

    static <T> ClearableLazy<T> of(@NotNull Supplier<T> supplier)
    {
        return new ClearableLazy.Fast<T>(supplier);
    }

    /**
     * Constructs a thread-safe lazy-initialized object
     * @param supplier The supplier for the value, to be called the first time the value is needed.
     */
    static <T> ClearableLazy<T> concurrentOf(@NotNull Supplier<T> supplier)
    {
        return new ClearableLazy.Concurrent<T>(supplier);
    }

    final class Fast<T> implements ClearableLazy<T>
    {
        private Supplier<T> supplier;
        private T instance;

        private Fast(Supplier<T> supplier)
        {
            this.supplier = supplier;
        }

        @Nullable
        @Override
        public final T get()
        {
            if (instance == null)
            {
                instance = supplier.get();
            }
            return instance;
        }

        @Override
        public void invalidate() {
            instance = null;
        }

        @Override
        public void invalidateAndGet() {
            instance = supplier.get();
        }
    }

    final class Concurrent<T> implements ClearableLazy<T>
    {
        private volatile Object lock = new Object();
        private volatile Supplier<T> supplier;
        private volatile T instance;

        private Concurrent(Supplier<T> supplier)
        {
            this.supplier = supplier;
        }

        @Nullable
        @Override
        public final T get()
        {
            // Copy the lock to a local variable to prevent NPEs if the lock field is set to null between the
            // null-check and the synchronization
            Object localLock = this.lock;
            if (instance == null)
            {
                // localLock is not null here because supplier was non-null after we copied the lock and both of them
                // are volatile
                synchronized (localLock)
                {
                    if (instance == null)
                    {
                        instance = supplier.get();
                        this.lock = null;
                    }
                }
            }
            return instance;
        }

        @Override
        public void invalidate() {
            Object localLock = this.lock;
            synchronized (localLock){
                instance = null;
            }
        }

        @Override
        public void invalidateAndGet() {
            Object localLock = this.lock;
            synchronized (localLock){
                instance = supplier.get();
            }
        }
    }
}
