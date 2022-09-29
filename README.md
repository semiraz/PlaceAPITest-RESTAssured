# Example of testing REST APIs using Java REST Assured 
 


#### Test automation suite for testing "rahulshettyacademy.com" REST APIs

## General info
Firstly, created POJO classes for converting Java object into request and back from response to Java object(src/main/java/pojo package) for CourseTest.

Created POJO classes for testing E2E APIs(src/test/java/tests/AddDeleteBookTest.java) for creating new places(src/main/java/company package).


Implemented OAuth 2.0 code for communication with REST API(src/main/java/authorization/OAuthTest1.java).
 Applied Access Token which we get from OAuth2Test for accesing APIs to verify Courses details.




## Environment

`Java version 11.0.13`




## Deployment


To run e2eBooktest.xml suite use:
```
mvn test -PE2EBook
```

To run apiCourses.xml suite use:
```
mvn test -PApiCourses
```



## Status

This project is for practicing how to build up Rest Assured Automation Test for the OAuth 2.0 authorization code.

Also, practiced Serialization and Deserialization using POJO classes with Rest Assured.
