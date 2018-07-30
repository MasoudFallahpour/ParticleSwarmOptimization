package ir.fallahpoor.pso;

import java.util.Arrays;

public class Particle {

    private double[] position;
    private double[] velocity;
    private double[] bestPosition;

    public Particle(double[] position, double[] velocity) {

        this.position = new double[ParticleSwarmOptimization.NO_DIMENSIONS];
        this.velocity = new double[ParticleSwarmOptimization.NO_DIMENSIONS];
        this.bestPosition = new double[ParticleSwarmOptimization.NO_DIMENSIONS];

        System.arraycopy(position, 0, this.position, 0, position.length);
        System.arraycopy(velocity, 0, this.velocity, 0, velocity.length);

    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public double[] getVelocity() {
        return velocity;
    }

    public void setVelocity(double[] velocity) {
        this.velocity = velocity;
    }

    public double[] getBestPosition() {
        return bestPosition;
    }

    public void setBestPosition(double[] bestPosition) {
        this.bestPosition = bestPosition;
    }

    @Override
    public String toString() {
        return "Best position: " + Arrays.toString(bestPosition);
    }

}
