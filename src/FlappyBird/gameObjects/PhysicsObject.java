package FlappyBird.gameObjects;


public interface PhysicsObject {
    void doPhysics(double deltaTime, double velocity, double gravity);
}
