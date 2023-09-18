package backend;

public class BinarySearchTree {
	
	public static boolean addTyre(Tyre n, Tyre k) {

		if (n != null) {

			if (n.product_code.equals(k.product_code)) {

				return false;

			} else if (k.product_code.compareTo(n.product_code) > 0 && n.right == null) {

				n.right = k;

				return true;

			} else if (k.product_code.compareTo(n.product_code) < 0 && n.left == null) {

				n.left = k;

				return true;

			} else if (k.product_code.compareTo(n.product_code) > 0) {

				return addTyre(n.right, k);

			} else if (k.product_code.compareTo(n.product_code) < 0) {

				return addTyre(n.left, k);

			} else {

				return false;
			}

		} else {

			return false;

		}
	}

}
