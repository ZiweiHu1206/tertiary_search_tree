import java.util.Iterator;



import java.util.ArrayList;

public class TST<T extends Comparable<T>> implements Iterable<T>{
    // root node of the tree
    TSTNode<T> root;
 
    // constructor
    public TST() {
        this.root = null;
    }
    

    public void insert(T element){
        this.root = insertHelperMethod(this.root, element);
    }
    
    
    public TSTNode<T> insertHelperMethod(TSTNode<T> root, T element){
    	if(root == null){
    		root = new TSTNode<T>(element);
    	} else if(element.compareTo(root.element) < 0){
    		root.left = insertHelperMethod(root.left, element);
    	} else if(element.compareTo(root.element) > 0){
    		root.right = insertHelperMethod(root.right, element);
    	} else if(element.compareTo(root.element) == 0){
    		root.mid = insertHelperMethod(root.mid, element);
    	}
    	return root;
    }

    public void remove(T element){
        this.root = removeHelper(this.root, element);
    }
    
    public TSTNode<T> removeHelper(TSTNode<T> root, T element){
    	if(root == null) {
    		return null;
    	}else if(element.compareTo(root.element) < 0) {
    		root.left = removeHelper(root.left, element);
    	}else if(element.compareTo(root.element) > 0) {
    		root.right = removeHelper(root.right, element);
    	}else {
    		if(root.mid != null) {
     			root.mid = removeHelper(root.mid, element);
    		}else if(root.left == null) {
    			root = root.right;
    		}else if(root.right == null) {
    			root = root.left;
    		}else {
    			root.element = root.left.findMax().element;
    			root.mid = root.left.findMax().mid;
    			root.left.findMax().mid = null;
    				
    			root.left = removeHelper(root.left, root.element);
    		}
    	}
    	return root;
    }
    
   
    public boolean contains(T element){
        return containsHelper(this.root, element) != null;
    }
    
    
    public TSTNode<T> containsHelper(TSTNode<T> root, T element){
    	if(root == null) {
    		return null;
    	}else if(element.compareTo(root.element) == 0) {
    		return root;
    	}else if(element.compareTo(root.element) < 0) {
    		return containsHelper(root.left, element);
    	}else { 
    		return containsHelper(root.right, element);
    	}
    }
    

    public void rebalance(){
        Iterator<T> iter = iterator();
        ArrayList<T> arr = new ArrayList<T>();
        while (iter.hasNext()) {
        	arr.add(iter.next());
        }
        
        this.root = rebalanceHelper(arr);
        
        
    }
     
    public TSTNode<T> rebalanceHelper(ArrayList<T> arr){
    	int firstIndex = 0;
        int lastIndex = arr.size() - 1;
        
    	if(firstIndex <= lastIndex) {
    		int midIndex = arr.size() / 2;
    		TST<T> tree = new TST<T>();
    		tree.insert(arr.get(midIndex));
    		
    		ArrayList<T> arrLess = new ArrayList<T>();
    		ArrayList<T> arrEqual = new ArrayList<T>();
    		ArrayList<T> arrGreater = new ArrayList<T>();
    		
    		for(int i = 0; i < arr.size(); i++) {
    			if(i != midIndex) {
    				if(arr.get(i).compareTo(arr.get(midIndex)) < 0) {
        				arrLess.add(arr.get(i));
        			}else if(arr.get(i).compareTo(arr.get(midIndex)) == 0) {
        				arrEqual.add(arr.get(i));
        			}else {
        				arrGreater.add(arr.get(i));
        			}
    			}
    		}
    		
    		tree.root.left = rebalanceHelper(arrLess);
    		tree.root.mid = rebalanceHelper(arrEqual);
    		tree.root.right = rebalanceHelper(arrGreater);
    		
    		return tree.root;
    	}else {
    		return null;
    	}
    	
    }

    

    
 
    public int height(){
        if (this.root == null)
            return -1;
        return this.root.height();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new TSTIterator<T>(this);
    }

 
    // This string representation of the tree mimics the 'tree' command in unix
    // with the first child being the left child, the second being the middle child, and the last being the right child.
    // The left child is connect by ~~, the middle child by -- and the right child by __.
    // e.g. consider the following tree
    //               5
    //            /  |  \
    //         2     5    9
    //                   /
    //                  8
    // the tree will be printed as
    // 5
    // |~~ 2
    // |   |~~ null
    // |   |-- null
    // |   |__ null
    // |-- 5
    // |   |~~ null
    // |   |-- null
    // |   |__ null
    // |__ 9
    //     |~~ 8
    //     |   |~~ null
    //     |   |-- null
    //     |   |__ null
    //     |-- null
    //     |__ null
    @Override
    public String toString() {
        if (this.root == null)
            return "empty tree";
        // creates a buffer of 100 characters for the string representation
        StringBuilder buffer = new StringBuilder(100);
        // build the string
        stringfy(buffer, this.root,"", "");
        return buffer.toString();
    }

    /**
     * Build a string representation of the tertiary tree.
     * @param buffer String buffer
     * @param node Root node
     * @param nodePrefix The string prefix to add before the node's data (connection line from the parent)
     * @param childrenPrefix The string prefix for the children nodes (connection line to the children)
     */
    private void stringfy(StringBuilder buffer, TSTNode<T> node, String nodePrefix, String childrenPrefix) {
        buffer.append(nodePrefix);
        buffer.append(node.element);
        buffer.append('\n');
        if (node.left != null)
            stringfy(buffer, node.left,childrenPrefix + "|~~ ", childrenPrefix + "|   ");
        else
            buffer.append(childrenPrefix + "|~~ null\n");
        if (node.mid != null)
            stringfy(buffer, node.mid,childrenPrefix + "|-- ", childrenPrefix + "|   ");
        else
            buffer.append(childrenPrefix + "|-- null\n");
        if (node.right != null)
            stringfy(buffer, node.right,childrenPrefix + "|__ ", childrenPrefix + "    ");
        else
            buffer.append(childrenPrefix + "|__ null\n");
    }

    /**
     * Print out the tree as a list using an enhanced for loop.
     * Since the Iterator performs an inorder traversal, the printed list will also be inorder.
     */
    public void inorderPrintAsList(){
        String buffer = "[";
        for (T element: this) {
            buffer += element + ", ";
        }
        int len = buffer.length();
        if (len > 1)
            buffer = buffer.substring(0,len-2);
        buffer += "]";
        System.out.println(buffer);
    }
}
