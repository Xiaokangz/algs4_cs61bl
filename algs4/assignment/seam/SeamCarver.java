import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

	private Picture picture;

	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture) {
		if (picture == null) {
			throw new java.lang.IllegalArgumentException();
		}
		this.picture = new Picture(picture);
	}

	// current picture
	public Picture picture() {
		return new Picture(picture);
	}

	// width of current picture
	public int width() {
		return picture.width();
	}

	// height of current picture
	public int height() {
		return picture.height();
	}

	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) {
			throw new java.lang.IllegalArgumentException();
		}
		if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
			return 1000.0;
		}
		return Math.sqrt(deltaX2(x, y) + deltaY2(x, y) + 0.0);
	}

	private int deltaX2(int x, int y) {
		int prgb = picture.getRGB(x + 1, y);
		int mrgb = picture.getRGB(x - 1, y);
		int pr = (prgb >> 16) & 0xFF;
		int pg = (prgb >> 8) & 0xFF;
		int pb = (prgb >> 0) & 0xFF;
		int mr = (mrgb >> 16) & 0xFF;
		int mg = (mrgb >> 8) & 0xFF;
		int mb = (mrgb >> 0) & 0xFF;
		int rx = pr - mr;
		int gx = pg - mg;
		int bx = pb - mb;
		return rx * rx + gx * gx + bx * bx;
	}

	private int deltaY2(int x, int y) {
		int prgb = picture.getRGB(x, y + 1);
		int mrgb = picture.getRGB(x, y - 1);
		int pr = (prgb >> 16) & 0xFF;
		int pg = (prgb >> 8) & 0xFF;
		int pb = (prgb >> 0) & 0xFF;
		int mr = (mrgb >> 16) & 0xFF;
		int mg = (mrgb >> 8) & 0xFF;
		int mb = (mrgb >> 0) & 0xFF;
		int ry = pr - mr;
		int gy = pg - mg;
		int by = pb - mb;
		return ry * ry + gy * gy + by * by;
	}

	// sequence of indices for vertical seam
	public int[] findVerticalSeam() {
		picture = transpose(picture);
		int[] horizontalSeam = findHorizontalSeam();
		picture = transpose(picture);
		return horizontalSeam;
	}

	private static Picture transpose(Picture picture) {
		Picture tpicture = new Picture(picture.height(), picture.width());
		for (int i = 0; i < picture.width(); i += 1) {
			for (int j = 0; j < picture.height(); j += 1) {
				tpicture.setRGB(j, i, picture.getRGB(i, j));
			}
		}
		return tpicture;
	}

	// sequence of indices for horizontal seam
	public int[] findHorizontalSeam() {
		double[][] energy = new double[width()][height()];
		int[][] prev = new int[width()][height()];
		double[][] total = new double[width()][height()];
		for (int i = 0; i < width(); i += 1) {
			for (int j = 0; j < height(); j += 1) {
				energy[i][j] = energy(i, j);
			}
		}
		for (int j = 0; j < height(); j += 1) {
			prev[0][j] = -1;
			total[0][j] = 1000.0;
		}
		for (int i = 1; i < width(); i += 1) {
			for (int j = 0; j < height(); j += 1) {
				int minj = minIndex(i - 1, j, total);
				prev[i][j] = minj;
				total[i][j] = total[i - 1][minj] + energy[i][j]; 
			}
		}
		int minj = -1;
		double minTotal = 0;
		for (int j = 0; j < height(); j += 1) {
			if (minj == -1 || total[width() - 1][j] < minTotal) {
				minTotal = total[width() - 1][j];
				minj = j;
			}
		}
		int temp = minj;
		int[] verticalSeam = new int[width()];
		for (int i = width() - 1; i >= 0; i -= 1) {
			verticalSeam[i] = temp;
			temp = prev[i][temp];
		}
		return verticalSeam;
	}

	private int minIndex(int x, int y, double[][] total) {
		if (height() == 1) {
			return y;
		}
		if (y == 0) {
			if (total[x][y] <= total[x][y + 1]) {
				return y;
			} else {
				return y + 1;
			}
		}
		if (y == height() - 1) {
			if (total[x][y] <= total[x][y - 1]) {
				return y;
			} else {
				return y - 1;
			}
		}
		if (total[x][y] <= total[x][y - 1]) {
			if (total[x][y] <= total[x][y + 1]) {
				return y;
			} else {
				return y + 1;
			}
		} else {
			if (total[x][y - 1] <= total[x][y + 1]) {
				return y - 1;
			} else {
				return y + 1;
			}
		}
	}

	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {
		picture = transpose(picture);
		removeHorizontalSeam(seam);
		picture = transpose(picture);
	}

	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam) {
		if (seam == null || width() < 1) {
			throw new java.lang.IllegalArgumentException();
		}
		if (seam.length != picture.width()) {
			throw new java.lang.IllegalArgumentException();
		}
		for (int i = 1; i < seam.length; i += 1) {
			if (Math.abs(seam[i] - seam[i - 1]) > 1) {
				throw new java.lang.IllegalArgumentException();
			}
		}
		Picture newPicture = new Picture(picture.width(), picture.height() - 1);
		for (int i = 0; i < width(); i += 1) {
			for (int j = 0; j < seam[i]; j += 1) {
				newPicture.setRGB(i, j, picture.getRGB(i, j));
			}
			for (int j = seam[i] + 1; j < height(); j += 1) {
				newPicture.setRGB(i, j - 1, picture.getRGB(i, j));
			}
		}
		picture = newPicture;
	}

}