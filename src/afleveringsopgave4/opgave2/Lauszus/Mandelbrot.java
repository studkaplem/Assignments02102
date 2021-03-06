package afleveringsopgave4.opgave2.Lauszus;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import afleveringsopgave3.StdDraw;

public class Mandelbrot {
	final static Scanner scanner = new Scanner(System.in);
	final static int g = 1000;
	final static int MAX = 255;
	private static double x0, y0, s;
	
	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		System.out.print("Enter the real coordinate of the center: ");
		x0 = consoleInputCheck(scanner);		
		System.out.print("Enter the imaginary coordinate of the center: ");
		y0 = consoleInputCheck(scanner);
		
		System.out.print("Enter the length of the side: ");
		s = consoleInputCheck(scanner);
		
		scanner.nextLine();
		System.out.print("Enter the filename of the color-file (press enter to skip): ");
		String filename = scanner.nextLine();

		drawMandelbrot(false,true,filename);
	}

	private static void drawMandelbrot(boolean drawPoints, boolean drawColors, String filename) {
		StdDraw.setCanvasSize(700, 700); // Adjust the size of the window
		StdDraw.setBorder(0); // We added this function ourself in order to adjust the border size
		StdDraw.setScale(0, g - 1); // Set size of the coordinate system
		
		
		int[][] colors = new int[MAX+1][3];
		if(drawColors && filename != null && filename != "") {
			try {
				colors = loadColors(filename);
			} catch (FileNotFoundException e) {
				System.out.println("Using default colors");
				filename = null;
			}
		}
		
		StdDraw.show(0);

		for (int i = 0; i < g; i++) {
			for (int j = 0; j < g; j++) {
				int iterations = iterate(getComplexCoordinate(j, i));
				StdDraw.setPenRadius(1.2/(double)g);
				if(drawColors) {
					if(filename == null) {
						int color;
						if(iterations < 5)
							color = (int)(50*iterations);
						else if(iterations < 10)
							color = (int)(25*iterations);
						else if(iterations < 100)
							color = (int)(2.5*iterations);
						else if(iterations < 150)
							color = (int)(1.5*iterations);
						else if(iterations < 200)
							color = (int)(1.25*iterations);
						else
							color = 255;
						StdDraw.setPenColor(new Color(color,255-color,color));
					} else
						StdDraw.setPenColor(new Color(colors[iterations][0],colors[iterations][1],colors[iterations][2]));
					
					StdDraw.point(j, i);
				} else if (iterations == MAX) {
					StdDraw.setPenColor(StdDraw.RED);						
					StdDraw.point(j, i);
				}
				if(drawPoints) {
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.setPenRadius(0.1/(double)g);
					StdDraw.point(j, i);
					//StdDraw.text(j, i+0.1, getComplexCoordinate(j, i).toString()); // This will write the coordinates as well				
				}
			}
		}
		
		StdDraw.show(0);
	}
	
	
	private static double consoleInputCheck(Scanner scanner){
		while(!scanner.hasNextDouble()){
			scanner.next();
			System.out.print("Invalid input! Try again: ");
		}
		return scanner.nextDouble();
	}
	
	private static int[][] loadColors(String filename) throws FileNotFoundException {
		int[][] colors = new int[MAX+1][3];
		Scanner fileScanner = new Scanner(new File("src" + File.separator + "afleveringsopgave4" + File.separator + "Files" + File.separator + "mnd" + File.separator + filename));
		
		for (int j = 0; j <= MAX; j++) {
			for (int i = 0; i < 3; i++)
				colors[j][i] = fileScanner.nextInt();
		}
		return colors;
	}
	
	private static Complex getComplexCoordinate(double j, double k) {
		return new Complex(x0 - s / 2.0 + (s * j) / (g - 1.0), y0 - s / 2.0 + (s * k) / (g - 1.0));
	}

	public static int iterate(Complex z0) {
		Complex z = new Complex(z0);
		for (int i = 0; i < MAX; i++) {
			if (z.abs() > 2.0) {
				return i;
			}
			z = z.times(z).plus(z0);
		}
		return MAX;
	}
}