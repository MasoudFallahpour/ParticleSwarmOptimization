package ir.fallahpoor.pso;

import java.util.Arrays;

public class ParticleSwarmOptimization {

    // Number of dimensions of problem to be solved
    public static final int NO_DIMENSIONS = 2;

    // Number of particles
    private static final int NO_PARTICLES = 10;

    // The minimum and maximum value of each dimension
    private static final double MIN = -2;
    private static final double MAX = 2;

    // Inertia weight that determines the significance of previous velocity of each particle
    private static final double W = 0.729;

    // Local weight that determines the tendency of particle to return to its best previous position
    private static final double C1 = 1.49;

    // Global weight that determines the tendency to move towards the best global position
    private static final double C2 = 1.49;
    private static final int MAX_ITERATIONS = 10000;

    private double[] globalBestSolution;
    private Particle[] particles;
    private int iteration;

    public ParticleSwarmOptimization() {
        globalBestSolution = new double[NO_DIMENSIONS];
        particles = new Particle[NO_PARTICLES];
        generateRandomSolution();
    }

    private void generateRandomSolution() {
        for (int i = 0; i < NO_DIMENSIONS; i++) {
            double randomCoordinate = random(MIN, MAX);
            globalBestSolution[i] = randomCoordinate;
        }
    }

    private double random(double min, double max) {
        return min + ((max - min) * Math.random());
    }

    public void run() {

        initializeParticles();

        while (iteration < MAX_ITERATIONS) {

            for (Particle particle : particles) {

                updateVelocity(particle);

                updatePosition(particle);

                updateParticleBestPositionIfNeeded(particle);

                UpdateGlobalBestPositionIfNeeded(particle);

            }

            iteration++;

        }

        printSolution();

    }

    private void initializeParticles() {
        for (int i = 0; i < NO_PARTICLES; i++) {
            double[] position = getRandomPosition();
            double[] velocity = getRandomVelocity();
            particles[i] = new Particle(position, velocity);
        }
    }

    private void updateVelocity(Particle particle) {
        for (int i = 0; i < particle.getVelocity().length; i++) {
            double Rp = Math.random();
            double Rg = Math.random();
            particle.getVelocity()[i] = (W * particle.getVelocity()[i]) +
                    (C1 * Rp * (particle.getBestPosition()[i] - particle.getPosition()[i])) +
                    (C2 * Rg * (globalBestSolution[i] - particle.getPosition()[i]));
        }
    }

    private void updatePosition(Particle particle) {

        for (int i = 0; i < particle.getPosition().length; i++) {

            particle.getPosition()[i] += particle.getVelocity()[i];

            // Adjust particle's position if we are out of range
            if (particle.getPosition()[i] < MIN) {
                particle.getPosition()[i] = MIN;
            } else if (particle.getPosition()[i] > MAX) {
                particle.getPosition()[i] = MAX;
            }

        }

    }

    private void updateParticleBestPositionIfNeeded(Particle particle) {
        if (f(particle.getPosition()) < f(particle.getBestPosition())) {
            particle.setBestPosition(particle.getPosition());
        }
    }

    private double[] getRandomPosition() {

        double[] position = new double[NO_DIMENSIONS];

        for (int i = 0; i < NO_DIMENSIONS; i++) {
            position[i] = random(MIN, MAX);
        }

        return position;
    }

    private double[] getRandomVelocity() {

        double[] velocity = new double[NO_DIMENSIONS];

        for (int i = 0; i < NO_DIMENSIONS; i++) {
            velocity[i] = random(-(MAX - MIN), MAX - MIN);
        }

        return velocity;

    }

    private void UpdateGlobalBestPositionIfNeeded(Particle particle) {
        if (f(particle.getBestPosition()) < f(globalBestSolution)) {
            System.arraycopy(particle.getBestPosition(), 0, globalBestSolution, 0,
                    particle.getBestPosition().length);
        }
    }

    private void printSolution() {
        System.out.println("Best solution is: " + Arrays.toString(globalBestSolution));
    }

    // Function f that we want to find its minimum
    private double f(double[] data) {
        double term1 = -data[0] * data[0];
        double term2 = Math.pow(data[1], 2);
        double term3 = Math.sin(data[0]);
        return Math.exp(term1 - term2) * term3;
    }

}
