

class TSTNode<T extends Comparable<T>>{
    T element;     	            // The data in the node
    TSTNode<T>  left;   		// left child
    TSTNode<T>  mid;   		    // middle child
    TSTNode<T>  right;  		// right child

    
    
    TSTNode(T element){
    	this.element = element;

    }
    
    TSTNode<T> findMaxHelper(TSTNode<T> root){
    	if(root == null) {
    		return null;
    	}else if(root.right == null) {
    		return root;
    	}else {
    		return findMaxHelper(root.right);
    	}
    }

    TSTNode<T> findMax(){
        return findMaxHelper(this);
    }
    
    TSTNode<T> findMinHelper(TSTNode<T> root){
    	if(root == null) {
    		return null;
    	}else if(root.left == null) {
    		return root;
    	}else {
    		return findMinHelper(root.left);
    	}
    }
    
    TSTNode<T> findMin(){
        return findMinHelper(this);
    }

    int heightHelper(TSTNode<T> root) {
    	if(root.left == null && root.mid == null && root.right == null) {
    		return 0;
    	}else {
    		int h = 0;
    		h = Math.max(h, heightHelper(root.left));
    		h = Math.max(h, heightHelper(root.mid));
    		h = Math.max(h, heightHelper(root.right));
    		return 1+h;
    	}
    }
    
    int height(){
        return heightHelper(this);
    }
    
 


}