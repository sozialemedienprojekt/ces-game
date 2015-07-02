package de.hub.cses.ces.util.collection;

/*
 * #%L
 * CES-Game
 * %%
 * Copyright (C) 2015 Humboldt-Universität zu Berlin,
 * Department of Computer Science,
 * Research Group "Computer Science Education / Computer Science and Society"
 * Sebastian Gross <sebastian.gross@hu-berlin.de>
 * Sven Strickroth <sven.strickroth@hu-berlin.de>
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 * @param <T>
 */
public final class ThreadSafeSizeLimitedCollection<T> implements Collection<T> {

    private final int maxEntries;
    private final Collection<T> collection = Collections.newSetFromMap(new LinkedHashMap<T, Boolean>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<T, Boolean> eldest) {
            return size() > maxEntries;
        }
    });
    private final ReadWriteLock collectionLock = new ReentrantReadWriteLock();

    /**
     *
     */
    public ThreadSafeSizeLimitedCollection() {
        this(Integer.MAX_VALUE);
    }

    /**
     *
     * @param maxEntries
     */
    public ThreadSafeSizeLimitedCollection(int maxEntries) {
        this.maxEntries = maxEntries;
    }

    @Override
    public boolean add(T t) {
        Lock writeLock = collectionLock.writeLock();
        try {
            writeLock.lock();
            collection.add(t);
            return true;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public int size() {
        Lock readLock = collectionLock.readLock();
        try {
            readLock.lock();
            return collection.size();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        Lock readLock = collectionLock.readLock();
        try {
            readLock.lock();
            return collection.isEmpty();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean contains(Object o) {
        Lock readLock = collectionLock.readLock();
        try {
            readLock.lock();
            if (collection.stream().anyMatch((notification) -> (notification.equals(o)))) {
                return true;
            }
        } finally {
            readLock.unlock();
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        Lock readLock = collectionLock.readLock();
        try {
            readLock.lock();
            return collection.iterator();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Object[] toArray() {
        Lock readLock = collectionLock.readLock();
        try {
            readLock.lock();
            return collection.toArray();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public T[] toArray(Object[] a) {
        Lock readLock = collectionLock.readLock();
        try {
            readLock.lock();
            return collection.toArray((T[]) Array.newInstance(a.getClass(), collection.size()));
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean remove(Object o) {
        Lock writeLock = collectionLock.writeLock();
        try {
            writeLock.lock();
            for (T notification : collection) {
                if (notification.equals(o)) {
                    collection.remove(notification);
                    return true;
                }
            }
        } finally {
            writeLock.unlock();
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        Lock readLock = collectionLock.readLock();
        try {
            readLock.lock();
            return collection.containsAll(c);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean addAll(Collection c) {
        Lock writeLock = collectionLock.writeLock();
        try {
            writeLock.lock();
            collection.addAll(c);
        } finally {
            writeLock.unlock();
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        Lock writeLock = collectionLock.writeLock();
        try {
            writeLock.lock();
            return collection.removeAll(c);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean retainAll(Collection c) {
        Lock writeLock = collectionLock.writeLock();
        try {
            writeLock.lock();
            return collection.retainAll(c);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void clear() {
        Lock writeLock = collectionLock.writeLock();
        try {
            writeLock.lock();
            collection.clear();
        } finally {
            writeLock.unlock();
        }
    }

}
