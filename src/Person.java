public abstract class Person {

    protected String id;
    protected String name;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Common getters/setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Abstract method to enforce a summary representation
     * in all subclasses (abstraction).
     */
    public abstract String getSummary();
}
