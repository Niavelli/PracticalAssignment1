/**
*
* WAVLTree
*
* An implementation of a WAVL Tree with
* distinct integer keys and info
*
*/

public class WAVLTree {

 private WAVLNode root;
 public WAVLNode VirNode;
 
 //ayala: what is the righ t one?
 
 public void setVN() {
	 this.VirNode = new WAVLNode(-1, null, null, null, null, -1);
 }
 
 public WAVLTree(){
	 this.VirNode = new WAVLNode(-1, null, null, null, null, -1);
	 this.root = this.VirNode;
 }
 
 public void setroot(WAVLNode root){
	 this.root = root;
 }
 ///two options
 
 public WAVLNode getVN() {
	 return VirNode;
 }
 /**
  * public boolean empty()
  *
  * returns true if and only if the tree is empty
  *
  */

 public boolean empty() {
   if ((root.getLeft() == null) || (root.getRight() == null))
     return true;
   return false;
 }

/**
  * public String search(int k)
  *
  * returns the info of an item with key k if it exists in the tree
  * otherwise, returns null
  */
 public String search(int k) {
   if (k == root.getKey()) 
   	return root.getValue();
   
   WAVLNode next;
   
   next = (k < root.key) ? root.getLeft() : root.getRight();
   while (next != null) 
   {
   	if (k == next.getKey()) 
   		return next.getValue();
   	next = (k < next.getKey()) ? next.getLeft() : next.getRight();
   };
   
   return null;
 }
 
 public WAVLNode search_node(int k){
	   if (k == root.getKey()) 
		   	return root;
		   
		   WAVLNode next;
		   
		   next = (k < root.key) ? root.getLeft() : root.getRight();
		   while (next != null) 
		   {
		   	if (k == next.getKey()) 
		   		return next;
		   	next = (k < next.getKey()) ? next.getLeft() : next.getRight();
		   };
		   
		   return null;
 }

 /**
  * public int insert(int k, String i)
  *
  * inserts an item with key k and info i to the WAVL tree.
  * the tree must remain valid (keep its invariants).
  * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
  * returns -1 if an item with key k already exists in the tree.
  */
  public int insert(int k, String i) {
	   WAVLNode newLeaf = new WAVLNode(k, i); 
	   WAVLNode next;
	   int balCnt = 0;
	   
	   //Looking for the right place. If similar key has been found - return -1
	   if (k == root.getKey()) 
		   return -1;
	   next = (k < root.key) ? root.getLeft() : root.getRight();
	   while ( ( (k < next.getKey()) ? next.getLeft() : next.getRight() ) != null ) 
	   {
	    	if (k == next.getKey()) 
	    		return -1;
	    	next = (k < next.getKey()) ? next.getLeft() : next.getRight();   
	   }
	   
	   if (k < next.getKey())
		   next.setLeft(newLeaf);
	   else
		   next.setRight(newLeaf);
	   
	   
	   //To be continued
	   
	   return 42;
  }

 /**
  * public int delete(int k)
  *
  * deletes an item with key k from the binary tree, if it is there;
  * the tree must remain valid (keep its invariants).
  * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
  * returns -1 if an item with key k was not found in the tree.
  */
  public int delete(int k)
  {
	  WAVLNode node = search_node(k);
	  if (node == null){
		  return -1;
	  }
	  if (node.getRank() != 0){
		  if (node.left.isRealNode()){    //ayala: can do it also smart - change it according to biggest size - size takes n
			  WAVLNode pre = predecessor(node);
			  lazy_swap(pre, node);
			  node = pre;
		  }else{
			  WAVLNode suc = successor(node);
			  lazy_swap(suc, node);
			  node = suc;
		  }
	  }
	  return delete_leaf(node);
	  
  }
  private void lazy_swap(WAVLNode one, WAVLNode two){
	  int key = one.getKey();
	  String value = one.getValue();
	  one.setValue(two.getValue());
	  one.setKey(one.getKey());
	  two.setValue(value);
	  two.setKey(key);
  }
  private void swap_places(WAVLNode leaf, WAVLNode node){
	  //step 1: setting kids
	  leaf.setRight(node.right);
	  leaf.setLeft(node.left);
	  node.setLeft(this.VirNode);
	  node.setRight(this.VirNode);
	  //step 2: setting kids parents
	  if (leaf.right.isRealNode()){
		  leaf.right.setParent(leaf);
	  }
	  if (leaf.getLeft().isRealNode()){
		  leaf.left.setParent(leaf);
	  }//set parent for vr or iwavlnode but then wont work
	  //step3: setting the rank
	  leaf.setRank(node.rank);
	  node.setRank(0);
	  //step 4: set the parets kids
	  WAVLNode parent = node.parent;
	  if ((node.parent).getRight()==node){
		  node.parent.setRight(leaf);
	  }else{
		  node.parent.setLeft(leaf);
	  }
	  if ((leaf.parent.right == leaf)){
		  leaf.parent.setRight(node);
	  }else{
		  leaf.parent.setLeft(node);
	  }
	  //step 5: set the nodes parents
	  node.setParent(leaf.parent);
	  leaf.setParent(node.parent);
	  
  }
  
  private WAVLNode successor(WAVLNode node){
	  return 42;
  }
  private WAVLNode predecessor(WAVLNode node){
	  return 3;
  }

  /**
   * public String min()
   *
   * Returns the info of the item with the smallest key in the tree,
   * or null if the tree is empty
   */
  public String min()
  {
	   if (empty()){
		   return null;
	   }
	   return min_rec(root);
  }

