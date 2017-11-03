package directedGraph;

public class MinBinHeap implements Heap_Interface {
	private EntryPair[] array;
	private int size;
	private static final int arraySize = 10000;

	public MinBinHeap() {
		this.array = new EntryPair[arraySize];
		array[0] = new EntryPair(null, -100000); 
	}

    
	public EntryPair[] getHeap() { 
		return this.array;
	}
	
	public void insert(EntryPair entry) {
		size++;
		int entryIndex = size;
		array[entryIndex] = entry;
		if(size != 1){
			int parentIndex =  (int)Math.floor(entryIndex/2);
			EntryPair parent = array[parentIndex];
			while(parent.priority > entry.priority){
				array[parentIndex] = entry;
				array[entryIndex] = parent;
				entryIndex = parentIndex;
				parentIndex =  (int)Math.floor(parentIndex/2);
				parent = array[parentIndex];
			}
		}
	}
	
	public void bubbleDown(int index){
		int leftIndex = (index *2);
		int rightIndex = (index *2) + 1;
		int minIndex;
		
		if(rightIndex > size){
			if(leftIndex > size){
				return;
			}
			else{
				minIndex = leftIndex;
			}
		}
		else{
			if(array[rightIndex].priority < array[leftIndex].priority){
				minIndex = rightIndex;
			}
			else{
				minIndex = leftIndex;
			}
		}
		if(array[minIndex].priority < array[index].priority){
			EntryPair temp = array[index];
			array[index] = array[minIndex];
			array[minIndex] = temp;
			bubbleDown(minIndex);
		}
	}

	@Override
	public void delMin() {
		array[1] = array[size];
		array[size] = null;
		size--;
		if(size > 1){
			bubbleDown(1);
		}
	}

	@Override
	public EntryPair getMin() {
		return array[1];
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void build(EntryPair[] entries) {
		for(int i = 0; i < entries.length; i++){
			array[i + 1] = entries[i];
		}
		size = entries.length;
		int child = size;
		while(child > 1){
			int index = (int)Math.floor(child / 2);
			bubbleDown(index);
			child -= 2;
		}
		bubbleDown(1);
	}
}
