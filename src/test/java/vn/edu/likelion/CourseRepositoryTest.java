package vn.edu.likelion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import vn.edu.likelion.entity.*;
import vn.edu.likelion.repository.ChapterRepository;
import vn.edu.likelion.repository.CourseRepository;
import vn.edu.likelion.repository.LessonRepository;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CourseRepositoryTest {
    @Autowired private CourseRepository courseRepository;
    @Autowired private ChapterRepository chapterRepository;
    @Autowired private LessonRepository lessonRepository;

    @Test
    public void testCreateCourse(){

        Course course1 = new Course("Java 17 Masterclass: Start Coding in 2024", "java-the-complete-java-developer-course",
                "Acquire Key Java Skills: From Basics to Advanced Programming and Certification - Start Your Dev Career",
                "https://res.cloudinary.com/dqnoopa0x/image/upload/v1723533376/konauxwk29zexktezzhs.webp",
                299000, 350000, new Category(1));

        Course course2 = new Course("Learn C++ Programming - Beginner to Advanced", "learn-cpp-programming-beginner-to-advanced",
                "Object Oriented Programming using C++ Language with File Handling, Exception Handling, Standard Template Library",
                "https://res.cloudinary.com/dqnoopa0x/image/upload/v1723533615/oyocdegotvmp01zuh7a0.jpg",
                345000, 550000, new Category(1));

        Course course3 = new Course("Ultimate C# Masterclass for 2024", "ultimate-csharp-masterclass",
                "In-depth .NET programming course from basics to advanced. Focus on clean code, performance and practice",
                "https://res.cloudinary.com/dqnoopa0x/image/upload/v1723533763/dxhhgampzijmdqksz9tk.png",
                590000, 659000, new Category(1));

        Course course4 = new Course("The Complete JavaScript Course 2024: From Zero to Expert", "the-complete-javascript-course",
                "The modern JavaScript course for everyone! Master JavaScript with projects, challenges and theory. Many courses in one!",
                "https://res.cloudinary.com/dqnoopa0x/image/upload/v1723533902/f2ua1awsatv1csdbq74p.png",
                267000, 324000, new Category(1));

        Course course5 = new Course("The Complete 2024 Web Development Bootcamp", "the-complete-web-development-bootcamp",
                "Become a Full-Stack Web Developer with just ONE course. HTML, CSS, Javascript, Node, React, PostgreSQL, Web3 and DApps",
                "https://res.cloudinary.com/dqnoopa0x/image/upload/v1723534035/wgx3dvyteudahxztb2jm.jpg",
                756000, 987000, new Category(2));

        Course course6 = new Course("Complete Web & Mobile Designer: UI/UX, Figma, +more", "complete-web-designer-mobile-designer-zero-to-mastery",
                "Become a UX/UI Designer! Master Mobile and Web Design, User Interface + User Experience (UI/UX Design), HTML, and CSS",
                "https://res.cloudinary.com/dqnoopa0x/image/upload/v1723534207/xgdlwnmdbozwfjfbhiho.jpg",
                219000, 320000, new Category(2));

        Course course7 = new Course("RESTful Web Services, Java, Spring Boot, Spring MVC and JPA", "restful-web-service-with-spring-boot-jpa-and-mysql",
                "Implement API calls: Sign-up, sign-in, email verification, password reset, update, delete. Deploy to Amazon AWS Cloud.",
                "https://res.cloudinary.com/dqnoopa0x/image/upload/v1723534318/w6km8a8dxmphrkelzgl5.jpg",
                359000, 419000, new Category(2));

        Course course8 = new Course("iOS & Swift - The Complete iOS App Development Bootcamp", "ios-13-app-development-bootcamp",
                "From Beginner to iOS App Developer with Just One Course! Fully Updated with a Comprehensive Module Dedicated to SwiftUI!",
                "https://res.cloudinary.com/dqnoopa0x/image/upload/v1723534422/quiikd1jwwmt6nt7zbxt.jpg",
                576000, 690000, new Category(3));

        Course course9 = new Course("The Complete Android 14 & Kotlin Development Masterclass", "android-kotlin-developer",
                "Learn Android 14 App Development From Begin",
                "https://res.cloudinary.com/dqnoopa0x/image/upload/v1723534535/zmklgboqd5edhz4g1oum.jpg",
                399000, 450000, new Category(3));

        Course course10 = new Course("Flutter & Dart - The Complete Guide [2024 Edition]", "learn-flutter-dart-to-build-ios-android-apps",
                "A Complete Guide to the Flutter SDK &amp; Flutter Framework for building native iOS and Android apps",
                "https://res.cloudinary.com/dqnoopa0x/image/upload/v1723534664/vyeiw2aasrarjs9lrqfl.png",
                439000, 500000, new Category(3));

        List<Course> listCourses = courseRepository.saveAll(List.of(course1, course2, course3, course4, course5, course6,
                course7, course8, course9, course10));
        Assertions.assertThat(listCourses).hasSize(10);
    }

    @Test
    public void testCreateCourseDetail1(){
        Course course = courseRepository.findById(1).get();

        course.getListCourseDetails().add(new CourseDetail("Learn the core Java skills needed to apply for Java developer positions in just 14 hours.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Be able to demonstrate your understanding of Java to future employers.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Acquire essential java basics for transitioning to the Spring Framework, Java EE, Android development and more.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Be able to sit for and pass the Oracle Java Certificate exam if you choose.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Obtain proficiency in Java 17, as well as older versions including Java 11 and Java 8.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Learn industry \"best practices\" in Java software development from a professional Java developer who has worked in the language for close to 25 years.",
                InformationType.TARGET, course));

        course.getListCourseDetails().add(new CourseDetail("A computer with either Windows, Mac or Linux to install all the free software and tools needed to build your new apps (I provide specific videos on installations for each platform).",
                InformationType.REQUIREMENT, course));
        course.getListCourseDetails().add(new CourseDetail("A strong work ethic, willingness to learn, and plenty of excitement about the awesome new programs you’re about to build.",
                InformationType.REQUIREMENT, course));
        course.getListCourseDetails().add(new CourseDetail("Nothing else! It’s just you, your computer and your hunger to get started today.",
                InformationType.REQUIREMENT, course));


        Course savedCourse = courseRepository.save(course);
        Assertions.assertThat(savedCourse.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateCourseDetail2(){
        Course course = courseRepository.findById(2).get();

        course.getListCourseDetails().add(new CourseDetail("Students will learn Objet Oriented Programming using C++ Language.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Students will learn Exception Handling in C++.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Students will learn how to use ChatGPT for Development.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Students will learn File Handling in C++.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Students will learn Standard Template Library in C++.",
                InformationType.TARGET, course));

        course.getListCourseDetails().add(new CourseDetail("A computer with either Windows, Mac or Linux to install all the free software and tools needed to build your new apps (I provide specific videos on installations for each platform).",
                InformationType.REQUIREMENT, course));
        course.getListCourseDetails().add(new CourseDetail("There is no hard requirement of knowledge of any programming language. Knowledge of C language will help students to understand this course in better way but it is not mandatory.",
                InformationType.REQUIREMENT, course));


        Course savedCourse = courseRepository.save(course);
        Assertions.assertThat(savedCourse.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateCourseDetail3(){
        Course course = courseRepository.findById(3).get();

        course.getListCourseDetails().add(new CourseDetail("You will gain an in-depth understanding of C#.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("You will understand the principles of object-oriented programming.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("You will learn to write code of excellent quality.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("You will practice your skills by solving exercises in the browser, as well as by creating advanced projects.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("You will learn how to create unit tests using NUnit and Moq libraries.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("You will understand how to write high-performance C# code.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("You will learn the most useful design patterns.",
                InformationType.TARGET, course));

        course.getListCourseDetails().add(new CourseDetail("No programming experience is needed. I'll teach you everything you need to know.",
                InformationType.REQUIREMENT, course));
        course.getListCourseDetails().add(new CourseDetail("A computer (Windows/macOS) with an access to the Internet.",
                InformationType.REQUIREMENT, course));
        course.getListCourseDetails().add(new CourseDetail("No paid software is needed; all tools used in this course are free.",
                InformationType.REQUIREMENT, course));

        Course savedCourse = courseRepository.save(course);
        Assertions.assertThat(savedCourse.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateCourseDetail4(){
        Course course = courseRepository.findById(4).get();

        course.getListCourseDetails().add(new CourseDetail("Become an advanced, confident, and modern JavaScript developer from scratch",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("JavaScript fundamentals: variables, if/else, operators, boolean logic, functions, arrays, objects, loops, strings, etc.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Modern OOP: Classes, constructors, prototypal inheritance, encapsulation, etc.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Asynchronous JavaScript: Event loop, promises, async/await, AJAX calls and APIs",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Modern tools for 2022 and beyond: NPM, Parcel, Babel and ES6 modules",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Build 6 beautiful real-world projects for your portfolio (not boring toy apps)",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("How to think and work like a developer: problem-solving, researching, workflows",
                InformationType.TARGET, course));

        course.getListCourseDetails().add(new CourseDetail("No coding experience is necessary to take this course! I take you from beginner to expert!",
                InformationType.REQUIREMENT, course));
        course.getListCourseDetails().add(new CourseDetail("Any computer and OS will work — Windows, macOS or Linux. We will set up your text editor the course.",
                InformationType.REQUIREMENT, course));
        course.getListCourseDetails().add(new CourseDetail("A basic understanding of HTML and CSS is a plus, but not a must! The course includes an HTML and CSS crash course.",
                InformationType.REQUIREMENT, course));

        Course savedCourse = courseRepository.save(course);
        Assertions.assertThat(savedCourse.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateCourseDetail5(){
        Course course = courseRepository.findById(5).get();

        course.getListCourseDetails().add(new CourseDetail("Build 16 web development projects for your portfolio, ready to apply for junior developer jobs.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("After the course you will be able to build ANY website you want.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Learn the latest technologies, including Javascript, React, Node and even Web3 development.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Build fully-fledged websites and web apps for your startup or business.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Master frontend development with React",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Learn professional developer best practices.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Master backend development with Node",
                InformationType.TARGET, course));

        course.getListCourseDetails().add(new CourseDetail("No programming experience needed - I'll teach you everything you need to know",
                InformationType.REQUIREMENT, course));
        course.getListCourseDetails().add(new CourseDetail("A computer with access to the internet",
                InformationType.REQUIREMENT, course));
        course.getListCourseDetails().add(new CourseDetail("I'll walk you through, step-by-step how to get all the software installed and set up",
                InformationType.REQUIREMENT, course));

        Course savedCourse = courseRepository.save(course);
        Assertions.assertThat(savedCourse.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateCourseDetail6(){
        Course course = courseRepository.findById(6).get();

        course.getListCourseDetails().add(new CourseDetail("Build beautifully designed web and mobile projects for your customers using modern tools used by top companies in 2023",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("After the course you will be able to build ANY website you want.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Includes 100+ assets and premium design templates that you can keep and use to customize for all your future projects",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Have an amazing design portfolio customized and professionally completed by the end of the course (we provide it for you!)",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Get hired as a Designer or become a freelancer that can work from anywhere and for anyone. Designers are in high demand!",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Master Figma for your design needs then learn to convert your designs into a live HTML an CSS website",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Learn to use HTML5 and CSS3 to make your designs come to life and create fully working websites",
                InformationType.TARGET, course));

        course.getListCourseDetails().add(new CourseDetail("No requirements. We teach you and show you everything from scratch! From Zero to Mastery",
                InformationType.REQUIREMENT, course));
        course.getListCourseDetails().add(new CourseDetail("Get ready to fall in love with Design and making everything you touch into beautiful projects for the rest of your life!",
                InformationType.REQUIREMENT, course));

        Course savedCourse = courseRepository.save(course);
        Assertions.assertThat(savedCourse.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateCourseDetail7(){
        Course course = courseRepository.findById(7).get();

        course.getListCourseDetails().add(new CourseDetail("Build a RESTful Web Service with Spring Boot",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Learn how to implement User Sign-up functionality",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Protect RESTful Web Service with Spring Security Framework",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Implement Password Reset and Email Verification features",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Learn how to implement Token-Based Authentication",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Use Spring Data JPA Query Methods",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Create new project using Spring Tool Suite and Spring Initializer",
                InformationType.TARGET, course));

        course.getListCourseDetails().add(new CourseDetail("Basic knowledge of Java",
                InformationType.REQUIREMENT, course));
        course.getListCourseDetails().add(new CourseDetail("Mac computer",
                InformationType.REQUIREMENT, course));

        Course savedCourse = courseRepository.save(course);
        Assertions.assertThat(savedCourse.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateCourseDetail8(){
        Course course = courseRepository.findById(8).get();

        course.getListCourseDetails().add(new CourseDetail("You will create a portfolio of 15 apps to be able apply for junior developer jobs at a technology company",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("You will learn by doing, where every lesson is incorporated into a real-world app project.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("You will learn Xcode, UIKit and SwiftUI, ARKit, CoreML and CoreData.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("After the course, you will be able to build any app you want.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Become a digital nomad by working as a freelance iOS developer",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Create apps that use Machine Learning using Apple’s new CoreML",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Master app marketing so you can publish your apps and generate downloads",
                InformationType.TARGET, course));

        course.getListCourseDetails().add(new CourseDetail("A Mac computer running macOS 10.15 (Catalina) or a PC running macOS.",
                InformationType.REQUIREMENT, course));
        course.getListCourseDetails().add(new CourseDetail("No programming experience needed - I'll teach you everything you need to know",
                InformationType.REQUIREMENT, course));
        course.getListCourseDetails().add(new CourseDetail("No paid software required - all apps will be created in Xcode 11 (which is free to download)",
                InformationType.REQUIREMENT, course));
        course.getListCourseDetails().add(new CourseDetail("I'll walk you through, step-by-step how to get Xcode installed and set up",
                InformationType.REQUIREMENT, course));

        Course savedCourse = courseRepository.save(course);
        Assertions.assertThat(savedCourse.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateCourseDetail9(){
        Course course = courseRepository.findById(9).get();

        course.getListCourseDetails().add(new CourseDetail("You can build any Android app you can think of. No matter if it is an idea that you or your friends have, or if it is a contract job that you need to develop.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("You Will Be Proficient in XML",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("You'll be able to work as an Android freelancer and work from anywere in the world.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("You'll be able to develop cloud apps using Google Firebase",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("You will build Apps for your portfolio to apply for jr. Android developer Jobs.",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("You Will Be Proficient using Jetpack Compose",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Publish your apps on Google Play and generate revenue with Google Pay and Google Ads",
                InformationType.TARGET, course));

        course.getListCourseDetails().add(new CourseDetail("A Windows, Mac or Linux Computer",
                InformationType.REQUIREMENT, course));
        course.getListCourseDetails().add(new CourseDetail("No Programming knowledge required - I'll teach you everything you need to know",
                InformationType.REQUIREMENT, course));

        Course savedCourse = courseRepository.save(course);
        Assertions.assertThat(savedCourse.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateCourseDetail10(){
        Course course = courseRepository.findById(10).get();

        course.getListCourseDetails().add(new CourseDetail("Learn Flutter and Dart from the ground up, step-by-step",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Use features like Google Maps, the device camera, authentication and much more!",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Learn all the basics without stopping after them: Dive deeply into Flutter & Dart and become an advanced developer",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Build engaging native mobile apps for both Android and iOS",
                InformationType.TARGET, course));
        course.getListCourseDetails().add(new CourseDetail("Learn how to upload images and how to send manual and automated push notifications",
                InformationType.TARGET, course));


        course.getListCourseDetails().add(new CourseDetail("Basic programming language will help but is not a must-have",
                InformationType.REQUIREMENT, course));
        course.getListCourseDetails().add(new CourseDetail("You can use either Windows, macOS or Linux for Android app development - iOS apps can only be built on macOS though",
                InformationType.REQUIREMENT, course));

        Course savedCourse = courseRepository.save(course);
        Assertions.assertThat(savedCourse.getId()).isGreaterThan(0);
    }

    @Test
    public void createCSharpChaptersAndLessons() {
        Course course = courseRepository.findById(3).get();

        // Chapter 1: Introduction to C#
        Chapter chapter1 = new Chapter("Introduction to C#", course);
        Lesson lesson1_1 = new Lesson("Introduction to the Course",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1715842020/yvnvlrg32pg2bonr9hbi.mp4", chapter1);
        Lesson lesson1_2 = new Lesson("What is C#?",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1715786427/swxwii04pqnrb2olowgy.mp4", chapter1);
        Lesson lesson1_3 = new Lesson("Setting Up the Development Environment",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1717597291/gwxlhhukyywu2bavcl7h.mp4", chapter1);

        // Chapter 2: C# Basics
        Chapter chapter2 = new Chapter("C# Basics", course);
        Lesson lesson2_1 = new Lesson("Variables and Data Types",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712569069/he6w39biizysgme3ceqd.mp4", chapter2);
        Lesson lesson2_2 = new Lesson("Control Structures in C#",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712568598/pglcufliizx40hkdpa4o.mp4", chapter2);

        // Chapter 3: Object-Oriented Programming in C#
        Chapter chapter3 = new Chapter("Object-Oriented Programming in C#", course);
        Lesson lesson3_1 = new Lesson("Introduction to OOP",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712567127/jizysfqfpdrd0m0lckq1.mp4", chapter3);
        Lesson lesson3_2 = new Lesson("Classes and Objects",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712567824/ju7dv8b4qq6vjiasuepb.mp4", chapter3);

        // Save Chapters and Lessons
        chapterRepository.saveAll(List.of(chapter1, chapter2, chapter3));
        lessonRepository.saveAll(List.of(lesson1_1, lesson1_2, lesson1_3, lesson2_1, lesson2_2, lesson3_1, lesson3_2));

        // Assertions
        Assertions.assertThat(chapterRepository.count()).isEqualTo(3);
        Assertions.assertThat(lessonRepository.count()).isEqualTo(7);
    }

}
