
PV243-student-recipe-book

How to deploy the application:

- build existing project using maven
- move the built target .war file to the $JBOSS_HOME/standalone/deployments folder and run the JBOSS AS. 
- the running application will be aviable on the: http://localhost:8080/cookbook-webapp/ depending 
the JBOSS AS configuration

The latest version is aviable on:
https://github.com/fkiss/pv243-student-recipe-book.git

The app is also aviable on OpenShift: http://cookbook-plevko.rhcloud.com/index.jsf


For entering to authenticated part of web please use these credentials:

- Role CUSTOMER:  
    - you can register yourself with the use of our fantastic registration form

- Role ADMIN:
    - use username "admin" password "admin"
