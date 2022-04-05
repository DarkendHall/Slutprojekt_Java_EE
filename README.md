[![Build status](https://github.com/DarkendHall/Slutprojekt_Java_EE/actions/workflows/maven.yml/badge.svg)](https://github.com/DarkendHall/Slutprojekt_Java_EE/actions/workflows/maven.yml)
[![LineLint](https://github.com/DarkendHall/Slutprojekt_Java_EE/actions/workflows/linelint.yml/badge.svg)](https://github.com/DarkendHall/Slutprojekt_Java_EE/actions/workflows/linelint.yml)

![Code Coverage](https://raw.githubusercontent.com/DarkendHall/Slutprojekt_Java_EE/badges/jacoco.svg)

# Slutprojekt Java EE

This is the project for the group project for the course Java Enterprise.

<p><u>Installation:</u></p>

You need to set the following environment variables:

| Environment variable | Description                                            | Default   |
|----------------------|--------------------------------------------------------|-----------|
| MYSQL_HOST           | The URL/IP address of the MySQL server                 | localhost |
| DB_DATABASE          | The database that you want to connect to on the server |           |
| DB_PORT              | The port of the MySQL server                           | 3306      |
| DB_USER              | Username of the database user                          |           |
| DB_PASSWORD          | Password of the database user                          |           |
| PASSWORD             | The password for the standard user                     |           |
| MJ_EMAIL_ADDRESS     | The email address of the MailJet sender                |           |
| MJ_PRIMARY_KEY       | The primary key of the MailJet account                 |           |
| MJ_SECRET_KEY        | The secret key of the MailJet account                  |           |

After those have been set, you're good to go!


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

Send a post request to [/users/signup](http://localhost:8080/users/signup/) with JSON body:

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
* Oscar Eriksson Stjernfeldt
* Christian Löfqvist
