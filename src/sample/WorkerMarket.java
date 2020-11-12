package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WorkerMarket {

    private Map<Worker, Integer> prices;

    public WorkerMarket() {
        this.prices = new HashMap<>();
        for (Worker worker : Worker.values()) {
            prices.put(worker, worker.getPrice());
        }
    }

    public double getPrice(Worker worker) {
        return this.prices.get(worker);
    }

    public Map<Worker, Integer> getWorkerMap() {
        return this.prices;
    }
}