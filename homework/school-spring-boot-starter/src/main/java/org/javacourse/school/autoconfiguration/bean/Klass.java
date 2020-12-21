package org.javacourse.school.autoconfiguration.bean;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "klass")
public class Klass {

    private List<Student> students;


    public Klass() {
    }

    public Klass(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Klass{" +
                "students=" + students +
                '}';
    }
}
