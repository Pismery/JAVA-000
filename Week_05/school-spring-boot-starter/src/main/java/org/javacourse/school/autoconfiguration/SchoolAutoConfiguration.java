package org.javacourse.school.autoconfiguration;


import org.javacourse.school.autoconfiguration.bean.Klass;
import org.javacourse.school.autoconfiguration.bean.School;
import org.javacourse.school.autoconfiguration.bean.Student;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({School.class, Klass.class, Student.class})
public class SchoolAutoConfiguration {

    //@Bean
    //@ConditionalOnProperty(name = "school.enabled", havingValue = "true", matchIfMissing = true)
    //@ConditionalOnMissingBean(School.class)
    //public School school() {
    //    return new School();
    //}
    //
    //@Bean
    //@ConditionalOnProperty(name = "klass.enabled", havingValue = "true", matchIfMissing = true)
    //@ConditionalOnMissingBean(Klass.class)
    //public Klass klass() {
    //    return new Klass();
    //}
    //
    //@Bean
    //@ConditionalOnProperty(name = "student.enabled", havingValue = "true", matchIfMissing = true)
    //@ConditionalOnMissingBean(Student.class)
    //public Student student() {
    //    return new Student();
    //}


}
