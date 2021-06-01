public class Nbody {
    public static void main(String[] args) {
        // Step 1. Parse command-line arguments.
        double stopTime = Double.parseDouble(args[0]);
        double deltat = Double.parseDouble(args[1]);
        // Step 2. Read universe from standard input.
        int n = StdIn.readInt();
        double radius = StdIn.readDouble();
        double G = 6.67e-11;
        // position \\
        double[] px = new double[n];
        double[] py = new double[n];
        // skorost' \\
        double[] vx = new double[n];
        double[] vy = new double[n];
        // massa \\
        double[] mass = new double[n];
        // for images \\
        String[] image = new String[n];

        for (int i = 0; i < n; i++) {
            px[i] = StdIn.readDouble();
            py[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            image[i] = StdIn.readString();
        }

        // Step 3. Initialize standard drawing.
        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);
        StdDraw.enableDoubleBuffering();

        // Step 4. Play music on standard audio.
        StdAudio.play("2001.wav");


        // Step 5. Simulate the universe.
        double fx[] = new double[n];
        double fy[] = new double[n];
        double ax[] = new double[n];
        double ay[] = new double[n];

        for (double t = 0.0; t < stopTime; t += deltat) {
            // Step 5A. Calculate net forces.
            for (int i = 0; i < n; i++) {
                fx[i] = 0;
                fy[i] = 0;
                for (int j = 0; j < n; j++) {
                    if (i == j) {
                        continue;
                    }
                    // rastoyanie
                    double dx = px[j] - px[i];
                    double dy = py[j] - py[i];
                    // double r = (px[i] - px[j]) * px[i] - px[j] + (py[i] - py[j] + )
                    double r = Math.sqrt((dx * dx) + (dy * dy));
                    double f = (G * mass[i] * mass[j]) / (r * r);
                    // r = Math.max(dx, 0.001);
                    fx[i] += f * (dx / r);
                    fy[i] += f * (dy / r);
                }
            }

            for (int i = 0; i < n; i++) {
                ax[i] = fx[i] / mass[i];
                ay[i] = fy[i] / mass[i];
            }

            // Step 5B. Update velocities and positions.
            for (int i = 0; i < n; i++) {
                vx[i] += (deltat * ax[i]);
                vy[i] += (deltat * ay[i]);
            }

            for (int i = 0; i < n; i++) {
                //update position
                px[i] += (deltat * vx[i]);
                py[i] += (deltat * vy[i]);
            }

            // Step 5C. Draw universe to standard drawing.
            StdDraw.picture(0.0, 0.0, "starfield.jpg");
            for (int i = 0; i < n; i++) {
                StdDraw.picture(px[i], py[i], image[i]);
            }
            StdDraw.show();
            StdDraw.pause(10);
            // StdDraw.pause(20);
        }

        // Step 6. Print universe to standard output.
        StdOut.printf("%d\n", n);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < n; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          px[i], py[i], vx[i], vy[i], mass[i], image[i]);
        }

    }
}
