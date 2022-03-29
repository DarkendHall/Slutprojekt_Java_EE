[![Build status](https://github.com/DarkendHall/Slutprojekt_Java_EE/actions/workflows/maven.yml/badge.svg)](https://github.com/DarkendHall/Slutprojekt_Java_EE/actions/workflows/maven.yml)
[![Qodana](https://github.com/DarkendHall/Slutprojekt_Java_EE/actions/workflows/qodana.yml/badge.svg)](https://github.com/DarkendHall/Slutprojekt_Java_EE/actions/workflows/qodana.yml)
[![LineLint](https://github.com/DarkendHall/Slutprojekt_Java_EE/actions/workflows/linelint.yml/badge.svg)](https://github.com/DarkendHall/Slutprojekt_Java_EE/actions/workflows/linelint.yml)

![Code Coverage](https://raw.githubusercontent.com/DarkendHall/Slutprojekt_Java_EE/badges/jacoco.svg)

# Slutprojekt Java EE

This is the project for the group project for the course Java Enterprise.

<details> <summary><b>Associations</b></summary>
Course:

* Students (ManyToMany)
* Teacher (ManyToOne)

School:

* Principal (OneToOne)
* Students (OneToMany)
* Courses (OneToMany)
* Teachers (ManyToMany)

Role:

* Users (ManyToMany)

User:

* Roles (ManyToMany)

</details>
<p></p>
<details> <summary><b>Endpoints & JSON</b></summary>

Send a post request to /users/signup with JSON body:

```JSON
{
  "username:": "<Username>",
  "password": "<Password>"
} 
```

Login with the same details at [/swagger-ui](http://localhost:8080/swagger-ui/)

There you will find a complete list of all available endpoints as well as JSON bodies.
</details>

The people who worked on this project:

* Marcus Leeman
* Oscar Stjernfeldt
* Christian Löfqvist
