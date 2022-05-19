import java.util.Iterator;
import java.util.LinkedList;

class TSTIterator<T extends Comparable<T>> implements Iterator<T> {
    LinkedList<T> list = new LinkedList<T>();
    
    public TSTIterator(TST<T> tree) {
    	if(tree.root != null) {
    		inorderTST(tree.root);
    	}
    }
    
    
    public void inorderTST(TSTNode<T> root){
    	if (root != null) {
    		inorderTST(root.left);
    		this.list.add(root.element);
    		inorderTST(root.mid);
    		inorderTST(root.right);
    	}
    }
    
    /**
     * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link #next}
     * would return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return (this.list.size() != 0);
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     *
     * @throws NoSuchElementException
     *         if the iteration has no more elements
     */
    @Override
    public T next() {
    	T element = this.list.removeFirst();
        return element;
    	
    }
}