public class WAVLTreeTester {

	public static void main(String[] args) {
		WAVLTree tree = new WAVLTree();
//		for (int i = 100; i >= 95; --i)
//			tree.insert(i, String.valueOf(i));
		int i = 10;
		tree.insert(i, String.valueOf(i));
		i = 11;
		tree.insert(i, String.valueOf(i));
		i = 7;
		tree.insert(i, String.valueOf(i));
		i = 6;
		tree.insert(i, String.valueOf(i));
		i = 8;
		tree.insert(i, String.valueOf(i));
		i = 9;
		tree.insert(i, String.valueOf(i));
		tree.delete(i);
		i = 10;
		tree.delete(i);
		i = 11;
		tree.delete(i);
		i = 6;
		tree.delete(i);
		System.out.println(tree.search(7));
	}

}
