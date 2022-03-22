[![Build status](https://github.com/DarkendHall/Slutprojekt_Java_EE/actions/workflows/maven.yml/badge.svg)](https://github.com/DarkendHall/Slutprojekt_Java_EE/actions/workflows/maven.yml)
[![Qodana](https://github.com/DarkendHall/Slutprojekt_Java_EE/actions/workflows/qodana.yml/badge.svg)](https://github.com/DarkendHall/Slutprojekt_Java_EE/actions/workflows/qodana.yml)

# Slutprojekt Java EE

This is the project for the group project for the course Java Enterprise.

### Associations

Course:

* Students (ManyToMany)
* Teacher (ManyToOne)

School:

* Principal (OneToOne)
* Students (OneToMany)
* Courses (OneToMany)
* Teachers (ManyToMany)

--------------------
Role:

* Users (ManyToMany)

User:

* Roles (ManyToMany)

### JSON

### ENDPOINTS