package org.pismery.javacourse.spring.homework;

public class User {
    private Long id;
    private String name;
    private String createBy;

    public User() {
    }

    public User(Long id, String name, String createBy) {
        this.id = id;
        this.name = name;
        this.createBy = createBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createBy='" + createBy + '\'' +
                '}';
    }

    public void initMethod() {
        System.out.println("Invoke initMethod...");
        setId(1L);
        setName("Liuliuliu");
        setCreateBy("init-method");
    }

    public static User createUser() {
        return new User(1L,"Liuliuliu","static-factory-method");
    }
}
