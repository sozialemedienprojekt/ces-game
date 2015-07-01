package de.hub.cses.ces.service.persistence;

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
import de.hub.cses.ces.entity.BaseEntity;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 * @param <T>
 */
public abstract class AbstractFacade<T extends BaseEntity> {

    private final Class<T> entityClass;

    /**
     *
     * @param entityClass
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     *
     * @return
     */
    protected abstract EntityManager getEntityManager();

    /**
     *
     * @param entity
     */
    public void create(T entity) {
        create(entity, false);
    }

    /**
     *
     * @param entity
     * @param flush
     */
    public void create(T entity, boolean flush) {
        getEntityManager().persist(entity);
        if (flush) {
            getEntityManager().flush();
        }
    }

    /**
     *
     * @param entity
     * @return
     */
    public T edit(T entity) {
        return edit(entity, false);
    }

    /**
     *
     * @param entity
     * @param flush
     * @return
     * @throws OptimisticLockException
     * @throws org.eclipse.persistence.exceptions.OptimisticLockException
     */
    public T edit(T entity, boolean flush) throws javax.persistence.OptimisticLockException, org.eclipse.persistence.exceptions.OptimisticLockException {
        entity = getEntityManager().merge(entity);
        if (flush) {
            getEntityManager().flush();
        }
        return entity;
    }

    /**
     *
     * @param entity
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     *
     * @param id
     * @param detach
     * @return
     */
    public T find(Object id, boolean detach) {
        T entity = getEntityManager().find(entityClass, id);
        if (detach) {
            getEntityManager().detach(entity);
        }
        return entity;
    }

    /**
     *
     * @param id
     * @param lockModeType
     * @return
     */
    public T find(Object id, LockModeType lockModeType) {
        return getEntityManager().find(entityClass, id, lockModeType);
    }

    /**
     *
     * @param id
     * @return
     */
    public T find(Object id) {
        return find(id, false);
    }

    /**
     *
     * @return
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));

        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     *
     * @param range
     * @return
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     *
     * @return
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    /**
     *
     * @param entity
     * @return
     */
    public boolean contains(T entity) {
        return getEntityManager().contains(entity);
    }

    /**
     *
     * @param entity
     * @param lockModeType
     */
    public void lock(T entity, LockModeType lockModeType) {
        getEntityManager().lock(entity, lockModeType);
    }

    /**
     *
     * @param t
     */
    public void refresh(T t) {
        getEntityManager().refresh(t);
    }

    /**
     *
     * @param ts
     */
    public void refresh(Collection<T> ts) {
        if (ts != null && !ts.isEmpty()) {
            for (T t : ts) {
                getEntityManager().refresh(t);
            }
        }
    }

    /**
     *
     */
    public void flush() {
        getEntityManager().flush();
    }

}
