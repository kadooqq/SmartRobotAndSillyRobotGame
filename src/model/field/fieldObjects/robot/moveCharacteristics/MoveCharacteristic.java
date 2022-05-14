package model.field.fieldObjects.robot.moveCharacteristics;

public abstract class MoveCharacteristic {
    public MoveCharacteristic(int lifeTime) {
        if (lifeTime < 0) {
            throw new IllegalArgumentException("Невозможно создать характеристику ландшафта отрицательной");
        }
        _lifeTime = lifeTime;
    }

    private double _lifeTime;

    public double getLifeTime() {
        return _lifeTime;
    }

    public boolean decrementLifeTime() {
        if (_lifeTime > 0) {
            _lifeTime--;
            return true;
        }
        return false;
    }
}
