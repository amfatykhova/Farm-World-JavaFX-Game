package sample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkerInventory {

    private Map<Worker, Integer> workers;
    private int capacity;
    private int size;
    private Worker sal; // starting worker

    public WorkerInventory(FarmWorldConfigurations.Difficulty difficulty) {
        this.capacity = (int) (100 * difficulty.getMultiplier());
        this.workers = new HashMap<Worker, Integer>(this.capacity);
        this.sal = Worker.ENTRY;
        this.workers.put(sal, 1);
        this.size = 1; // start with 0 workers
    }

    public void remove(Worker worker, int number) throws InsufficientItemsException {
        int existingQuantity = this.workers.get(worker);
        if (existingQuantity < number) {
            throw new InsufficientItemsException("Cannot remove " + number + " " + worker.name()
                    + "'s since there are only " + existingQuantity
                    + " in the player's inventory");
        }
        System.out.println("New quantity of " + worker.name() + " = " + (existingQuantity - number));
        this.workers.put(worker, existingQuantity - number); // Update the quantity of workers
        this.size -= number;
    }

    public void add(Worker worker, int number) throws WorkerInventoryCapacityException {
        if (this.size + number > this.capacity) {
            throw new WorkerInventoryCapacityException("The player's inventory (size = "
                    + this.size + ") does not have enough capacity (capacity = "
                    + this.capacity + ") to hire " + number + " " + worker.name() + "'s ");
        }
        System.out.println("New quantity of " + worker.name() + " = " + (number));
        this.workers.put(worker, number);
        this.size += number;
    }


    public Map<Worker, Integer> getWorkerMap() {
        return this.workers;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getSize() {
        return size;
    }

}
