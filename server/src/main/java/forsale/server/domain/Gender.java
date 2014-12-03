package forsale.server.domain;

public enum Gender {
    MALE,
    FEMALE;

    @Override
    public String toString() {
        return this.name().toUpperCase();
    }
}
