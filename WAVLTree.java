import java.util.ArrayList;
import java.util.List;

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
 //amit:  from slide 5 in WAVL presentation says you were right and it should be represented by a single entity for all external leaves
 
 private void setVN() {
	 this.VirNode = new WAVLNode(0, null, null, null, null, -1);
 }
 
 public WAVLTree(){
	 this.VirNode = new WAVLNode(0, null, null, null, null, -1);
	 this.root = this.VirNode;
 }
 
 private void setroot(WAVLNode root){
	 this.root = root;
 }
 ///two options
 
 private WAVLNode getVN() {
	 return VirNode;
 }
 
 private boolean isRoot(WAVLNode node) {
	 return getRoot()==node;
 }
 
 /**
  * public boolean empty()
  *
  * returns true if and only if the tree is empty
  *
  */
 public boolean empty() {
   if ((!root.getLeft().isRealNode()) || (!root.getRight().isRealNode()))
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
   while (next.isRealNode()) 
   {
   	if (k == next.getKey()) 
   		return next.getValue();
   	next = (k < next.getKey()) ? next.getLeft() : next.getRight();
   };
   
   return null;
 }
 
 public WAVLNode searchNode(int k){
	   if (k == root.getKey()) 
		   	return root;
		   
		   WAVLNode next;
		   
		   next = (k < root.key) ? root.getLeft() : root.getRight();
		   while (next.isRealNode()) 
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
	   WAVLNode newLeaf;
	   WAVLNode next;
	   WAVLNode father;
	   //Looking for the right place. If similar key has been found - return -1
	   if (!root.isRealNode())
	   {
		   root = new WAVLNode(k, i);
		   return 0;
	   }
	   else
	   {
		   if (k == root.getKey())			 
			   return -1;
		   next = (k < root.key) ? root.getLeft() : root.getRight();
		   father = root;
		   while ( next.isRealNode() ) 
		   {
		    	if (k == next.getKey()) 
		    		return -1;
		    	father = next;
		    	next = (k < next.getKey()) ? next.getLeft() : next.getRight();   
		   }															//We stopped the while when next is virtual leaf
		   newLeaf = new WAVLNode(k, i, VirNode, VirNode, father, 0);
		   if (k < father.getKey())
		   {
			   father.setLeft(newLeaf);
			   //return rearTree(father.getLeft(), 0);
		   }
		   else
		   {
			   father.setRight(newLeaf);
			   //return rearTree(father.getRight(), 0);
		   }
	   }
	   return rearTree(father, 0);
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
	  WAVLNode node = searchNode(k);
	  if (node == null){
		  return -1;
	  }
	  if (node.getRight().isRealNode() && node.getLeft().isRealNode()){
		  if (node.getLeft().isRealNode()){    //ayala: can do it also smart - change it according to biggest size - size takes n
			  WAVLNode pre = predecessor(node);//amit: I didn't understand what you meant above. Also - there is no difference with deleting with the predecessor or the successor - right?
			  lazySwap(pre, node);
			  node = pre;
		  }else{								//amit: this else is unnecessary. At this point - node.getLeft().isRealNode() always true
			  WAVLNode suc = successor(node);
			  lazySwap(suc, node);
			  node = suc;
		  }
	  }
	  return delete_lu(node);
	  
  }
  
  private int delete_lu(WAVLNode node){
	  //part 1: delete the leaf, add the vir or sons as the kids
	  WAVLNode son;
	  WAVLNode parent;
	  if (node.getRank() == 1){
		  if (node.getRight().isRealNode()){
			  son = node.getRight(); 
		  }else{
			  son = node.getLeft();
		  }
		  son.setParent(node.getParent());
	  }else{
		  son = VirNode;
	  }
	  if ((node.parent).getRight()==node){
		  node.parent.setRight(son);
	  }else{
		  node.parent.setLeft(son);
	  }
	  parent = node.getParent();
	  node.setParent(null);
	  return rearTree(parent, 0);
  }
  
  private int rearTree(WAVLNode parent, int blnct){
	  //Insertion Part //
	  if (rankDiff(parent,parent.getLeft()) == 0)
	  {
		  if (rankDiff(parent,parent.getRight()) == 1)
			 promote(parent, blnct);
		  else if (rankDiff(parent,parent.getRight()) == 2)
			  if (rankDiff(parent.getLeft(),parent.getLeft().getLeft()) == 1)
				  Rotate(parent, blnct);
			  else
				  DoubleRotate(parent, blnct);
	  }
	  else if (rankDiff(parent,parent.getRight()) == 0)
	  {
		  if (rankDiff(parent,parent.getLeft()) == 1)
			 promote(parent, blnct);
		  else if (rankDiff(parent,parent.getLeft()) == 2)
			  if (rankDiff(parent.getRight(),parent.getRight().getRight()) == 1)
				  Rotate(parent, blnct);
			  else
				  DoubleRotate(parent, blnct);
	  }
	  
	  // Deletion Part //
	  if (rankDiff(parent, parent.getLeft()) >2){  
		  ///the left is the wrong one !! the one we deleted
		 if(rankDiff(parent, parent.getRight())== 2){
			 demote(parent, blnct);
		 }else{
			 WAVLNode node = parent.getRight();
			 if((rankDiff(node, node.getLeft()) ==  2) && (rankDiff(node, node.getRight()) ==  2)){
				 DoubleDemote(parent, blnct);
			 }else if((rankDiff(node, node.getRight()) ==  1)){
				 Rotate(parent, blnct);
			 }else{
				 DoubleRotate(parent, blnct);
			 }
		 }
	  }else if(rankDiff(parent, parent.getRight()) > 2){
		 ///the right is the wrong one !! the one we deleted
			if(rankDiff(parent, parent.getLeft())== 2){
				demote(parent, blnct);
			}else{
				WAVLNode node = parent.getLeft();
				if((rankDiff(node, node.getRight()) ==  2) && (rankDiff(node, node.getLeft()) ==  2)){
					DoubleDemote(parent, blnct);
				}else if((rankDiff(node, node.getLeft()) ==  1)){
					Rotate(parent, blnct);
				}else{
					DoubleRotate(parent, blnct);
				}
			}
	  }
	  return blnct;
  }
  
  private void demote(WAVLNode node, int blnct){
	  node.setRank(node.getRank()-1);
	  if (node.parent != null)
		  rearTree(node.parent, blnct+1);
  }
  
  private void promote(WAVLNode node, int blnct){
	  node.setRank(node.getRank()+1);
	  if (node.parent != null)
		  rearTree(node.parent, blnct+1);
  }
  
  private void DoubleDemote(WAVLNode node, int blnct){
	  if (node.getLeft().getRank() == node.getRank()-1){
		  node.getLeft().setRank(node.getLeft().getRank()-1);
	  }else{
		  node.getRight().setRank(node.getRight().getRank()-1);
	  }
	  node.setRank(node.getRank()-1);
	  rearTree(node.getParent(), blnct+2);
  }
  
  private void Rotate(WAVLNode node, int blnct){
	  	int rankDiffLeft = rankDiff(node, node.getLeft());
	  
	  	if (rankDiffLeft == 0 || rankDiffLeft == 1)
	  		rotateRight(node);
	  	else
	  		rotateLeft(node);
	  
	  	//We need to correct node ranking anyway
	  	node.setRank(node.getRank()-1);
	  	blnct += 2;												// 1 for the rotation and 1 for the "demotion"
	  	
	  	//This is needed only after deletion
	  	if (rankDiffLeft == 3 || rankDiffLeft == 1)
	  	{
	  		node.getParent().setRank(node.getParent().getRank()+1);
	  		blnct += 1;
	  	}

	  //return blnct;
  }
  
  private void DoubleRotate(WAVLNode node, int blnct)
  {
	  int rankDiffLeft = rankDiff(node, node.getLeft());
	  switch (rankDiffLeft) {
	  	case 0:
	  	{
	  		rotateLeft(node.getLeft());
	  		rotateRight(node);
	  		
	  		node.setRank(node.getRank()-1);
	  		node.getParent().setRank(node.getParent().getRank()+1);
	  		node.getParent().getLeft().setRank(node.getParent().getLeft().getRank()-1);
	  	}
	  	
	  	case 2:
	  	{
	  		rotateRight(node.getRight());
	  		rotateLeft(node);
	  		
	  		node.setRank(node.getRank()-1);
	  		node.getParent().setRank(node.getParent().getRank()+1);
	  		node.getParent().getRight().setRank(node.getParent().getRight().getRank()-1);
	  	}
	  	
	  	case 1:
	  	{
	  		rotateLeft(node.getLeft());
	  		rotateRight(node);
	  		
	  		node.setRank(node.getRank() - 2);
	  		node.getParent().setRank(node.getParent().getRank() + 2);
	  		node.getParent().getRight().setRank(node.getParent().getRight().getRank()-1);
	  	}
	  	
	  	case 3:
	  	{
	  		rotateRight(node.getRight());
	  		rotateLeft(node);
	  		
	  		node.setRank(node.getRank() - 2);
	  		node.getParent().setRank(node.getParent().getRank() + 2);
	  		node.getParent().getLeft().setRank(node.getParent().getLeft().getRank()-1);
	  	}
	  }

	  blnct += 4;
	  //return blnct;
  }
  
  private void rotateRight(WAVLNode node)
  {
	  if (node.getParent() != null)
	  {
		  if (node.getParent().getKey() > node.getKey())			//node is left child
		  {
			  node.getParent().setLeft(node.getLeft());
		  }
		  else
		  {
			  node.getParent().setRight(node.getLeft());
		  }
	  }
	  node.getLeft().setParent(node.getParent());
	  
	  node.setParent(node.getLeft());
	  
	  node.setLeft(node.getLeft().getRight());
	  if (node.getLeft().isRealNode())
		  node.getLeft().setParent(node);
	  
	  node.getParent().setRight(node);
	  
	  if (isRoot(node))
		  setroot(node.getParent());
  }
  
  private void rotateLeft(WAVLNode node)
  {
	  if (node.getParent() != null)
	  {
		  if (node.getParent().getKey() > node.getKey())			//node is left child
		  {
			  node.getParent().setLeft(node.getRight());
		  }
		  else
		  {
			  node.getParent().setRight(node.getRight());
		  }
	  }
	  node.getRight().setParent(node.getParent());
	  
	  node.setParent(node.getRight());
	  
	  node.setRight(node.getRight().getLeft());
	  if (node.getRight().isRealNode())
		  node.getRight().setParent(node);
	  
	  node.getParent().setLeft(node);
	  
	  if (isRoot(node))
		  setroot(node.getParent());
  }
  
  private int rankDiff(WAVLNode highn, WAVLNode lown){
	  return highn.getRank()-lown.getRank();
  }
  
  private void lazySwap(WAVLNode one, WAVLNode two){				// amit: Better not to do it lazy - if there are any pointers to the leaf not being deleted - we are going to delete them after the swap
	  WAVLNode oneParent = one.getParent();
	  WAVLNode oneLeft = one.getLeft();
	  WAVLNode oneRight = one.getRight();
	  WAVLNode twoParent = two.getParent();
	  WAVLNode twoLeft = two.getLeft();
	  WAVLNode twoRight = two.getRight();
	  
	  //Changing the one part
	  one.setParent(twoParent);
	  one.setLeft((twoLeft.isRealNode()) ? twoLeft : VirNode);
	  one.setRight((twoRight.isRealNode()) ? twoRight : VirNode);
	  if (twoParent.getKey() > two.getKey())
		  twoParent.setLeft(one);
	  else
		  twoParent.setRight(one);
	  if (twoLeft.isRealNode()) twoLeft.setParent(one);
	  if (twoRight.isRealNode()) twoRight.setParent(one);
	  
	  //Changing the two part
	  two.setParent(oneParent);
	  two.setLeft((oneLeft.isRealNode()) ? oneLeft : VirNode);
	  two.setRight((oneRight.isRealNode()) ? oneRight : VirNode);
	  if (oneParent.getKey() > one.getKey())
		  oneParent.setLeft(two);
	  else
		  twoParent.setRight(two);
	  if (oneLeft.isRealNode()) oneLeft.setParent(two);
	  if (oneRight.isRealNode()) oneRight.setParent(two);
	  
  }
  
  private void swapPlaces(WAVLNode leaf, WAVLNode node){			//amit: this is not being used - maybe delete it?
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
	  if (node.right.isRealNode()){
		  node = node.right;
		  while (node.left.isRealNode()){
			  node = node.left;
		  }
		  return node;
	  }
	  if (node.parent.left== node){
		  return node.parent;
	  }
	  while (node != root || (node.parent).left != node){
		node = node.parent.left;
		if (node.parent.getLeft() == node){
			return node.getParent();
		}
	  }
	  return null; 
  }
  
  private WAVLNode predecessor(WAVLNode node){
	  if ((node.getLeft()).isRealNode()){
		  node = node.left;
		  while (node.right.isRealNode()){
			  node = node.right;
		  }
		  return node;
	  }
	  if (node.parent.right== node){
		  return node.parent;
	  }
	  while (node != root || (node.parent).getRight() != node){
		node = node.parent.getRight();
		if (node.parent.getRight() == node){
			return node.getParent();
		}
	  }
	  return null; 
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
	   return minRec(root);
  }

  //Recursive Minimum
  public String minRec(WAVLNode node){				
	  if (node.getLeft().isRealNode()){
		  return minRec(node.getLeft());
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
	   return maxRec(root);
  }

  public String maxRec(WAVLNode node){
	  if (node.getRight().isRealNode()){
		  return maxRec(node.getRight());
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
	 	List<Integer> lst = keysToArrayRec(root);
	 	return lst.stream().mapToInt(Integer::intValue).toArray();
 }
 
 private List<Integer> keysToArrayRec(WAVLNode subTree) {
	 	List<Integer> returnArray = new ArrayList<Integer>();
 		if (subTree.getLeft().isRealNode())
 			returnArray.addAll(keysToArrayRec(subTree.getLeft()));
 		returnArray.add(subTree.getKey());
 		if (subTree.getRight().isRealNode())
 			returnArray.addAll(keysToArrayRec(subTree.getRight()));
	 	return returnArray;
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
       
	 	int[] keysArray = keysToArray();	 		
	 	String[] arr = new String[keysArray.length];
	 	for (int i = 0; i < keysArray.length; ++i)
	 		arr[i] = search(keysArray[i]);
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
	   return root.getSubtreeSize(); 
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
	  return selectRec(root, i-1);
  }
  
  private String selectRec(WAVLNode node, int i){
	  int subsize = node.getLeft().getSubtreeSize();
	  if (subsize == i){
		  return node.getValue();
	  }else if(i<subsize){
		  return selectRec(node.left, i);
	  }
	  return selectRec(node.getRight(), i-subsize-1);	  
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
   	this.parent = null;		// amit: I don't know if it's needed - we can build this new leaf with the virtual leaf of the tree itself with the first constructor
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
		if (isRealNode()){
			return 1+ this.right.getSubtreeSize() + this.left.getSubtreeSize();
		}
		return 0;
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
	
	//ayala: in prder to do lazy swapping
	public void setValue(String Value){
		value = Value;
	}
	public void setKey(int Key){
		key = Key;
	}
 }

}

