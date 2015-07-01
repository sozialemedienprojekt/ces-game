package de.hub.cses.ces.service.simulation.accounting;

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
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named
@ApplicationScoped
public class AccountingSystem implements Serializable {

    private final Map<Long, Account> accounts = new HashMap<>();
    private final ReadWriteLock mapLock = new ReentrantReadWriteLock();

    /**
     *
     * @param companyId
     * @param wages
     */
    public void addWages(Long companyId, double wages) {
        Lock lock = mapLock.writeLock();
        try {
            lock.lock();
            Account account;
            if (accounts.containsKey(companyId)) {
                account = accounts.get(companyId);
            } else {
                account = new Account();
                accounts.put(companyId, account);
            }
            account.addWages(wages);
        } finally {
            lock.unlock();
        }
    }

    /**
     *
     * @param companyId
     * @param warehouseCharges
     */
    public void addWarehouseCharges(Long companyId, double warehouseCharges) {
        Lock lock = mapLock.writeLock();
        try {
            lock.lock();
            Account account;
            if (accounts.containsKey(companyId)) {
                account = accounts.get(companyId);
            } else {
                account = new Account();
                accounts.put(companyId, account);
            }
            account.addWarehouseCharges(warehouseCharges);
        } finally {
            lock.unlock();
        }
    }

    /**
     *
     * @param companyId
     * @return
     */
    public double retrieveWages(Long companyId) {
        double wages = 0.0d;
        Lock lock = mapLock.readLock();
        try {
            lock.lock();
            Account account;
            if (accounts.containsKey(companyId)) {
                account = accounts.get(companyId);
                wages = account.retrieveWages();
            }
        } finally {
            lock.unlock();
        }
        return wages;
    }

    /**
     *
     * @param companyId
     * @return
     */
    public double retrieveWarehouseCharges(Long companyId) {
        double warehouseCharges = 0.0d;
        Lock lock = mapLock.readLock();
        try {
            lock.lock();
            Account account;
            if (accounts.containsKey(companyId)) {
                account = accounts.get(companyId);
                warehouseCharges = account.retrieveWarehouseCharges();
            }
        } finally {
            lock.unlock();
        }
        return warehouseCharges;
    }

}
