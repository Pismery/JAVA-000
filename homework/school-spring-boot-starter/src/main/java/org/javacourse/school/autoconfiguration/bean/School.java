package org.javacourse.school.autoconfiguration.bean;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "school")
public class School {

    private Klass klass;

    private Student student100;

    public School() {
    }

    public School(Klass klass, Student student100) {
        this.klass = klass;
        this.student100 = student100;
    }

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }

    public Student getStudent100() {
        return student100;
    }

    public void setStudent100(Student student100) {
        this.student100 = student100;
    }

    @Override
    public String toString() {
        return "School{" +
                "klass=" + klass +
                ", student100=" + student100 +
                '}';
    }
}
