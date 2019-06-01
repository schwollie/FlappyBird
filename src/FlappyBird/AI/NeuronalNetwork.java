package FlappyBird.AI;

import java.util.Random;

public class NeuronalNetwork {
    private Matrix[] weights;
    private int[] layer_sizes;

    public NeuronalNetwork(int[] layer_sizes) {
        this.layer_sizes = layer_sizes;
        setupWeights();
    }

    public NeuronalNetwork(Matrix[] weights, int[] layer_sizes) {
        this.layer_sizes = layer_sizes;
        this.weights = weights;
    }

    private static int[] createSequenceParams() {
        Random rnd = new Random();
        return new int[]{rnd.nextInt(19) + 1, rnd.nextInt(1)};
        // {length of sequence, mother(0) or father(1)}
    }

    private void setupWeights() {
        weights = new Matrix[layer_sizes.length - 1];
        for (int i = 0; i < layer_sizes.length - 1; i++) {
            weights[i] = Matrix.random(layer_sizes[i + 1], layer_sizes[i]);
        }
    }

    public Matrix[] getWeights() {
        return weights;
    }

    public int[] getLayerSizes() {
        return layer_sizes;
    }

    public static NeuronalNetwork mutate(NeuronalNetwork nn) {
        NeuronalNetwork new_nn = new NeuronalNetwork(nn.getWeights(), nn.getLayerSizes());
        double influence = 0.04;

        Matrix[] weights = new_nn.getWeights();
        Random rnd = new Random();

        for (int w = 0; w < weights.length; w++) {
            for (double[] m : weights[w].getData()) {
                for (int c = 0; c < m.length; c++) {
                    double randomDelta = (double) (rnd.nextInt(200) - 100) / 100 * influence;
                    m[c] += randomDelta;
                }
            }
        }
        return new_nn;
    }

    public Matrix forward(Matrix A) {
        Matrix[] dots = new Matrix[weights.length];
        Matrix[] sigs = new Matrix[weights.length];

        Matrix dot = weights[0].times(A.transpose());
        dots[0] = dot;
        Matrix sig = dot.sigmoid();
        sigs[0] = sig;

        for (int i = 1; i < weights.length; i++) {
            dots[i] = weights[i].times(sigs[i - 1]);
            sigs[i] = dots[i].sigmoid();
        }

        //sigs[weights.length-1].show();
        return sigs[weights.length - 1];
    }

    public static NeuronalNetwork breed(NeuronalNetwork nn1, NeuronalNetwork nn2) {
        NeuronalNetwork father = new NeuronalNetwork(nn1.getWeights(), nn1.getLayerSizes());
        NeuronalNetwork mother = new NeuronalNetwork(nn2.getWeights(), nn2.getLayerSizes());

        Matrix[] fatherM = father.getWeights();
        Matrix[] motherM = mother.getWeights();

        NeuronalNetwork child = new NeuronalNetwork(nn1.getLayerSizes());

        try {

            for (int weight = 0; weight < fatherM.length; weight++) {
                for (int p = 0; p < motherM[weight].getData().length; p++) {
                    int[] sequenceParams = createSequenceParams();
                    for (int w = 0; w < motherM[weight].getData()[p].length; w++) {
                        double new_w = 0;
                        if (sequenceParams[0] == 0) {
                            sequenceParams[0] = 10;
                        }
                        if (sequenceParams[1] == 1) {
                            new_w = motherM[weight].getData()[p][w];
                        } else if (sequenceParams[1] == 0) {
                            new_w = fatherM[weight].getData()[p][w];
                        }

                        child.getWeights()[weight].getData()[p][w] = new_w;

                    }
                }
            }

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return child;

    }

    public void randomWeights() {
        this.setupWeights();
    }

    @Override
    public Object clone() {
        NeuronalNetwork nn = null;
        try {
            nn = (NeuronalNetwork) super.clone();
        } catch (CloneNotSupportedException e) {
            nn = new NeuronalNetwork(this.getWeights(), this.getLayerSizes());
        }
        return nn;
    }

    /*public static void main(String[] args) {
        NeuronalNetwork nn = new NeuronalNetwork(new int[] {3, 2, 1});
        //Matrix m = Matrix.random(1,2000);
        //nn.forward(m);
        nn.getWeights()[0].show();
        nn.mutate();

        nn.getWeights()[0].show();
    }*/
}
