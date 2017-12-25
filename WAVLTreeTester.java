public class WAVLTreeTester {

	public static void main(String[] args) {
		WAVLTree tree = new WAVLTree();
		for (int i = 1; i <= 10000; ++i)
			tree.insert(i, String.valueOf(i));
		System.out.println(tree.search(9581));
	}

}