  public String min_rec(WAVLNode node){
	  if (node.getLeft().isRealNode()){
		  return min_rec(node.getLeft());
	  }else{
		  return node.value;
	  }
  }
  /**
   * public String max()
   *
   * Returns the info of the item with the largest key in the tree,
   * or null if the tree is empty
   */
  public String max()
  {
	   if (empty()){
		   return null;
	   }
	   return max_rec(root);
  }

  public String max_rec(WAVLNode node){
	  if (node.getRight().isRealNode()){
		  return max_rec(node.getRight());
	  }else{
		  return node.value;
	  }
  }
 /**
  * public int[] keysToArray()
  *
  * Returns a sorted array which contains all keys in the tree,
  * or an empty array if the tree is empty.
  */
 public int[] keysToArray()
 {
       int[] arr = new int[42]; // to be replaced by student code
       return arr;              // to be replaced by student code
 }

 /**
  * public String[] infoToArray()
  *
  * Returns an array which contains all info in the tree,
  * sorted by their respective keys,
  * or an empty array if the tree is empty.
  */
 public String[] infoToArray()
 {
       String[] arr = new String[42]; // to be replaced by student code
       return arr;                    // to be replaced by student code
 }

  /**
   * public int size()
   *
   * Returns the number of nodes in the tree.
   *
   * precondition: none
   * postcondition: none
   */
  public int size()
  {
	   return root.size(); 
  }
  
    /**
   * public int getRoot()
   *
   * Returns the root WAVL node, or null if the tree is empty
   *
   * precondition: none
   * postcondition: none
   */
  public WAVLNode getRoot() //ayala: changed type into wavlnode because that how we defined it, can change it in return
  {
	   return root;
  }
    /**
   * public int select(int i)
   *
   * Returns the value of the i'th smallest key (return -1 if tree is empty)
   * Example 1: select(1) returns the value of the node with minimal key 
	* Example 2: select(size()) returns the value of the node with maximal key 
	* Example 3: select(2) returns the value 2nd smallest minimal node, i.e the value of the node minimal node's successor 	
   *
	* precondition: size() >= i > 0
   * postcondition: none
   */   
  public String select(int i)
  {
	  if ((i>size()) ||  (i<=0)){
		  return null;
	  }
	  return select_rec(root, i-1);
  }
  
  private String select_rec(WAVLNode node, int i){
	  int subsize = node.getLeft().size();
	  if (subsize == i){
		  return node.getValue();
	  }else if(i<subsize){
		  return select_rec(node.left, i);
	  }
	  return select_rec(node.getRight(), i-subsize-1);	  
  }

	/**
	   * public interface IWAVLNode
	   * ! Do not delete or modify this - otherwise all tests will fail !
	   */
	public interface IWAVLNode{	
		public int getKey(); //returns node's key (for virtuval node return -1)
		public String getValue(); //returns node's value [info] (for virtuval node return null)
		public IWAVLNode getLeft(); //returns left child (if there is no left child return null)
		public IWAVLNode getRight(); //returns right child (if there is no right child return null)
		public boolean isRealNode(); // Returns True if this is a non-virtual WAVL node (i.e not a virtual leaf or a sentinal)
		public int getSubtreeSize(); // Returns the number of real nodes in this node's subtree (Should be implemented in O(1))
	}

  /**
  * public class WAVLNode
  *
  * If you wish to implement classes other than WAVLTree
  * (for example WAVLNode), do it in this file, not in 
  * another file.
  * This class can and must be modified.
  * (It must implement IWAVLNode)
  */
 public class WAVLNode implements IWAVLNode{
	private int key;
   private String value;
   private WAVLNode left;								//Amit: Changed the return type to WAVLNode instead of IWAVLNode - it's not suppose to have any consequences on the above interface
   private WAVLNode right;								//Amit: Did it because the interface doesn't have references to rank or parent
   private WAVLNode parent;
   private int rank;
   
   public WAVLNode(int key, String value, WAVLNode left, WAVLNode right, WAVLNode parent, int rank) {
   	this.key = key;
   	this.value = value;
   	this.left = left;
   	this.right = right;
   	this.parent = parent;
   	this.rank = rank;
   }
   
   public WAVLNode(int key, String value) {
   	this.key = key;
   	this.value = value;
   	this.left = getVN();    //ayala: for leaf qwe should have two virtual sons, changed
   	this.right = getVN();   ///ayala: should check if its ok cause its in the class (knowing its in there) if not wouldnt recognise and know
   	this.parent = null;
   	this.rank = 0;
   }    
   
   public int getKey()
	{
		return key;
	}
	public String getValue()
	{
		return value;
	}
	public WAVLNode getLeft()
	{
		return left;
	}
	public WAVLNode getRight()
	{
		return right;
	}
	// Returns True if this is a non-virtual WAVL node (i.e not a virtual leaf or a sentinal)
	public boolean isRealNode()
	{
		return rank >= 0;
	}

	public int getSubtreeSize()
	{
		return 42; // to be replaced by student code
	}
	
	public int getRank()
	{
		return rank;
	}
	public WAVLNode getParent()
	{
		return parent;
	}
	public void setLeft(WAVLNode node)
	{
		left = node;
	}
	public void setRight(WAVLNode node)
	{
		right = node;
	}
	public void setParent(WAVLNode node)
	{
		parent = node;
	}
	public void setRank(int r){
		rank = r;
	}
	public int size(){
		if (isRealNode()){
			return 1+ this.right.size() + this.left.size();
		}
		return 0;
		}
	
	//ayala: in prder to do lazy swapping
	public void setValue(String Value){
		value = Value;
	}
	public void setKey(int Key){
		key = Key;
	}
 }

}

