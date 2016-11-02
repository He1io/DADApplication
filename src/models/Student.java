package models;

/**
 *
 * @author Helio
 */
public class Student {
    
    //PROPIEDADES
    String id,name, firstSurname,secondSurname;
    int registry;

    //CONSTRUCTOR
    public Student() {
        
    }
    
    //GETTER Y SETTER 
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurname = firstSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
    }    

    public int getRegistry() {
        return registry;
    }

    public void setRegistry(int registry) {
        this.registry = registry;
    }
}
